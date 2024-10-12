package com.yasserakbbach.composemaps.ui.accesslocation

sealed interface AccessLocationEvent {
    data object OnOk : AccessLocationEvent
    data object OnLocationPermissionGranted : AccessLocationEvent
    data object OnLocationPermissionRejected : AccessLocationEvent
}