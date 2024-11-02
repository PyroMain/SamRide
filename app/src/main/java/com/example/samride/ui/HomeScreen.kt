package com.example.samride.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.samride.auth.FirebaseAuthHelper

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val currentUser = FirebaseAuthHelper.getCurrentUser()
    val userName = currentUser?.email ?: "Utilisateur"
    val isUserLoggedIn = currentUser != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isUserLoggedIn) "Bienvenue, $userName!" else "Bienvenue sur SamRide!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (isUserLoggedIn) {
            Button(
                onClick = { /* Navigation vers l'écran de réservation */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Réserver un Sam")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Navigation vers l'historique des courses */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Historique des courses")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Navigation vers le support */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Support")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (isUserLoggedIn) {
            OutlinedButton(
                onClick = {
                    FirebaseAuthHelper.logoutUser()
                    Toast.makeText(context, "Déconnecté avec succès", Toast.LENGTH_SHORT).show()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Se déconnecter")
            }
        } else {
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Se connecter")
            }
        }
    }
}