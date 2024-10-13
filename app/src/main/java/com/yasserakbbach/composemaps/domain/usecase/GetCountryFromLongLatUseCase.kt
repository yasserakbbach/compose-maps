package com.yasserakbbach.composemaps.domain.usecase

import com.yasserakbbach.composemaps.domain.model.LongLat
import com.yasserakbbach.composemaps.domain.repository.LocationManager
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCountryFromLongLatUseCase @Inject constructor(
    private val locationManager: LocationManager,
) {
    suspend operator fun invoke(longLat: LongLat?) =
        longLat?.let { locationManager.getCountryFromLongLat(it) }
}