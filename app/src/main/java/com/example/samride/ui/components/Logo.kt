package com.example.samride.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = com.example.samride.R.drawable.logo),
        contentDescription = "Logo SamRide",
        modifier = Modifier
        .size(100.dp)
        .clip(RoundedCornerShape(8.dp))
    )
}