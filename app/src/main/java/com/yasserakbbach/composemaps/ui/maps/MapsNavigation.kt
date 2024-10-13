package com.yasserakbbach.composemaps.ui.maps

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MapsRoute(val longitude: Double, val latitude: Double)

fun NavGraphBuilder.mapsScreen() {
    composable<MapsRoute> {
        val viewModel = hiltViewModel<MapsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        MapsScreen(state = state)
    }
}