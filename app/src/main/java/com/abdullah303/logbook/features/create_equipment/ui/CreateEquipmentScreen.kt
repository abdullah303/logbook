package com.abdullah303.logbook.features.create_equipment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.model.WeightRange
import com.abdullah303.logbook.core.ui.components.Range
import com.abdullah303.logbook.features.create_equipment.presentation.CreateEquipmentViewModel
import com.abdullah303.logbook.features.create_equipment.presentation.SaveResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEquipmentScreen(
    navController: NavController,
    equipmentType: String,
    modifier: Modifier = Modifier,
    viewModel: CreateEquipmentViewModel = hiltViewModel()
) {
    val equipment by viewModel.equipment.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val saveResult by viewModel.saveResult.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Convert equipment type string to enum
    val type = when (equipmentType) {
        "Cable Stack" -> EquipmentType.CABLE_STACK
        "Resistance Machine" -> EquipmentType.RESISTANCE_MACHINE
        "Smith Machine" -> EquipmentType.SMITH_MACHINE
        else -> EquipmentType.BARBELL
    }

    // Update equipment type when screen is first loaded
    LaunchedEffect(type) {
        viewModel.updateType(type)
    }

    // Handle save result
    LaunchedEffect(saveResult) {
        when (saveResult) {
            is SaveResult.Success -> {
                snackbarHostState.showSnackbar("Equipment saved successfully!")
                navController.navigateUp()
            }
            is SaveResult.Error -> {
                snackbarHostState.showSnackbar((saveResult as SaveResult.Error).message)
            }
            null -> { /* Do nothing */ }
        }
        viewModel.clearSaveResult()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Create Equipment") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.saveEquipment() },
                        enabled = !isSaving && equipment.name.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Save, contentDescription = "Save Equipment")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Equipment Name Card
            Card(
                modifier = Modifier.fillMaxWidth(),
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
                    OutlinedTextField(
                        value = equipmentType,
                        onValueChange = { },
                        label = { Text("Equipment Type") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )

                    OutlinedTextField(
                        value = equipment.name,
                        onValueChange = { viewModel.updateName(it) },
                        label = { Text("Equipment Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        isError = equipment.name.isBlank(),
                        supportingText = if (equipment.name.isBlank()) {
                            { Text("Equipment name is required") }
                        } else null
                    )
                }
            }

            // Equipment Specific Fields
            when (equipmentType) {
                "Cable Stack" -> {
                    Range(
                        ranges = equipment.ranges?.map { Triple(it.weight, it.minReps, it.maxReps) } ?: emptyList(),
                        onRangesChange = { viewModel.updateRanges(it.map { (weight, minReps, maxReps) -> WeightRange(weight, minReps, maxReps) }) },
                        navController = navController,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "Resistance Machine" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                            Text("Load Type:", style = MaterialTheme.typography.titleMedium)
                            SingleChoiceSegmentedButtonRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 0,
                                        count = 2
                                    ),
                                    onClick = { viewModel.updateIsPinLoaded(true) },
                                    selected = equipment.isPinLoaded == true,
                                    label = { Text("Pin Loaded") }
                                )
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 1,
                                        count = 2
                                    ),
                                    onClick = { viewModel.updateIsPinLoaded(false) },
                                    selected = equipment.isPinLoaded == false,
                                    label = { Text("Plate Loaded") }
                                )
                            }
                        }
                    }

                    if (equipment.isPinLoaded == true) {
                        Range(
                            ranges = equipment.ranges?.map { Triple(it.weight, it.minReps, it.maxReps) } ?: emptyList(),
                            onRangesChange = { viewModel.updateRanges(it.map { (weight, minReps, maxReps) -> WeightRange(weight, minReps, maxReps) }) },
                            navController = navController,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedTextField(
                                value = equipment.machineWeight ?: "",
                                onValueChange = { viewModel.updateMachineWeight(it) },
                                label = { Text("Machine Weight (kg)") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                )
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
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
                                    Text("Loading Pegs:", style = MaterialTheme.typography.titleMedium)
                                    SingleChoiceSegmentedButtonRow(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        SegmentedButton(
                                            shape = SegmentedButtonDefaults.itemShape(
                                                index = 0,
                                                count = 2
                                            ),
                                            onClick = { viewModel.updateLoadingPegs(1) },
                                            selected = equipment.loadingPegs == 1,
                                            label = { Text("1") }
                                        )
                                        SegmentedButton(
                                            shape = SegmentedButtonDefaults.itemShape(
                                                index = 1,
                                                count = 2
                                            ),
                                            onClick = { viewModel.updateLoadingPegs(2) },
                                            selected = equipment.loadingPegs == 2,
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
                        value = equipment.barWeight ?: "",
                        onValueChange = { viewModel.updateBarWeight(it) },
                        label = { Text("Bar Weight (kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
} 