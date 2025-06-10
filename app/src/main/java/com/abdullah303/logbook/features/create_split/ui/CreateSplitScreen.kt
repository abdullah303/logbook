package com.abdullah303.logbook.features.create_split.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.material3.FloatingToolbarDefaults.floatingToolbarVerticalNestedScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.features.create_split.ui.components.DayButtonGroup
import com.abdullah303.logbook.features.create_split.ui.components.EditableTitle
import com.abdullah303.logbook.features.create_split.ui.components.SplitCreationToolbar
import com.abdullah303.logbook.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateSplitScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    var days by rememberSaveable { mutableStateOf(listOf("Day 1")) }
    var selectedDayIndex by rememberSaveable { mutableStateOf(0) }
    var splitTitle by rememberSaveable { mutableStateOf("My Split") }
    var isRenamingInline by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                EditableTitle(
                    title = splitTitle,
                    onTitleChanged = { splitTitle = it }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .floatingToolbarVerticalNestedScroll(
                        expanded = expanded,
                        onExpand = { expanded = true },
                        onCollapse = { expanded = false }
                    ),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    DayButtonGroup(
                        days = days,
                        selectedDayIndex = selectedDayIndex,
                        onDaySelected = { selectedDayIndex = it },
                        isRenamingInline = isRenamingInline,
                        onRenameComplete = { newName ->
                            days = days.toMutableList().apply {
                                set(selectedDayIndex, newName)
                            }
                            isRenamingInline = false
                        }
                    )
                }
            }

            SplitCreationToolbar(
                onAddExercise = { navController.navigate(Screen.ExerciseList.route) },
                onAddDay = { 
                    days = days + "Day ${days.size + 1}"
                },
                onRenameDay = { 
                    isRenamingInline = true
                },
                onDeleteDay = { 
                    if (days.size > 1) {
                        days = days.filterIndexed { idx, _ -> idx != selectedDayIndex }
                        selectedDayIndex = minOf(selectedDayIndex, days.lastIndex)
                    }
                },
                onCancelPlan = { navController.navigateUp() },
                onSaveSplit = { /* TODO: Implement save split */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
