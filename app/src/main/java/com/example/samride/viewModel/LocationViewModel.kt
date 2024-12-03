package com.example.samride.viewModel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    private var _fusedLocationClient: FusedLocationProviderClient? = null

    private fun initLocationClient(context: Context) {
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastKnownLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
        if (_fusedLocationClient == null) {
            initLocationClient(context)
        }

        viewModelScope.launch {
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
                return@launch
            }

            _fusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    onLocationReceived(task.result)
                } else {
                    onLocationReceived(null)
                }
            }
        }
    }
}