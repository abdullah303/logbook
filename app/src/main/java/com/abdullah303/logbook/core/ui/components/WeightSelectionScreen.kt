package com.abdullah303.logbook.core.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlin.math.abs

@Composable
fun WeightSelectionScreen(
    navController: NavController,
    min: String,
    max: String,
    increment: String,
    unit: String,
    onValueSelected: (String) -> Unit
) {
    val minValue = min.toFloatOrNull() ?: 0f
    val maxValue = max.toFloatOrNull() ?: 1000f
    val incrementValue = increment.toFloatOrNull() ?: 2.5f

    // Calculate decimal places from increment
    val decimalPlaces = incrementValue.toString().let { str ->
        if (str.contains(".")) {
            str.substringAfter(".").length
        } else {
            0
        }
    }

    // Generate value options from start to end in step increments
    val valueOptions = generateSequence(minValue) { it + incrementValue }
        .takeWhile { it <= maxValue }
        .map { 
            String.format("%.${decimalPlaces}f", it)
        }
        .toList()

    ValueSelectionScreen(
        navController = navController,
        title = "Select Weight",
        selectedValue = "",
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onValueSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set("selected_value", selectedValue)
            navController.popBackStack()
        }
    )
} 