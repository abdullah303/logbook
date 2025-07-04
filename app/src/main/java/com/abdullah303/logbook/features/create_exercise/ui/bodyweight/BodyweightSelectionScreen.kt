package com.abdullah303.logbook.features.create_exercise.ui.bodyweight

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.abdullah303.logbook.core.ui.components.ValueSelectionScreen

@Composable
fun BodyweightSelectionScreen(
    navController: NavController,
    onPercentageSelected: (Float) -> Unit
) {
    // Get the current value from the saved state handle as a string and convert to float
    val currentValueStr = navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedBodyweightPercentage") ?: "0"
    val currentValue = currentValueStr.toFloatOrNull() ?: 0f

    // Generate percentage options from 0 to 100 in 5% increments
    val valueOptions = (0..100 step 5).map { it.toFloat() }

    ValueSelectionScreen(
        navController = navController,
        title = "Select Bodyweight Percentage",
        selectedValue = currentValue,
        unit = "%",
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onPercentageSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "selectedBodyweightPercentage",
                selectedValue.toString()
            )
            navController.popBackStack()
        }
    )
} 