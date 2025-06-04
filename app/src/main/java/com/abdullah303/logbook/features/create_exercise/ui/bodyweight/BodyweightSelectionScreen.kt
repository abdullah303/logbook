package com.abdullah303.logbook.features.create_exercise.ui.bodyweight

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.abdullah303.logbook.core.components.ValueSelectionScreen

@Composable
fun BodyweightSelectionScreen(
    navController: NavController,
    selectedPercentage: String = "",
    onPercentageSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    ValueSelectionScreen(
        navController = navController,
        title = "Bodyweight Contribution",
        selectedValue = selectedPercentage,
        unit = "%",
        startValue = 0,
        endValue = 100,
        step = 5,
        onValueSelected = onPercentageSelected,
        modifier = modifier
    )
} 