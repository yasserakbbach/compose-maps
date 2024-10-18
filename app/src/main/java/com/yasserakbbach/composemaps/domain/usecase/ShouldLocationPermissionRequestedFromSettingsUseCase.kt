package com.yasserakbbach.composemaps.domain.usecase

import com.yasserakbbach.composemaps.domain.repository.UserPreferencesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ShouldLocationPermissionRequestedFromSettingsUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke() =
        userPreferencesRepository.shouldLocationPermissionRequestedFromSettings
}