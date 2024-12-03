package com.example.samride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.samride.ui.BookSamScreen
import com.example.samride.ui.theme.SamRideTheme
import com.example.samride.ui.MenuScreen
import com.example.samride.ui.LoginScreen
import com.example.samride.ui.ProfileScreen
import com.example.samride.ui.RegisterScreen
import com.example.samride.viewModel.LocationViewModel
import com.example.samride.viewModel.LoginViewModel
import com.example.samride.viewModel.MenuViewModel
import com.example.samride.viewModel.ProfileViewModel
import com.example.samride.viewModel.RegisterViewModel
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the SDK with the Google Maps Platform API key
        Places.initialize(this, BuildConfig.GOOGLE_MAPS_API_KEY)

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    SamRideTheme {
        AppNavigator()
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, LoginViewModel()) }
        composable("register") { RegisterScreen(navController, RegisterViewModel()) }
        composable("bookSam") { BookSamScreen(navController, LocationViewModel()) }
        composable("home") { MenuScreen(navController, MenuViewModel()) }
        composable("profile") { ProfileScreen(navController, ProfileViewModel()) }
    }
}