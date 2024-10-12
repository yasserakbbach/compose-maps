package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AccessLocationRoute

fun NavGraphBuilder.accessLocationScreen() {
    composable<AccessLocationRoute> {
        AccessLocationScreen()
    }
}