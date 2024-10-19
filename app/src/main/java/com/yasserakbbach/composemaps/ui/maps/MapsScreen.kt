package com.yasserakbbach.composemaps.ui.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.yasserakbbach.composemaps.ui.theme.Red

@Composable
fun MapsScreen(state: MapsState) {
    val position = remember { LatLng(state.latitude, state.longitude) }
    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition.fromLatLngZoom(position, 15f)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = position),
            )
        }
        state.countryNameWithCode.takeIf { it.isNotBlank() }?.let { countryNameWithCode ->
            Text(
                text = countryNameWithCode,
                modifier = Modifier.wrapContentSize()
                    .align(Alignment.BottomCenter)
                    .systemBarsPadding()
                    .background(color = Color.Black, shape = RoundedCornerShape(5.dp))
                    .padding(all = 8.dp),
                color = Color.White,
            )
        }
        if(state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Red,
            )
        }
    }
}

@Composable
@Preview
private fun MapsScreenPreview() {
    MapsScreen(
        state = MapsState()
    )
}