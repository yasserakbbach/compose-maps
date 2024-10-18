package com.yasserakbbach.composemaps.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val shouldLocationPermissionRequestedFromSettings: Flow<Boolean>
    suspend fun incrementLocationPermissionRejectedCount()
}