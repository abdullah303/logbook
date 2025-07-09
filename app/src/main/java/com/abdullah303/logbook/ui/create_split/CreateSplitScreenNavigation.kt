package com.abdullah303.logbook.ui.create_split

import androidx.compose.runtime.Composable

@Composable
fun CreateSplitScreenNavigation(
    onNavigateBack: () -> Unit,
    onNavigateToCreateExercise: () -> Unit
): CreateSplitScreenNavigationState {
    return CreateSplitScreenNavigationState(
        onNavigateBack = onNavigateBack,
        onNavigateToCreateExercise = onNavigateToCreateExercise
    )
}

data class CreateSplitScreenNavigationState(
    val onNavigateBack: () -> Unit,
    val onNavigateToCreateExercise: () -> Unit
) 