package com.yasserakbbach.composemaps.data.di

import com.yasserakbbach.composemaps.data.repository.UserPreferencesRepositoryImpl
import com.yasserakbbach.composemaps.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class UserPreferencesModule {

    @Binds
    abstract fun bindUserPreferencesRepository(userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}