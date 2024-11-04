package com.example.samride.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

data class Location(var latitude: Double, var longitude: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSamScreen(navController: NavController) {
    var locationDestination by remember { mutableStateOf(Location(0.0, 0.0)) }
    var locationDepart by remember { mutableStateOf(Location(0.0, 0.0)) }
    var locationDestinationText by remember { mutableStateOf("") }
    var locationDepartText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("En attente de localisation...") }

    val context = LocalContext.current

    // Position de la caméra pour la carte
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            com.google.android.gms.maps.model.LatLng(48.8566, 2.3522), // Paris comme position par défaut
            10f
        )
    }

    // Lanceur pour la demande de permission
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getUserLocation(context) { latitude, longitude ->
                locationDepart.latitude = latitude
                locationDepart.longitude = longitude
                locationText = "Coordonnées récupérées : latitude: ${locationDepart.latitude} longitude: ${locationDepart.longitude}"
                // Met à jour la position de la caméra
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(latitude, longitude),
                    10f // Zoom level
                )
            }
        } else {
            locationText = "Permission refusée"
        }
    }

    // Vérifier et demander la permission
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUserLocation(context) { latitude, longitude ->
                locationDepart.latitude = latitude
                locationDepart.longitude = longitude
                locationText = "Coordonnées récupérées : latitude: ${locationDepart.latitude} longitude: ${locationDepart.longitude}"
                // Met à jour la position de la caméra
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(latitude, longitude),
                    10f // Zoom level
                )
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Mise en page de l'écran avec la carte en arrière-plan
    Box(modifier = Modifier.fillMaxSize()) {
        // Carte en arrière-plan
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )

        // Contenu au premier plan
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = locationDepartText,
                onValueChange = { locationDepartText = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ma position") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = locationDestinationText,
                onValueChange = { locationDestinationText = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Où allons nous ?") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(locationText)
        }

        // Bouton flottant en bas à droite
        FloatingActionButton(
            onClick = {
                // Action à exécuter lorsque le bouton est cliqué
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(30.dp),
            containerColor = Color.Cyan // Couleur de fond du bouton
        ) {
            Text(text = "Sam !")
        }
    }
}

// Fonction pour récupérer la position de l'utilisateur
@SuppressLint("MissingPermission") // Nous avons vérifié la permission avant d'appeler cette fonction
fun getUserLocation(context: Context, onLocationResult: (Double, Double) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocationResult(location.latitude, location.longitude)
        } else {
            onLocationResult(6.6, 6.6) // Valeurs par défaut si aucune localisation n'est trouvée
        }
    }
}
