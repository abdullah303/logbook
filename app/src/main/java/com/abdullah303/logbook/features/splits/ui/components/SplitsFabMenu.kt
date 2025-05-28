package com.abdullah303.logbook.features.splits.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter

/**
 * Floating Action Button menu for the Splits screen.
 * Provides quick access to create new splits and workouts.
 *
 * @param onNavigateToCreateSplit Callback when create split is selected
 * @param onNavigateToCreateWorkout Callback when create workout is selected
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitsFabMenu(
    onNavigateToCreateSplit: () -> Unit,
    onNavigateToCreateWorkout: () -> Unit
) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    Box(modifier = Modifier.fillMaxSize()) {
        val menuItems = listOf(
            Icons.Default.List to "Create Split" to onNavigateToCreateSplit,
            Icons.Default.FitnessCenter to "Create Workout" to onNavigateToCreateWorkout
        )

        FloatingActionButtonMenu(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = fabMenuExpanded,
            button = {
                ToggleFloatingActionButton(
                    modifier = Modifier.animateFloatingActionButton(
                        visible = true,
                        alignment = Alignment.BottomEnd
                    ),
                    checked = fabMenuExpanded,
                    containerSize = ToggleFloatingActionButtonDefaults.containerSizeMedium(),
                    onCheckedChange = { fabMenuExpanded = !fabMenuExpanded }
                ) {
                    val imageVector by remember {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                        }
                    }
                    Icon(
                        painter = rememberVectorPainter(imageVector),
                        contentDescription = if (fabMenuExpanded) "Close menu" else "Open menu",
                        modifier = Modifier.animateIcon({ checkedProgress })
                    )
                }
            }
        ) {
            menuItems.forEach { (item, onClick) ->
                FloatingActionButtonMenuItem(
                    onClick = { 
                        fabMenuExpanded = false
                        onClick()
                    },
                    icon = { 
                        Icon(
                            item.first,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    text = { 
                        Text(
                            text = item.second,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
        }
    }
} 