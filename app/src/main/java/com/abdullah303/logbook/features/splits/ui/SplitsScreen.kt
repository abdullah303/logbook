package com.abdullah303.logbook.features.splits.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.abdullah303.logbook.features.graphs.ui.GraphsScreen
import com.abdullah303.logbook.features.splits.data.DummyData
import com.abdullah303.logbook.features.splits.ui.components.SplitsBottomBar
import com.abdullah303.logbook.features.splits.ui.components.SplitsFabMenu
import com.abdullah303.logbook.features.splits.ui.components.SplitsTabContent
import com.abdullah303.logbook.features.settings.ui.SettingsScreen
import com.abdullah303.logbook.navigation.Screen

/**
 * Main screen for the Splits feature that displays workout splits, graphs, and settings.
 * Uses Material 3 components and follows a bottom navigation pattern.
 *
 * @param navController The NavController for handling navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitsScreen(navController: NavController) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            SplitsBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                SplitsFabMenu(
                    onNavigateToCreateSplit = {
                        navController.navigate(Screen.CreateSplit.route)
                    },
                    onNavigateToCreateWorkout = {
                        // TODO: Navigate to create workout screen
                    }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> SplitsTabContent(
                paddingValues = paddingValues,
                activeSplit = DummyData.activeSplit
            )
            1 -> GraphsScreen(paddingValues = paddingValues)
            2 -> SettingsScreen(paddingValues = paddingValues)
        }
    }
} 