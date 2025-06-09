package com.abdullah303.logbook.features.create_equipment.ui

import androidx.compose.foundation.clickable
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
import com.abdullah303.logbook.core.ui.components.SegmentedControl
import com.abdullah303.logbook.navigation.Screen

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
        if (type == EquipmentType.RESISTANCE_MACHINE && equipment.isPinLoaded == null) {
            viewModel.updateIsPinLoaded(true)
        }
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

    // Handle weight selection result
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedWeight", "")
            ?.collect { weight ->
                if (weight.isNotEmpty()) {
                    val selectedField = navController.currentBackStackEntry?.savedStateHandle?.get<String>("selectedField")
                    if (selectedField == null) {  // Only handle direct weight selections (machine/bar weight)
                        when (equipmentType) {
                            "Resistance Machine" -> viewModel.updateMachineWeight(weight)
                            "Smith Machine" -> viewModel.updateBarWeight(weight)
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedWeight")
                    }
                }
            }
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
                            SegmentedControl(
                                items = listOf("Pin Loaded", "Plate Loaded"),
                                defaultSelectedItemIndex = if (equipment.isPinLoaded == true) 0 else 1,
                                onItemSelection = { index ->
                                    // Clear any existing weight selection state
                                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedWeight")
                                    navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("selectedRangeIndex")
                                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedField")
                                    viewModel.updateIsPinLoaded(index == 0)
                            }
                            )
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
                                    Text("Machine Weight:", style = MaterialTheme.typography.titleMedium)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate(
                                                    Screen.WeightSelection.createRoute(
                                                        min = "0",
                                                        max = "1000",
                                                        interval = "5",
                                                        unit = "kg"
                                                    )
                                                )
                                            }
                                    ) {
                                        OutlinedTextField(
                                            value = "${equipment.machineWeight ?: "0"} kg",
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
                                }
                            }

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
                                    SegmentedControl(
                                        items = listOf("1", "2"),
                                        defaultSelectedItemIndex = (equipment.loadingPegs ?: 2) - 1,
                                        onItemSelection = { index ->
                                            viewModel.updateLoadingPegs(index + 1)
                                        }
                                        )
                                }
                            }
                        }
                    }
                }
                "Smith Machine" -> {
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
                            Text("Bar Weight:", style = MaterialTheme.typography.titleMedium)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screen.WeightSelection.createRoute(
                                                min = "0",
                                                max = "100",
                                                interval = "1",
                                                unit = "kg"
                                            )
                                        )
                                    }
                            ) {
                                OutlinedTextField(
                                    value = "${equipment.barWeight ?: "0"} kg",
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
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
} 