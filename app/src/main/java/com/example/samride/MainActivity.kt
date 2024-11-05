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
import com.example.samride.ui.HomeScreen
import com.example.samride.ui.LoginScreen
import com.example.samride.ui.RegisterScreen
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vérifiez que l'API Google Places est initialisée
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        }
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

    NavHost(navController, startDestination = "bookSam") { //put login
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("bookSam") { BookSamScreen(navController) }

    }
}