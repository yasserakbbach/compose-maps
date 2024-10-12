package com.yasserakbbach.composemaps.ui.main

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yasserakbbach.composemaps.ui.accesslocation.accessLocationScreen
import com.yasserakbbach.composemaps.ui.maps.mapsScreen

@Composable
fun ComposeMapsApp(
    navController: NavHostController,
    startDestination: Any,
) {
    NavHost(
        modifier = Modifier.navigationBarsPadding().imePadding(),
        navController = navController,
        startDestination = startDestination,
    ) {
        accessLocationScreen()
        mapsScreen()
    }
}