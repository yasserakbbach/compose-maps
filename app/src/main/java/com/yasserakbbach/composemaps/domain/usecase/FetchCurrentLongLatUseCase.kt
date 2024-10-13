package com.yasserakbbach.composemaps.domain.usecase

import com.yasserakbbach.composemaps.domain.model.LongLat
import com.yasserakbbach.composemaps.domain.repository.LocationManager
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class FetchCurrentLongLatUseCase @Inject constructor(
    private val locationManager: LocationManager,
) {
    operator fun invoke(): Flow<LongLat> =
        locationManager.fetchCurrentLongLat()
}