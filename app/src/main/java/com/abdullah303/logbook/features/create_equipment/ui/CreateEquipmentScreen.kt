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
import com.abdullah303.logbook.core.ui.components.PillTextField

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
    var loadingPegs by remember { mutableStateOf("") }

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
                    PillTextField(
                        value = equipmentType,
                        onValueChange = { },
                        label = { Text("Equipment Type") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    PillTextField(
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = isPinLoaded,
                                    onClick = { isPinLoaded = true }
                                )
                                Text("Pin Loaded")
                                RadioButton(
                                    selected = !isPinLoaded,
                                    onClick = { isPinLoaded = false }
                                )
                                Text("Plate Loaded")
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
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                PillTextField(
                                    value = machineWeight,
                                    onValueChange = { machineWeight = it },
                                    label = { Text("Machine Weight") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                PillTextField(
                                    value = loadingPegs,
                                    onValueChange = { loadingPegs = it },
                                    label = { Text("Number of Loading Pegs") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                "Smith Machine" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            PillTextField(
                                value = barWeight,
                                onValueChange = { barWeight = it },
                                label = { Text("Bar Weight") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
} 