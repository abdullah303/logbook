package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.runtime.Composable

@Composable
fun CreateExerciseScreenNavigation(
    onNavigateBack: () -> Unit
): CreateExerciseScreenNavigationState {
    return CreateExerciseScreenNavigationState(
        onNavigateBack = onNavigateBack
    )
}

data class CreateExerciseScreenNavigationState(
    val onNavigateBack: () -> Unit
) 