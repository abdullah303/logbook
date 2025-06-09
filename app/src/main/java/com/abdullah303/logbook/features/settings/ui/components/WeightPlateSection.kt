package com.abdullah303.logbook.features.settings.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeightPlateSection(
    modifier: Modifier = Modifier
) {
    val plates = listOf(1.25f, 2.5f, 5.0f, 10.0f, 15.0f, 20.0f, 25.0f)
    var enabledPlates by remember { mutableStateOf(plates.associateWith { false }) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        WeightPlateSelector(
            enabledPlates = enabledPlates,
            onPlateToggle = { weight ->
                enabledPlates = enabledPlates.toMutableMap().apply {
                    put(weight, !(get(weight) ?: false))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
} 