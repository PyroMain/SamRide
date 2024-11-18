package com.example.samride.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.samride.services.PlacesService
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchModalContent(sheetState: BottomSheetScaffoldState): @Composable() (ColumnScope.() -> Unit) {
    val coroutinesScope = rememberCoroutineScope()
    val placesClient: PlacesClient = Places.createClient(LocalContext.current)
    val placesService = PlacesService()

    var searchQuery by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf(emptyList<AutocompletePrediction>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    coroutinesScope.launch {
                        if (focusState.isFocused) {
                            sheetState.bottomSheetState.expand()
                        }
                    }
                },
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                coroutinesScope.launch {
                    predictions = withContext(Dispatchers.IO) {
                        placesService.getPredictions(query, placesClient)
                    }
                }
            },
            label = {
                Row {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Text("Rechercher un lieu")
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
        )

        LazyColumn {
            items(predictions) { prediction ->
                ListItem(
                    modifier = Modifier.clickable {},
                    headlineContent = {
                        Text(
                            prediction.getPrimaryText(null).toString()
                        )
                    },
                    overlineContent = {
                        Text(
                            prediction.getSecondaryText(null).toString()
                        )
                    }
                )
            }
        }
    }

    return {}
}