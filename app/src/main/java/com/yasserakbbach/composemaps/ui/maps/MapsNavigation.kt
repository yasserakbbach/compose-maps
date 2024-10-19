package com.yasserakbbach.composemaps.ui.maps

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

@Serializable
data class MapsRoute(val longitude: Double, val latitude: Double)
private const val url = "composemaps.com"

fun NavGraphBuilder.mapsScreen() {
    composable<MapsRoute>(
        deepLinks = listOf(
            navDeepLink<MapsRoute>(
                basePath = url
            )
        )
    ) {
        val viewModel = hiltViewModel<MapsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        MapsScreen(state = state)
    }
}