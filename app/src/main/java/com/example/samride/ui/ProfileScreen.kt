package com.example.samride.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.samride.ui.components.ReturnButton
import com.example.samride.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ReturnButton(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Profil",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.Start),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.firstName.value,
                onValueChange = { viewModel.onFirstNameChange(it) },
                label = { Text("Prénom") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.lastName.value,
                onValueChange = { viewModel.onLastNameChange(it) },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.age.intValue.toString(),
                    onValueChange = { viewModel.onAgeChange(it.toIntOrNull() ?: 0) },
                    label = { Text("Âge") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = viewModel.picture.value,
                    onValueChange = { viewModel.onPictureChange(it) },
                    label = { Text("Photo") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.drivingLicenseNumber.value,
                onValueChange = { viewModel.onDrivingLicenseNumberChange(it) },
                label = { Text("Numéro de permis de conduire") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedTextField(
                value = viewModel.drivingLicense.value,
                onValueChange = { viewModel.onDrivingLicenseChange(it) },
                label = { Text("Permis de conduire") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                ) {
                    Text(text = "Annuler")
                }

                Button(
                    onClick = {
                        coroutineScope.launch { viewModel.update() }
                        navController.popBackStack()
                    },
                ) {
                    Text(text = "Enregistrer")
                }
            }
        }
    }
}
