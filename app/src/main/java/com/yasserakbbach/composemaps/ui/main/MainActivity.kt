package com.yasserakbbach.composemaps.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.yasserakbbach.composemaps.ui.accesslocation.AccessLocationRoute
import com.yasserakbbach.composemaps.ui.theme.ComposeMapsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        setContent {
            val navHostController = rememberNavController()
            ComposeMapsTheme {
                ComposeMapsApp(
                    navController = navHostController,
                    startDestination = AccessLocationRoute,
                )
            }
        }
    }
}
