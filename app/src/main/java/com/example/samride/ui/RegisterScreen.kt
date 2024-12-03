package com.example.samride.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.samride.ui.components.Logo
import com.example.samride.ui.components.ReturnButton
import com.example.samride.enums.UserRole
import com.example.samride.viewModel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReturnButton(navController)

        Logo()
        Spacer(modifier = Modifier.height(48.dp))

        Text("Inscription", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = { Text("Confirmer le mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Allez vous être un Sam ?", style = MaterialTheme.typography.bodyMedium)
            Checkbox(
                checked = selectedRole == UserRole.SAM,
                onCheckedChange = {
                    viewModel.onRoleSelected(if (it) UserRole.SAM else UserRole.USER)
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (
                viewModel.isValidEmail(email)
                || password == confirmPassword
            ) {
                viewModel.register(navController)
            } else {
                print("L'adresse e-mail est mal formatée ou le mot de passe est incorrect")
            }
        }) {
            Text("S'inscrire")
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Déjà un compte ? Connectez-vous")
        }
    }
}