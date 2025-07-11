package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.runtime.Composable

@Composable
fun CreateExerciseScreenNavigation(
    onNavigateBack: (String?) -> Unit,
    onNavigateToCreateSmithMachine: () -> Unit = {},
    onNavigateToCreateCableStack: () -> Unit = {}
): CreateExerciseScreenNavigationState {
    return CreateExerciseScreenNavigationState(
        onNavigateBack = onNavigateBack,
        onNavigateToCreateSmithMachine = onNavigateToCreateSmithMachine,
        onNavigateToCreateCableStack = onNavigateToCreateCableStack
    )
}

data class CreateExerciseScreenNavigationState(
    val onNavigateBack: (String?) -> Unit,
    val onNavigateToCreateSmithMachine: () -> Unit,
    val onNavigateToCreateCableStack: () -> Unit
) 