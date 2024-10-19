package com.yasserakbbach.composemaps.ui.maps

data class MapsState(
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val loading: Boolean = true,
    val countryNameWithCode: String = "",
)
