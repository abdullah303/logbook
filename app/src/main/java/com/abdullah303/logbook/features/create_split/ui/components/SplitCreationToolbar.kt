package com.abdullah303.logbook.features.create_split.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitCreationToolbar(
    onAddExercise: () -> Unit,
    onAddDay: () -> Unit,
    onRenameDay: () -> Unit,
    onDeleteDay: () -> Unit,
    onCancelPlan: () -> Unit,
    onSaveSplit: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Tracks whether the "extra" two buttons are visible
    var showExtras by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(true) }

    Box(modifier = modifier) {
        // Clickable overlay to dismiss expanded state
        if (showExtras) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showExtras = false }
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -FloatingToolbarDefaults.ScreenOffset),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = Color.Transparent
                    ),
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                HorizontalFloatingToolbar(
                    modifier = Modifier
                        .animateContentSize()
                        .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(24.dp)),
                    expanded = expanded,
                    leadingContent = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = onAddExercise
                            ) {
                                Icon(
                                    Icons.Filled.FitnessCenter,
                                    contentDescription = "Add exercise",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = onAddDay
                            ) {
                                Icon(
                                    Icons.Filled.CalendarToday,
                                    contentDescription = "Add day",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = onRenameDay
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Rename day",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Three-dot overflow (only when extras are hidden)
                            AnimatedVisibility(visible = !showExtras) {
                                IconButton(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { showExtras = true }
                                ) {
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "More actions",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            // Delete Day & Cancel Plan (only when extras are shown)
                            AnimatedVisibility(visible = showExtras) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    IconButton(
                                        modifier = Modifier.size(40.dp),
                                        onClick = {
                                            onDeleteDay()
                                            showExtras = false
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Delete day",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    IconButton(
                                        modifier = Modifier.size(40.dp),
                                        onClick = {
                                            onCancelPlan()
                                            showExtras = false
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "Cancel plan",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    },
                    trailingContent = { },
                    content = { }
                )
            }

            // Save Split FAB
            FloatingActionButton(
                onClick = onSaveSplit,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    Icons.Filled.Save,
                    contentDescription = "Save Split",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}