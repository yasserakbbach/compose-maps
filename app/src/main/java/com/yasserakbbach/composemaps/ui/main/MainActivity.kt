package com.yasserakbbach.composemaps.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.yasserakbbach.composemaps.ui.theme.ComposeMapsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setContent {
            val navHostController = rememberNavController()
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition {
                state.startDestination == null
            }

            ComposeMapsTheme {
                state.startDestination?.let { startDestination ->
                    ComposeMapsApp(
                        navController = navHostController,
                        startDestination = startDestination,
                    )
                }
            }
        }
    }
}
