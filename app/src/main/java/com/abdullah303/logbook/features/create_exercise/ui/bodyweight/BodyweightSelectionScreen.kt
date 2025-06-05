package com.abdullah303.logbook.features.create_exercise.ui.bodyweight

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.abdullah303.logbook.core.ui.components.ValueSelectionScreen

@Composable
fun BodyweightSelectionScreen(
    navController: NavController,
    onPercentageSelected: (String) -> Unit
) {
    // Generate percentage options from 0 to 100 in 5% increments
    val valueOptions = (0..100 step 5).map { "$it" }

    ValueSelectionScreen(
        navController = navController,
        title = "Select Bodyweight Percentage",
        selectedValue = "0",
        unit = "%",
        valueOptions = valueOptions,
        onValueSelected = { selectedValue ->
            onPercentageSelected(selectedValue)
            navController.previousBackStackEntry?.savedStateHandle?.set("selectedBodyweightPercentage", selectedValue)
            navController.popBackStack()
        }
    )
} 