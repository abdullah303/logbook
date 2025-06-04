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
import com.abdullah303.logbook.navigation.Screen
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExerciseScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var exerciseName by remember { mutableStateOf("") }
    var equipment by remember { mutableStateOf("") }
    var primaryMuscle by remember { mutableStateOf("") }
    var auxillaryMuscles by remember { mutableStateOf("") }
    var bodyweightContribution by remember { mutableStateOf("") }
    
    // Listen for muscle selection results
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedPrimaryMuscle", "")
            ?.collect { selectedMuscle ->
                if (selectedMuscle.isNotEmpty()) {
                    primaryMuscle = selectedMuscle
                    // Clear the saved state to prevent reprocessing
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("selectedPrimaryMuscle")
                }
            }
    }
    
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedAuxillaryMuscles", "")
            ?.collect { selectedMuscles ->
                if (selectedMuscles.isNotEmpty()) {
                    auxillaryMuscles = selectedMuscles
                    // Clear the saved state to prevent reprocessing
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("selectedAuxillaryMuscles")
                }
            }
    }
    
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedBodyweightPercentage", "")
            ?.collect { selectedPercentage ->
                if (selectedPercentage.isNotEmpty()) {
                    bodyweightContribution = selectedPercentage
                    // Clear the saved state to prevent reprocessing
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("selectedBodyweightPercentage")
                }
            }
    }
    
    // Listen for equipment selection
    LaunchedEffect(navController) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("selectedEquipment", "")
            ?.collect { selectedEquipment ->
                if (selectedEquipment.isNotEmpty()) {
                    equipment = selectedEquipment
                    // Clear the saved state to prevent reprocessing
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("selectedEquipment")
                }
            }
    }
    
    Scaffold(
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
                        onClick = {
                            // TODO: Save exercise
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            Icons.Default.Save,
                            contentDescription = "Save"
                        )
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
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
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
                OutlinedTextField(
                    value = equipment,
                    onValueChange = { },
                    label = { Text("Equipment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Select equipment",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        navController.navigate(Screen.EquipmentSelection.route)
                                    }
                                }
                            }
                        }
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
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Primary Muscle - Clickable
                    OutlinedTextField(
                        value = primaryMuscle,
                        onValueChange = { },
                        label = { Text("Primary Muscle") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = "Select muscle",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                            .also { interactionSource ->
                                LaunchedEffect(interactionSource) {
                                    interactionSource.interactions.collect {
                                        if (it is PressInteraction.Release) {
                                            navController.navigate(Screen.PrimaryMuscleSelection.route)
                                        }
                                    }
                                }
                            }
                    )
                    
                    // Auxillary Muscles - Clickable
                    OutlinedTextField(
                        value = auxillaryMuscles,
                        onValueChange = { },
                        label = { Text("Auxillary Muscles") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = "Select muscles",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                            .also { interactionSource ->
                                LaunchedEffect(interactionSource) {
                                    interactionSource.interactions.collect {
                                        if (it is PressInteraction.Release) {
                                            navController.navigate(Screen.AuxillaryMuscleSelection.route)
                                        }
                                    }
                                }
                            }
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
                OutlinedTextField(
                    value = if (bodyweightContribution.isNotEmpty()) "$bodyweightContribution%" else "",
                    onValueChange = { },
                    label = { Text("Bodyweight Contribution (%)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Select percentage",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        navController.navigate(Screen.BodyweightSelection.route)
                                    }
                                }
                            }
                        }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
} 