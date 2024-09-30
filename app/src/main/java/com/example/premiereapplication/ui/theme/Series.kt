package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

@Composable
fun Series() {
    Text(
        text = "Page pour les series",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(10.dp)
    )
}