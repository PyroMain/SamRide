package com.example.samride.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun customButtonColors() = ButtonDefaults.buttonColors(
    containerColor = DarkBlue,
    contentColor = LightGrey
)