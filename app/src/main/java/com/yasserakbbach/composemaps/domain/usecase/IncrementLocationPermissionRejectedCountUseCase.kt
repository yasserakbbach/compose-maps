package com.yasserakbbach.composemaps.domain.usecase

import com.yasserakbbach.composemaps.domain.repository.UserPreferencesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class IncrementLocationPermissionRejectedCountUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke() =
        userPreferencesRepository.incrementLocationPermissionRejectedCount()
}