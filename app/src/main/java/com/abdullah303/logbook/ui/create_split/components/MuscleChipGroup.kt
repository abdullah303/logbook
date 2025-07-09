package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.model.Muscles

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MuscleChipGroup(
    selectedMuscles: Set<Muscles>,
    onMuscleSelectionChanged: (Set<Muscles>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val allMuscles = Muscles.values().sortedBy { muscle ->
        muscle.name.lowercase().replace("_", " ")
    }
    
    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top)
                .then(
                    if (expanded) {
                        Modifier.verticalScroll(rememberScrollState())
                    } else {
                        Modifier.horizontalScroll(rememberScrollState())
                    }
                ),
            horizontalArrangement = Arrangement.Start,
            maxLines = if (!expanded) 1 else Int.MAX_VALUE,
        ) {
            // "all" filter chip
            FilterChip(
                selected = selectedMuscles.isEmpty(),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(alignment = Alignment.CenterVertically),
                onClick = { 
                    if (selectedMuscles.isEmpty()) {
                        expanded = !expanded
                    } else {
                        onMuscleSelectionChanged(emptySet())
                    }
                },
                label = { Text("All") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Filter Options",
                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                    )
                },
            )
            
            // divider
            Box(
                Modifier
                    .height(FilterChipDefaults.Height)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                VerticalDivider()
            }
            
            // muscle chips
            allMuscles.forEach { muscle ->
                val isSelected = selectedMuscles.contains(muscle)
                FilterChip(
                    selected = isSelected,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(alignment = Alignment.CenterVertically),
                    onClick = { 
                        val newSelection = if (isSelected) {
                            selectedMuscles - muscle
                        } else {
                            selectedMuscles + muscle
                        }
                        onMuscleSelectionChanged(newSelection)
                    },
                    label = { 
                        Text(
                            text = muscle.name.lowercase()
                                .replace("_", " ")
                                .split(" ")
                                .joinToString(" ") { it.capitalize(Locale.current) }
                        )
                    },
                )
            }
        }
    }
} 