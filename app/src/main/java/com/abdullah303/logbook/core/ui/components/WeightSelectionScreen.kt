package com.abdullah303.logbook.core.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.abdullah303.logbook.core.ui.components.ValueSelectionScreen

@Composable
fun WeightSelectionScreen(
    navController: NavController,
    min: String,
    max: String,
    interval: String,
    unit: String,
    onWeightSelected: (String) -> Unit
) {
    // Generate weight options from min to max in interval increments
    val minValue = min.toFloatOrNull() ?: 0f
    val maxValue = max.toFloatOrNull() ?: 100f
    val intervalValue = interval.toFloatOrNull() ?: 5f
    val valueOptions = generateSequence(minValue) { it + intervalValue }
        .takeWhile { it <= maxValue }
        .map { it.toString() }
        .toList()

    ValueSelectionScreen(
        navController = navController,
        title = "Select Weight",
        selectedValue = min,
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onWeightSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set("selectedWeight", selectedValue)
            navController.popBackStack()
        }
    )
} 