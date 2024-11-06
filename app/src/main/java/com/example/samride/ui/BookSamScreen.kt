package com.example.samride.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.RectangularBounds

data class Location(var latitude: Double, var longitude: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSamScreen(navController: NavController) {
    var locationDestination by remember { mutableStateOf(Location(-100.0, -100.0)) }
    var locationDepart by remember { mutableStateOf(Location(-100.0, 1-00.0)) }
    var locationDestinationText by remember { mutableStateOf("") }
    var locationDepartText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("En attente de localisation...") }
    var predictions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isTypingInDepartField by remember { mutableStateOf(false) }
    var isTypingInDestinationField by remember { mutableStateOf(false) }
    var samClicked by remember { mutableStateOf(false) }

    // Animation de décalage pour chaque TextField
    val offsetYDestination by animateDpAsState(if (isTypingInDestinationField) -16.dp else 0.dp)
    val context = LocalContext.current
    val placesClient: PlacesClient = Places.createClient(context)
    val token = AutocompleteSessionToken.newInstance()


    // Fonction pour chercher des prédictions d'adresses avec la localisation actuelle
    fun fetchPlacePredictions(query: String, location: Location) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(token)
            .setQuery(query)
            .setLocationBias(
                RectangularBounds.newInstance(
                    LatLng(location.latitude - 0.05, location.longitude - 0.05), // Point sud-ouest
                    LatLng(location.latitude + 0.05, location.longitude + 0.05)  // Point nord-est
                )
            )
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                predictions = response.autocompletePredictions.map { it.getFullText(null).toString() }
            }
            .addOnFailureListener { exception ->
                predictions = listOf("Erreur : ${exception.message}")
            }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(48.8566, 2.3522),
            10f
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getUserLocation(context) { latitude, longitude ->
                locationDepart.latitude = latitude
                locationDepart.longitude = longitude
                locationText = "Coordonnées récupérées : latitude: ${locationDepart.latitude} longitude: ${locationDepart.longitude}"
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(latitude, longitude), 15f
                )
            }
        } else {
            locationText = "Permission refusée"
        }
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUserLocation(context) { latitude, longitude ->
                locationDepart.latitude = latitude
                locationDepart.longitude = longitude
                locationText = "Coordonnées récupérées : latitude: ${locationDepart.latitude} longitude: ${locationDepart.longitude}"
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(latitude, longitude), 15f
                )
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Carte en arrière-plan
        Log.d("BookSamCourse", "lat :${locationDepart.latitude}")
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            if (locationDepart.latitude != -100.0 && locationDepart.longitude != -100.0) {
                Marker(
                    state = com.google.maps.android.compose.MarkerState(
                        position = LatLng(locationDepart.latitude, locationDepart.longitude)
                    ),
                    title = "Moi"
                )
            }
            if (locationDestination.latitude != -100.0 && locationDestination.longitude != -100.0){
                Marker(
                    state = com.google.maps.android.compose.MarkerState(
                        position = LatLng(locationDestination.latitude, locationDestination.longitude)
                    ),
                    title = "déstination"
                )
            }
            // Tracé entre le point de départ et la destination
            if (samClicked &&
                locationDepart.latitude != -100.0 && locationDepart.longitude != -100.0 &&
                locationDestination.latitude != -100.0 && locationDestination.longitude != -100.0) {
                Polyline(
                    points = listOf(
                        LatLng(locationDepart.latitude, locationDepart.longitude),
                        LatLng(locationDestination.latitude, locationDestination.longitude)
                    ),
                    color = Color.Blue, // Couleur du tracé
                    width = 5f // Largeur du tracé
                )
            }else if (samClicked &&
            locationDestination.latitude == -100.0 && locationDestination.longitude == -100.0) {
                // cas où on doit lancer le tracer depuis la loc du
            // tel jusqu'a son domicile qui est set dans son profil (bdd)
            }
        }

        // Superposition grise si l'utilisateur tape dans le premier champ
        if (isTypingInDepartField or isTypingInDestinationField) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)) // Couleur grise semi-transparente
                    .clickable {
                        isTypingInDepartField = false
                        isTypingInDestinationField = false
                    } // Permet de désactiver en cliquant en dehors
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Masquer le premier TextField si on tape dans le deuxième
            if (!isTypingInDestinationField){
                TextField(
                    value = locationDepartText,
                    onValueChange = {
                        locationDepartText = it
                        fetchPlacePredictions(it, locationDepart)
                        isTypingInDepartField = true
                        isTypingInDestinationField = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(20.dp))
                        .onFocusChanged { focusState ->
                            isTypingInDepartField = focusState.isFocused
                            if (focusState.isFocused) isTypingInDestinationField = false
                        },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Ma position") }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Masquer le deuxième TextField si on tape dans le premier
            if (!isTypingInDepartField) {
                TextField(
                    value = locationDestinationText,
                    onValueChange = {
                        locationDestinationText = it
                        fetchPlacePredictions(it, locationDestination)
                        isTypingInDestinationField = true
                        isTypingInDepartField = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(20.dp))
                        .offset(y = offsetYDestination) // Utilisation de l'offset pour animer la position
                        .onFocusChanged { focusState ->
                            isTypingInDestinationField = focusState.isFocused
                            if (focusState.isFocused) isTypingInDepartField = false
                        },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Où allons nous ?") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Text(locationText) // debug
            }

            // Afficher les prédictions de lieu
            if (predictions.isNotEmpty()) {
                LazyColumn {
                    items(predictions) { prediction ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 1.dp) // Espace vertical entre les éléments
                                .clickable {
                                    if (isTypingInDepartField) {
                                        locationDepartText = prediction
                                        geocodePlace(prediction, context) { latitude, longitude ->
                                            locationDepart.latitude = latitude
                                            locationDepart.longitude = longitude
                                            cameraPositionState.position =
                                                CameraPosition.fromLatLngZoom(
                                                    LatLng(latitude, longitude), 15f
                                                )
                                        }
                                    } else if (isTypingInDestinationField) {
                                        locationDestinationText = prediction
                                        geocodePlace(prediction, context) { latitude, longitude ->
                                            locationDestination.latitude = latitude
                                            locationDestination.longitude = longitude
                                            cameraPositionState.position =
                                                CameraPosition.fromLatLngZoom(
                                                    LatLng(latitude, longitude), 15f
                                                )
                                        }

                                    }
                                    isTypingInDepartField = false // Désactive le mode édition
                                    isTypingInDestinationField = false
                                    predictions =
                                        emptyList() // Cache les prédictions après sélection
                                },
                            shape = RoundedCornerShape(8.dp), // Coins arrondis
                            border = BorderStroke(1.dp, Color.LightGray) // Bordure légère
                        ) {
                            // Contenu de la carte
                            Text(
                                text = prediction,
                                modifier = Modifier.padding(16.dp), // Padding interne de la carte
                                style = MaterialTheme.typography.bodyLarge, // Style de texte
                                overflow = TextOverflow.Ellipsis, // Texte qui dépasse est coupé
                                maxLines = 1 // Limite le texte à une ligne
                            )
                        }
                    }
                }
            }
        }

        // Bouton flottant
        FloatingActionButton(
            onClick = { samClicked = true},
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(30.dp),
            containerColor = Color.Cyan
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
            onLocationResult(-100.0, -100.0) // Valeurs par défaut si aucune localisation n'est trouvée
        }
    }
}

fun geocodePlace(placeName: String, context: Context, onCoordinatesResult: (Double, Double) -> Unit) {
    val geocoder = Geocoder(context)
    val addresses = geocoder.getFromLocationName(placeName, 1)
    if (addresses != null && addresses.isNotEmpty()) {
        val location = addresses[0]
        onCoordinatesResult(location.latitude, location.longitude)
    } else {
        // Si aucune adresse n'est trouvée, vous pouvez gérer une réponse alternative
        onCoordinatesResult(-100.0, -100.0)
    }
}