package com.abdullah303.logbook.features.create_equipment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.core.ui.components.Range
import com.abdullah303.logbook.core.ui.components.WeightSelectionScreen
import com.abdullah303.logbook.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEquipmentScreen(
    navController: NavController,
    equipmentType: String,
    modifier: Modifier = Modifier
) {
    var equipmentName by remember { mutableStateOf("") }
    var ranges by remember { mutableStateOf(listOf(Triple("", "", ""))) }
    var isPinLoaded by remember { mutableStateOf(true) }
    var barWeight by remember { mutableStateOf("") }
    var machineWeight by remember { mutableStateOf("") }
    var loadingPegs by remember { mutableStateOf(2) }

    // Handle weight selection result
    LaunchedEffect(navController.previousBackStackEntry?.savedStateHandle) {
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedWeight")?.let { weight ->
            if (weight.isNotEmpty()) {
                // Find the index of the range that was being edited
                val currentRangeIndex = ranges.indexOfFirst { it.first.isEmpty() }
                if (currentRangeIndex != -1) {
                    val updatedRanges = ranges.toMutableList()
                    updatedRanges[currentRangeIndex] = Triple(weight, ranges[currentRangeIndex].second, ranges[currentRangeIndex].third)
                    ranges = updatedRanges
                }
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedWeight", "")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Equipment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: Save the new equipment
                    navController.popBackStack()
                }
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = equipmentType,
                        onValueChange = { },
                        label = { Text("Equipment Type") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false
                    )

                    OutlinedTextField(
                        value = equipmentName,
                        onValueChange = { equipmentName = it },
                        label = { Text("Equipment Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            when (equipmentType) {
                "Cable Stack" -> {
                    Range(
                        ranges = ranges,
                        onRangesChange = { ranges = it },
                        navController = navController,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "Resistance Machine" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text("Load Type:", style = MaterialTheme.typography.titleMedium)
                            SingleChoiceSegmentedButtonRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 0,
                                        count = 2
                                    ),
                                    onClick = { isPinLoaded = true },
                                    selected = isPinLoaded,
                                    label = { Text("Pin Loaded") }
                                )
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 1,
                                        count = 2
                                    ),
                                    onClick = { isPinLoaded = false },
                                    selected = !isPinLoaded,
                                    label = { Text("Plate Loaded") }
                                )
                            }
                        }
                    }

                    if (isPinLoaded) {
                        Range(
                            ranges = ranges,
                            onRangesChange = { ranges = it },
                            navController = navController,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedTextField(
                                value = barWeight,
                                onValueChange = { barWeight = it },
                                label = { Text("Bar Weight (kg)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text("Loading Pegs:", style = MaterialTheme.typography.titleMedium)
                                    SingleChoiceSegmentedButtonRow(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        SegmentedButton(
                                            shape = SegmentedButtonDefaults.itemShape(
                                                index = 0,
                                                count = 2
                                            ),
                                            onClick = { loadingPegs = 1 },
                                            selected = loadingPegs == 1,
                                            label = { Text("1") }
                                        )
                                        SegmentedButton(
                                            shape = SegmentedButtonDefaults.itemShape(
                                                index = 1,
                                                count = 2
                                            ),
                                            onClick = { loadingPegs = 2 },
                                            selected = loadingPegs == 2,
                                            label = { Text("2") }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                "Smith Machine" -> {
                    OutlinedTextField(
                        value = barWeight,
                        onValueChange = { barWeight = it },
                        label = { Text("Bar Weight (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = loadingPegs.toString(),
                        onValueChange = { loadingPegs = it.toIntOrNull() ?: 2 },
                        label = { Text("Loading Pegs (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
} 