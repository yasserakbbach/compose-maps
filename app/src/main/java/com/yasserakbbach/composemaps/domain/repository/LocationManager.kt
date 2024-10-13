package com.yasserakbbach.composemaps.domain.repository

import com.yasserakbbach.composemaps.domain.model.Country
import com.yasserakbbach.composemaps.domain.model.LongLat
import kotlinx.coroutines.flow.Flow

interface LocationManager {
    fun fetchCurrentLongLat(): Flow<LongLat>
    suspend fun getCountryFromLongLat(longLat: LongLat): Country?
}