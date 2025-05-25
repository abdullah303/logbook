package com.abdullah303.logbook.features.splits.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material3.*
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.features.graphs.ui.GraphsScreen
import com.abdullah303.logbook.features.splits.data.DummyData
import com.abdullah303.logbook.features.splits.ui.components.SplitContainer
import com.abdullah303.logbook.features.splits.ui.components.WeekCalendarView
import com.abdullah303.logbook.features.settings.ui.SettingsScreen
import com.abdullah303.logbook.navigation.Screen
import java.time.LocalDate
import com.abdullah303.logbook.R
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitsScreen(navController: NavController) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val activeSplit = DummyData.activeSplit

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Timeline, contentDescription = null) },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        },
        floatingActionButton = {
            // FAB Menu - only show on Splits tab
            if (selectedTab == 0) {
                FloatingActionButtonMenu(
                    expanded = fabMenuExpanded,
                    button = {
                        FloatingActionButton(
                            onClick = { fabMenuExpanded = !fabMenuExpanded },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(
                                imageVector = if (fabMenuExpanded) Icons.Filled.Close else Icons.Filled.Add,
                                contentDescription = if (fabMenuExpanded) "Close menu" else "Open menu"
                            )
                        }
                    }
                ) {
                    FloatingActionButtonMenuItem(
                        onClick = { 
                            fabMenuExpanded = false
                            // TODO: Navigate to create split
                        },
                        icon = { 
                            Icon(
                                Icons.Default.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        text = { 
                            Text(
                                "Create Split",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    
                    FloatingActionButtonMenuItem(
                        onClick = { 
                            fabMenuExpanded = false
                            // TODO: Navigate to create workout
                        },
                        icon = { 
                            Icon(
                                Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        text = { 
                            Text(
                                "Create Workout",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        // Content based on selected tab
        when (selectedTab) {
            0 -> SplitsContent(
                paddingValues = paddingValues,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it },
                activeSplit = activeSplit
            )
            1 -> GraphsScreen(paddingValues = paddingValues)
            2 -> SettingsScreen(paddingValues = paddingValues)
        }
    }
}

@Composable
private fun SplitsContent(
    paddingValues: PaddingValues,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    activeSplit: com.abdullah303.logbook.features.splits.data.Split
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Week Calendar View
        WeekCalendarView(
            modifier = Modifier.padding(bottom = 24.dp),
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )

        // Split Container
        SplitContainer(
            split = activeSplit,
            exerciseTemplates = DummyData.exerciseTemplates,
            onSettingsClick = { /* TODO: Handle settings click */ }
        )
    }
} 