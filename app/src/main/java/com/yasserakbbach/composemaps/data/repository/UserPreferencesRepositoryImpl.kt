package com.yasserakbbach.composemaps.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.yasserakbbach.composemaps.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>,
) : UserPreferencesRepository {

    override val shouldLocationPermissionRequestedFromSettings: Flow<Boolean>
        get() = userDataStorePreferences.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.mapNotNull { preferences ->
            (preferences[LOCATION_PERMISSION_REJECTED_COUNT_KEY] ?: 0) >= MAX_LOCATION_PERMISSION_REJECTED_COUNT
        }.filterNotNull()

    override suspend fun incrementLocationPermissionRejectedCount() {
        userDataStorePreferences.edit { preferences ->
            preferences[LOCATION_PERMISSION_REJECTED_COUNT_KEY] = preferences[LOCATION_PERMISSION_REJECTED_COUNT_KEY]?.inc() ?: 1
        }
    }

    private companion object {
        val LOCATION_PERMISSION_REJECTED_COUNT_KEY = intPreferencesKey("LOCATION_PERMISSION_REJECTED_COUNT_KEY")
        const val MAX_LOCATION_PERMISSION_REJECTED_COUNT = 2
    }
}