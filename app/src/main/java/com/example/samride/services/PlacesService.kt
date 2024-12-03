package com.example.samride.services;

import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlacesService {
    public List<AutocompletePrediction> getPredictions(String query, PlacesClient placesClient) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build();
        CompletableFuture<List<AutocompletePrediction>> future = new CompletableFuture<>();
        placesClient
                .findAutocompletePredictions(request)
                .addOnSuccessListener(response -> {
                    future.complete(response.getAutocompletePredictions());
                })
                .addOnFailureListener(future::completeExceptionally);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return Collections.emptyList();
        }
    }
}