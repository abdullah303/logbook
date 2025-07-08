package com.abdullah303.logbook.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abdullah303.logbook.ui.components.FABMenuItem

@Composable
fun HomeScreenNavigation(
    onNavigateToCreateSplit: () -> Unit,
    onNavigateToStandaloneWorkout: () -> Unit
): HomeScreenNavigationState {
    var selectedItem by remember { mutableIntStateOf(0) }

    // fab menu items for split section
    val fabMenuItems = listOf(
        FABMenuItem(
            icon = Icons.Filled.Add,
            text = "Create Split",
            onClick = onNavigateToCreateSplit
        ),
        FABMenuItem(
            icon = Icons.Filled.FitnessCenter,
            text = "Standalone Workout",
            onClick = onNavigateToStandaloneWorkout
        )
    )

    return HomeScreenNavigationState(
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it },
        fabMenuItems = fabMenuItems
    )
}

data class HomeScreenNavigationState(
    val selectedItem: Int,
    val onItemSelected: (Int) -> Unit,
    val fabMenuItems: List<FABMenuItem>
) 