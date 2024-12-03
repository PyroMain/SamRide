package com.example.samride.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.samride.ui.components.Logo
import com.example.samride.services.UserService
import com.example.samride.viewModel.MenuViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()

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
            text = if(user.email != null) {"Bonjour ${user.email}"} else { "" },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("profile")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Profil")
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate("bookSam")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Réserver un Sam")
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    UserService().logout()
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
    }
}