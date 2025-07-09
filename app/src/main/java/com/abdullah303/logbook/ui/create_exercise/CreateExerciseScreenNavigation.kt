package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.runtime.Composable

@Composable
fun CreateExerciseScreenNavigation(
    onNavigateBack: () -> Unit,
    onNavigateToCreateSmithMachine: () -> Unit = {},
    onNavigateToCreateBarbell: () -> Unit = {},
    onNavigateToCreateCableStack: () -> Unit = {},
    onNavigateToCreateResistanceMachine: () -> Unit = {}
): CreateExerciseScreenNavigationState {
    return CreateExerciseScreenNavigationState(
        onNavigateBack = onNavigateBack,
        onNavigateToCreateSmithMachine = onNavigateToCreateSmithMachine,
        onNavigateToCreateBarbell = onNavigateToCreateBarbell,
        onNavigateToCreateCableStack = onNavigateToCreateCableStack,
        onNavigateToCreateResistanceMachine = onNavigateToCreateResistanceMachine
    )
}

data class CreateExerciseScreenNavigationState(
    val onNavigateBack: () -> Unit,
    val onNavigateToCreateSmithMachine: () -> Unit,
    val onNavigateToCreateBarbell: () -> Unit,
    val onNavigateToCreateCableStack: () -> Unit,
    val onNavigateToCreateResistanceMachine: () -> Unit
) 