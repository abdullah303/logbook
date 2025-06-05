package com.abdullah303.logbook.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.core.ui.components.SelectablePillTextField
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Range(
    ranges: List<Triple<String, String, String>>,
    onRangesChange: (List<Triple<String, String, String>>) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var weightUnitIndex by remember { mutableStateOf(1) } // Default to kg
    val weightUnits = listOf("lb", "kg")
    val weightUnit = weightUnits[weightUnitIndex]

    // Initialize ranges with default values
    LaunchedEffect(Unit) {
        if (ranges.isEmpty()) {
            onRangesChange(listOf(Triple("0", "1000", "2.5")))
        }
    }

    // Handle selected value from WeightSelectionScreen
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.get<String>("selected_value")?.let { selectedValue ->
            // Find which field was selected and update it
            val currentRanges = ranges.toMutableList()
            val selectedField = navController.currentBackStackEntry?.savedStateHandle?.get<String>("selected_field")
            val selectedIndex = navController.currentBackStackEntry?.savedStateHandle?.get<Int>("selected_index") ?: 0
            
            if (currentRanges.isNotEmpty() && selectedField != null) {
                val (min, max, interval) = currentRanges[selectedIndex]
                currentRanges[selectedIndex] = when (selectedField) {
                    "min" -> Triple(selectedValue, max, interval)
                    "max" -> Triple(min, selectedValue, interval)
                    "interval" -> Triple(min, max, selectedValue)
                    else -> Triple(min, max, interval)
                }
                onRangesChange(currentRanges)
            }
            
            // Clear the saved state
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selected_value")
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selected_field")
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("selected_index")
        }
    }

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Range",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                SingleChoiceSegmentedButtonRow {
                    weightUnits.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = weightUnits.size
                            ),
                            onClick = { weightUnitIndex = index },
                            selected = index == weightUnitIndex,
                            label = { Text(label) }
                        )
                    }
                }
            }

            // Centered headings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Min", style = MaterialTheme.typography.titleMedium)
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Max", style = MaterialTheme.typography.titleMedium)
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Interval", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.size(48.dp)) // Space for delete button
            }

            ranges.forEachIndexed { index, (min, max, interval) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SelectablePillTextField(
                        value = "$min $weightUnit",
                        onSelect = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_field", "min")
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_index", index)
                            navController.navigate("weight_selection/$min/$max/$interval/$weightUnit") { 
                                launchSingleTop = true 
                            }
                        },
                        label = { Text("Min") },
                        modifier = Modifier.weight(1f)
                    )
                    SelectablePillTextField(
                        value = "$max $weightUnit",
                        onSelect = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_field", "max")
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_index", index)
                            navController.navigate("weight_selection/$min/$max/$interval/$weightUnit") { 
                                launchSingleTop = true 
                            }
                        },
                        label = { Text("Max") },
                        modifier = Modifier.weight(1f)
                    )
                    SelectablePillTextField(
                        value = "$interval $weightUnit",
                        onSelect = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_field", "interval")
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_index", index)
                            navController.navigate("weight_selection/0/1000/$interval/$weightUnit") { 
                                launchSingleTop = true 
                            }
                        },
                        label = { Text("Interval") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            val newRanges = ranges.toMutableList()
                            newRanges.removeAt(index)
                            onRangesChange(newRanges)
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Range")
                    }
                }
            }

            Button(
                onClick = {
                    onRangesChange(ranges + Triple("0", "1000", "2.5"))
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Range")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Add Range")
            }
        }
    }
} 