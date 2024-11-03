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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSamScreen(navController: NavController) {
    var destination by remember { mutableStateOf("") }
    var depart by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("En attente de localisation...") }

    val context = LocalContext.current

    // Lanceur pour la demande de permission
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getUserLocation(context) { latitude, longitude ->
                depart = "Latitude: $latitude, Longitude: $longitude"
                locationText = "Coordonnées récupérées"
            }
        } else {
            locationText = "Permission refusée"
        }
    }

    // Vérifier et demander la permission
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUserLocation(context) { latitude, longitude ->
                depart = "Latitude: $latitude, Longitude: $longitude"
                locationText = "Coordonnées récupérées"
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = destination,
                onValueChange = { destination = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Destination") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = depart,
                onValueChange = { depart = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Départ") }
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
                .align(Alignment.BottomEnd)
                .padding(16.dp),
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
            onLocationResult(0.0, 0.0) // Valeurs par défaut si aucune localisation n'est trouvée
        }
    }
}