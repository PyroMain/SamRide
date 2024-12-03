package com.example.samride.viewModel

import androidx.lifecycle.ViewModel
import com.example.samride.services.PlacesService
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.android.libraries.places.api.Places
import android.content.Context
import android.util.Log

class PlacesViewModel(context: Context) : ViewModel() {
    private var _query = MutableStateFlow("")
    private val placesClient: PlacesClient = Places.createClient(context)

    val query: StateFlow<String> = _query

    fun onQueryChanged(newQuery: String) {
        Log.d("PlacesViewModel", "Query before: $newQuery")
        _query.value = newQuery
        Log.d("PlacesViewModel", "Query after: ${_query.value}")
    }

    fun getPlaces(): List<AutocompletePrediction> {
        return PlacesService().getPredictions(_query.value, placesClient)
    }
}