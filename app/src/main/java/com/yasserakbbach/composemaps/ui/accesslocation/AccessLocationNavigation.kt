package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AccessLocationRoute

fun NavGraphBuilder.accessLocationScreen() {
    composable<AccessLocationRoute> {
        val viewModel = hiltViewModel<AccessLocationViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        AccessLocationScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}