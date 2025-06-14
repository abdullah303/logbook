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
    onWeightSelected: (Float) -> Unit
) {
    // Default implementation for backward compatibility
    val minValue = min.toFloatOrNull() ?: 0f
    val maxValue = max.toFloatOrNull() ?: 100f
    val intervalValue = interval.toFloatOrNull() ?: 5f
    val valueOptions = generateSequence(minValue) { it + intervalValue }
        .takeWhile { it <= maxValue }
        .toList()

    val defaultSelectedValue = valueOptions.firstOrNull() ?: 0f

    ValueSelectionScreen(
        navController = navController,
        title = "Select Weight",
        selectedValue = defaultSelectedValue,
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onWeightSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "selectedWeight",
                selectedValue
            )
            navController.popBackStack()
        }
    )
}

@Composable
fun MinWeightSelectionScreen(
    navController: NavController,
    max: String,
    step: String,
    unit: String,
    currentValue: String,
    onWeightSelected: (Float) -> Unit
) {
    // Min range: 0 to current max value, using step increment
    val maxValue = max.toFloatOrNull() ?: 100f
    val stepValue = step.toFloatOrNull() ?: 5f
    val valueOptions = generateSequence(0f) { it + stepValue }
        .takeWhile { it <= maxValue }
        .toList()

    ValueSelectionScreen(
        navController = navController,
        title = "Select Minimum Weight",
        selectedValue = currentValue.toFloatOrNull() ?: 0f,
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onWeightSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "selectedWeight",
                selectedValue
            )
            navController.popBackStack()
        }
    )
}

@Composable
fun MaxWeightSelectionScreen(
    navController: NavController,
    min: String,
    step: String,
    unit: String,
    currentValue: String,
    onWeightSelected: (Float) -> Unit
) {
    // Max range: current min value to 1000, using step increment
    val minValue = min.toFloatOrNull() ?: 0f
    val stepValue = step.toFloatOrNull() ?: 5f
    val valueOptions = generateSequence(minValue) { it + stepValue }
        .takeWhile { it <= 1000f }
        .toList()

    ValueSelectionScreen(
        navController = navController,
        title = "Select Maximum Weight",
        selectedValue = currentValue.toFloatOrNull() ?: minValue,
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onWeightSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "selectedWeight",
                selectedValue
            )
            navController.popBackStack()
        }
    )
}

@Composable
fun StepWeightSelectionScreen(
    navController: NavController,
    unit: String,
    currentValue: String,
    onWeightSelected: (Float) -> Unit
) {
    // Step range: 0 to 50 in 0.5 increments
    val valueOptions = generateSequence(0f) { it + 0.5f }
        .takeWhile { it <= 50f }
        .toList()

    ValueSelectionScreen(
        navController = navController,
        title = "Select Step Increment",
        selectedValue = currentValue.toFloatOrNull(),
        unit = unit,
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onWeightSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "selectedWeight",
                selectedValue
            )
            navController.popBackStack()
        }
    )
} 