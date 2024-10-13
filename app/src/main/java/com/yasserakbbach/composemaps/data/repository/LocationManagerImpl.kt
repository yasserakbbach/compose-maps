package com.yasserakbbach.composemaps.data.repository

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.yasserakbbach.composemaps.domain.model.Country
import com.yasserakbbach.composemaps.domain.model.LongLat
import com.yasserakbbach.composemaps.domain.repository.LocationManager
import com.yasserakbbach.composemaps.manager.LocationPermissionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "LocationManagerImpl"
private const val LOCATION_REQUEST_INTERVAL_MILLIS = 5000L
private const val LOCATION_REQUEST_MIN_UPDATE_DISTANCE_METERS = 1000F

class LocationManagerImpl @Inject constructor(
    private val locationPermissionManager: LocationPermissionManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val geocoder: Geocoder,
) : LocationManager {

    override suspend fun getCountryFromLongLat(longLat: LongLat): Country? =
        withContext(Dispatchers.IO) {
            try {
                suspendCoroutine { continuation ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocation(longLat.latitude, longLat.longitude, 1) {
                            val address = it.firstOrNull()
                            continuation.resume(
                                Country(
                                    name = address?.countryName.orEmpty(),
                                    code = address?.countryCode.orEmpty(),
                                ),
                            )
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        val address = geocoder.getFromLocation(longLat.latitude, longLat.longitude, 1)?.firstOrNull()
                        continuation.resume(
                            Country(
                                name = address?.countryName.orEmpty(),
                                code = address?.countryCode.orEmpty(),
                            ),
                        )
                    }
                }
            } catch (c: CancellationException) {
                throw c
            } catch (e: Exception) {
                Log.e(TAG, "fetchCurrentCountry error", e)
                null
            }
        }

    override fun fetchCurrentLongLat(): Flow<LongLat> =
        fetchLongLat().map {
            LongLat(
                longitude = it.longitude,
                latitude = it.latitude
            )
        }

    @SuppressLint("MissingPermission")
    @Throws
    private fun fetchLongLat(): Flow<Location> {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_REQUEST_INTERVAL_MILLIS,
        ).setMinUpdateDistanceMeters(LOCATION_REQUEST_MIN_UPDATE_DISTANCE_METERS).build()
        return callbackFlow {
            if (locationPermissionManager.hasLocationPermission().not()) throw Exception("Location permission is not granted!")
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.lastLocation?.let { location ->
                        launch {
                            send(location)
                        }
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper(),
            )

            awaitClose {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}