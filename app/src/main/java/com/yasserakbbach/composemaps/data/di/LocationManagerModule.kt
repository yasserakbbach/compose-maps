package com.yasserakbbach.composemaps.data.di

import com.yasserakbbach.composemaps.data.repository.LocationManagerImpl
import com.yasserakbbach.composemaps.domain.repository.LocationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class LocationManagerModule {

    @Binds
    abstract fun bindLocationManager(locationManagerImpl: LocationManagerImpl): LocationManager
}