package com.example.samride.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.samride.ui.components.Map
import com.example.samride.ui.components.searchModalContent
import com.example.samride.viewModel.LocationViewModel
import com.example.samride.viewModel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSamScreen(navController: NavController, viewModel: LocationViewModel) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetScaffoldState()

    var latitude by remember { mutableDoubleStateOf(48.864716) }
    var longitude by remember { mutableDoubleStateOf(2.349014) }

    LaunchedEffect(Unit) {
        viewModel.getLastKnownLocation(context) { location ->
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
                searchModalContent(sheetState, PlacesViewModel(context))
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