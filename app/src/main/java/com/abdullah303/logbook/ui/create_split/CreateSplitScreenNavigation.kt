package com.abdullah303.logbook.ui.create_split

import androidx.compose.runtime.Composable

@Composable
fun CreateSplitScreenNavigation(
    onNavigateBack: () -> Unit
): CreateSplitScreenNavigationState {
    return CreateSplitScreenNavigationState(
        onNavigateBack = onNavigateBack
    )
}

data class CreateSplitScreenNavigationState(
    val onNavigateBack: () -> Unit
) 