package com.example.samride.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.samride.components.Map
import com.example.samride.components.searchModalContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSamScreen(navController: NavController) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetScaffoldState()

    var latitude by remember { mutableDoubleStateOf(48.864716) }
    var longitude by remember { mutableDoubleStateOf(2.349014) }

    fun getLastKnownLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            return
        }

        fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful && task.result != null) {
                onLocationReceived(task.result)
            } else {
                onLocationReceived(null)
            }
        }
    }

    LaunchedEffect(Unit) {
        getLastKnownLocation(context) { location ->
            location?.let {
                latitude = it.latitude
                longitude = it.longitude
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                searchModalContent(sheetState)
            }
        },
        sheetPeekHeight = 150.dp,
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Map(latitude, longitude)

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("home") },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
                FloatingActionButton(
                    onClick = { },
                ) {
                    Text("Sam !")
                }
            }
        }
    }
}