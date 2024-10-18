package com.yasserakbbach.composemaps.ui.accesslocation

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasserakbbach.composemaps.R
import com.yasserakbbach.composemaps.ui.theme.Red

@Composable
fun AccessLocationScreen(
    state: AccessLocationState,
    event: (AccessLocationEvent) -> Unit,
) {
    val locationPermissions = arrayOf(
        ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION,
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val permissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }
            if (permissionsGranted) {
                event(AccessLocationEvent.OnLocationPermissionGranted)
            } else {
                event(AccessLocationEvent.OnLocationPermissionRejected)
            }
        })

    Scaffold(
        bottomBar = {
            if(state.allowLocationPermissionFromSettings == true) {
                AllowLocationPermissionFromSettingsBanner()
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        event(AccessLocationEvent.OnOk)
                        locationPermissionLauncher.launch(locationPermissions)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                    ),
                    enabled = state.loading.not(),
                ) {
                    when {
                        state.loading -> CircularProgressIndicator()
                        else -> Text(
                            text = stringResource(R.string.ok),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.grant_location),
                contentDescription = "Grant location permission",
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .testTag("SPACE_24_DP"),
            )
            Text(
                modifier = Modifier.padding(12.dp),
                text = stringResource(id = R.string.please_allow_location_permission_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
@Preview
private fun AccessLocationScreenPreview() {
    AccessLocationScreen(
        state = AccessLocationState(),
        event = {},
    )
}

@Composable
@Preview
private fun AllowLocationPermissionFromSettingsBanner() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(24.dp)
            .background(
                color = Red,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.please_allow_location_from_settings),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}