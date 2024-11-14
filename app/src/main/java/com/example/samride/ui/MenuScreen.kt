package com.example.samride.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.samride.auth.FirebaseAuthHelper
import com.example.samride.components.Logo

@Composable
fun MenuScreen(navController: NavController) {
    val currentUser = FirebaseAuthHelper.getCurrentUser()
    val userName = currentUser?.email ?: "Utilisateur"
    val isUserLoggedIn = currentUser != null

    @Composable
    fun actionButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text)
        }
    }

    @Composable
    fun logoutButton(navController: NavController) {
        val context = LocalContext.current
        OutlinedButton(
            onClick = {
                FirebaseAuthHelper.logoutUser()
                Toast.makeText(context, "Déconnecté avec succès", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Se déconnecter")
        }
    }
    
    @Composable
    fun userLoggedInActions(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            actionButton(text = "Réserver un Sam", onClick = { navController.navigate("bookSam") })
            Spacer(modifier = Modifier.height(12.dp))
            logoutButton(navController)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = if (isUserLoggedIn) "Bienvenue, $userName!" else "Bienvenue sur SamRide!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        userLoggedInActions(navController)
    }
}