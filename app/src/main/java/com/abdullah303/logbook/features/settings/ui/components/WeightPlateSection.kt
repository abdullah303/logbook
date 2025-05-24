package com.abdullah303.logbook.features.settings.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.settings.ui.components.WeightPlateSelector

@Composable
fun WeightPlateSection(
    modifier: Modifier = Modifier
) {
    val plates = listOf(1.25, 2.5, 5.0, 10.0, 15.0, 20.0, 25.0)
    var enabledPlates by remember { mutableStateOf(plates.map { it to false }.toMap()) }

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