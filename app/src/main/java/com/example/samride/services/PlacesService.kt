package com.example.samride.services

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

class PlacesService {
    fun getPredictions(query: String?, placesClient: PlacesClient): List<AutocompletePrediction> {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()
        val future = CompletableFuture<List<AutocompletePrediction>>()
        placesClient
            .findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                future.complete(response.autocompletePredictions)
            }
            .addOnFailureListener { throwable: Exception? ->
                future.completeExceptionally(
                    throwable
                )
            }
        return try {
            future.get()
        } catch (e: InterruptedException) {
            emptyList()
        } catch (e: ExecutionException) {
            emptyList()
        }
    }
}