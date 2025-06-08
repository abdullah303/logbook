package com.abdullah303.logbook.core.ui.components

import androidx.compose.foundation.clickable
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
import com.abdullah303.logbook.navigation.Screen

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

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ranges",
                    style = MaterialTheme.typography.titleMedium
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

            // Headings row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Min",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Max",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Step",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.width(48.dp)) // Space for delete button
            }

            ranges.forEachIndexed { index, (min, max, interval) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(
                                    Screen.WeightSelection.createRoute(min, max, interval, weightUnit)
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = min,
                            onValueChange = { },
                            label = { Text("Min") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(
                                    Screen.WeightSelection.createRoute(min, max, interval, weightUnit)
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = max,
                            onValueChange = { },
                            label = { Text("Max") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(
                                    Screen.WeightSelection.createRoute(min, max, interval, weightUnit)
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = interval,
                            onValueChange = { },
                            label = { Text("Step") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false
                        )
                    }

                    IconButton(
                        onClick = {
                            val updatedRanges = ranges.toMutableList()
                            updatedRanges.removeAt(index)
                            onRangesChange(updatedRanges)
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Range",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val updatedRanges = ranges.toMutableList()
                    updatedRanges.add(Triple("", "", ""))
                    onRangesChange(updatedRanges)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Range"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Range")
            }
        }
    }
} 