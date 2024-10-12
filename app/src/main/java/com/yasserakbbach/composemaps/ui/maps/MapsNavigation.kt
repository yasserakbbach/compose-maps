package com.yasserakbbach.composemaps.ui.maps

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MapsRoute

fun NavGraphBuilder.mapsScreen() {
    composable<MapsRoute> {
        MapsScreen()
    }
}