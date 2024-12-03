package com.example.samride.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun Map(
    alatitude: Double,
    alongitude: Double
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = CameraPositionState(
            CameraPosition.Builder()
                .target(LatLng(alatitude, alongitude))
                .zoom(12f)
                .build()
        ),
    ) {
        val markerState = MarkerState(LatLng(alatitude, alongitude))
        Marker(
            state = markerState,
            title = "Vous êtes ici",
        )
    }
}