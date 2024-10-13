package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yasserakbbach.composemaps.domain.model.LongLat
import com.yasserakbbach.composemaps.ui.util.ObserverAsActions
import kotlinx.serialization.Serializable

@Serializable
data object AccessLocationRoute

fun NavGraphBuilder.accessLocationScreen(
    navigateToMaps: (longLat: LongLat) -> Unit,
) {
    composable<AccessLocationRoute> {
        val viewModel = hiltViewModel<AccessLocationViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserverAsActions(viewModel.action) { action ->
            when (action) {
                is AccessLocationAction.NavigateToMaps -> navigateToMaps(action.longLat)
            }
        }

        AccessLocationScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}