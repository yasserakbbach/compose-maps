package com.yasserakbbach.composemaps.manager

import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationPermissionManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun hasLocationPermission() =
        context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}