package com.abdullah303.logbook.features.create_exercise.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdullah303.logbook.features.create_exercise.presentation.CreateExerciseViewModel
import com.abdullah303.logbook.features.create_exercise.presentation.SaveResult
import com.abdullah303.logbook.navigation.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExerciseScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateExerciseViewModel = hiltViewModel()
) {
    val exercise by viewModel.exercise.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val saveResult by viewModel.saveResult.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle save result
    LaunchedEffect(saveResult) {
        when (saveResult) {
            is SaveResult.Success -> {
                snackbarHostState.showSnackbar("Exercise saved successfully!")
                navController.navigateUp()
            }
            is SaveResult.Error -> {
                snackbarHostState.showSnackbar((saveResult as SaveResult.Error).message)
            }
            null -> { /* Do nothing */ }
        }
        viewModel.clearSaveResult()
    }

    // Handle equipment selection
    LaunchedEffect(navController.previousBackStackEntry?.savedStateHandle) {
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedEquipment")?.let { equipment ->
            if (equipment.isNotEmpty()) {
                viewModel.updateEquipment(equipment)
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedEquipment", "")
            }
        }
    }

    // Also observe the current back stack entry for equipment selection
    LaunchedEffect(navController.currentBackStackEntry?.savedStateHandle) {
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("selectedEquipment", "")?.collect { equipment ->
            if (equipment.isNotEmpty()) {
                viewModel.updateEquipment(equipment)
                navController.currentBackStackEntry?.savedStateHandle?.set("selectedEquipment", "")
            }
        }
    }

    // Handle primary muscle selection
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedPrimaryMuscle", "")
            ?.collect { selectedMuscle ->
                if (selectedMuscle.isNotEmpty()) {
                    viewModel.updatePrimaryMuscle(selectedMuscle)
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedPrimaryMuscle")
                }
            }
    }
    
    // Handle auxiliary muscles selection
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedAuxillaryMuscles", "")
            ?.collect { selectedMuscles ->
                if (selectedMuscles.isNotEmpty()) {
                    viewModel.updateAuxiliaryMuscles(selectedMuscles)
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedAuxillaryMuscles")
                }
            }
    }
    
    // Handle bodyweight contribution selection
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedBodyweightPercentage", "")
            ?.collect { selectedPercentage ->
                if (selectedPercentage.isNotEmpty()) {
                    viewModel.updateBodyweightContribution(selectedPercentage)
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("selectedBodyweightPercentage")
                }
            }
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Create Exercise") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.saveExercise() },
                        enabled = !isSaving && exercise.name.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.Default.Save,
                                contentDescription = "Save Exercise"
                            )
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
            
            // Exercise Name Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                OutlinedTextField(
                    value = exercise.name,
                    onValueChange = { viewModel.updateName(it) },
                    label = { Text("Exercise Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    isError = exercise.name.isBlank(),
                    supportingText = if (exercise.name.isBlank()) {
                        { Text("Exercise name is required") }
                    } else null
                )
            }
            
            // Equipment Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                val equipmentInteractionSource = remember { MutableInteractionSource() }
                LaunchedEffect(equipmentInteractionSource) {
                    equipmentInteractionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            navController.navigate(Screen.EquipmentSelection.route)
                        }
                    }
                }
                
                OutlinedTextField(
                    value = exercise.equipment,
                    onValueChange = { },
                    label = { Text("Equipment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Select Equipment",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    interactionSource = equipmentInteractionSource,
                    placeholder = { Text("Select equipment") },
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
            
            // Muscle Selection Card
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Primary Muscle
                    val primaryMuscleInteractionSource = remember { MutableInteractionSource() }
                    LaunchedEffect(primaryMuscleInteractionSource) {
                        primaryMuscleInteractionSource.interactions.collect { interaction ->
                            if (interaction is PressInteraction.Release) {
                                navController.navigate(Screen.PrimaryMuscleSelection.route)
                            }
                        }
                    }
                    
                    OutlinedTextField(
                        value = exercise.primaryMuscle,
                        onValueChange = { },
                        label = { Text("Primary Muscle") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = "Select Primary Muscle",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        interactionSource = primaryMuscleInteractionSource,
                        placeholder = { Text("Select primary muscle") }
                    )
                    
                    // Auxiliary Muscles
                    val auxiliaryMusclesInteractionSource = remember { MutableInteractionSource() }
                    LaunchedEffect(auxiliaryMusclesInteractionSource) {
                        auxiliaryMusclesInteractionSource.interactions.collect { interaction ->
                            if (interaction is PressInteraction.Release) {
                                navController.navigate(Screen.AuxillaryMuscleSelection.route)
                            }
                        }
                    }
                    
                    OutlinedTextField(
                        value = exercise.auxiliaryMuscles,
                        onValueChange = { },
                        label = { Text("Auxiliary Muscles") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = "Select Auxiliary Muscles",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        interactionSource = auxiliaryMusclesInteractionSource,
                        placeholder = { Text("Select auxiliary muscles (optional)") }
                    )
                }
            }
            
            // Bodyweight Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                val bodyweightInteractionSource = remember { MutableInteractionSource() }
                LaunchedEffect(bodyweightInteractionSource) {
                    bodyweightInteractionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            navController.navigate(Screen.BodyweightSelection.route)
                        }
                    }
                }
                
                OutlinedTextField(
                    value = if (exercise.bodyweightContribution.isNotEmpty()) "${exercise.bodyweightContribution}%" else "",
                    onValueChange = { },
                    label = { Text("Bodyweight Contribution") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Select Bodyweight Contribution",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    interactionSource = bodyweightInteractionSource,
                    placeholder = { Text("Select bodyweight contribution (optional)") }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
} 