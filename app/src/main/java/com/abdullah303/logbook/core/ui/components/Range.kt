package com.abdullah303.logbook.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.navigation.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Range(
    ranges: List<Triple<Float, Float, Float>>,
    onRangesChange: (List<Triple<Float, Float, Float>>) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var weightUnitIndex by remember { mutableStateOf(1) } // Default to kg
    val weightUnits = listOf("lb", "kg")
    val weightUnit = weightUnits[weightUnitIndex]
    var currentRanges by remember { mutableStateOf(if (ranges.isEmpty()) listOf(Triple(0f, 100f, 5f)) else ranges) }
    val scope = rememberCoroutineScope()

    // Update currentRanges when ranges prop changes
    LaunchedEffect(ranges) {
        if (ranges.isNotEmpty()) {
            currentRanges = ranges
        }
    }

    // Handle weight selection result using StateFlow
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Float>("selectedWeight", 0f)
            ?.collect { weight ->
                if (weight > 0f) {
                    val currentRangeIndex = navController.currentBackStackEntry?.savedStateHandle?.get<Int>("selectedRangeIndex") ?: -1
                    val selectedField = navController.currentBackStackEntry?.savedStateHandle?.get<String>("selectedField") ?: "min"
                    if (currentRangeIndex != -1) {
                        val updatedRanges = currentRanges.toMutableList()
                        if (currentRangeIndex < updatedRanges.size) {
                            val (min, max, step) = updatedRanges[currentRangeIndex]
                            updatedRanges[currentRangeIndex] = when (selectedField) {
                                "min" -> Triple(weight, max, step)
                                "max" -> Triple(min, weight, step)
                                "step" -> Triple(min, max, weight)
                                else -> Triple(weight, max, step)
                            }
                            currentRanges = updatedRanges
                            onRangesChange(updatedRanges)
                        }
                    }
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedWeight", 0f)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedRangeIndex", -1)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedField", "")
                }
            }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
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
                SegmentedControl(
                    items = weightUnits,
                    defaultSelectedItemIndex = weightUnitIndex,
                    useFixedWidth = true,
                    itemWidth = 60.dp,
                    onItemSelection = { weightUnitIndex = it }
                )
            }

            // Headings row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Min",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Max",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Step",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Ranges
            currentRanges.forEachIndexed { index, (min, max, interval) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedRangeIndex", index)
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedField", "min")
                                navController.navigate(
                                    Screen.MinWeightSelection.createRoute(
                                        max = max.toString(),
                                        step = interval.toString(),
                                        unit = weightUnit,
                                        currentValue = min.toString()
                                    )
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = "%.1f %s".format(min, weightUnit),
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false,
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedRangeIndex", index)
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedField", "max")
                                navController.navigate(
                                    Screen.MaxWeightSelection.createRoute(
                                        min = min.toString(),
                                        step = interval.toString(),
                                        unit = weightUnit,
                                        currentValue = max.toString()
                                    )
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = "%.1f %s".format(max, weightUnit),
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false,
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedRangeIndex", index)
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedField", "step")
                                navController.navigate(
                                    Screen.StepWeightSelection.createRoute(
                                        unit = weightUnit,
                                        currentValue = interval.toString()
                                    )
                                )
                            }
                    ) {
                        OutlinedTextField(
                            value = "%.1f %s".format(interval, weightUnit),
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            enabled = false,
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }

                    IconButton(
                        onClick = {
                            val updatedRanges = currentRanges.toMutableList()
                            updatedRanges.removeAt(index)
                            currentRanges = updatedRanges
                            onRangesChange(updatedRanges)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Range"
                        )
                    }
                }
            }

            // Add Range button
            Button(
                onClick = {
                    val updatedRanges = currentRanges.toMutableList()
                    updatedRanges.add(Triple(0f, 100f, 5f))
                    currentRanges = updatedRanges
                    onRangesChange(updatedRanges)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Range")
            }
        }
    }
} 