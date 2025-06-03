package com.abdullah303.logbook.features.create_exercise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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
    var isUnilateral by remember { mutableStateOf(false) }
    
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Exercise Name
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Equipment
            OutlinedTextField(
                value = equipment,
                onValueChange = { equipment = it },
                label = { Text("Equipment") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Primary Muscle
            OutlinedTextField(
                value = primaryMuscle,
                onValueChange = { primaryMuscle = it },
                label = { Text("Primary Muscle") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Auxillary Muscles
            OutlinedTextField(
                value = auxillaryMuscles,
                onValueChange = { auxillaryMuscles = it },
                label = { Text("Auxillary Muscles") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Bodyweight Contribution
            OutlinedTextField(
                value = bodyweightContribution,
                onValueChange = { bodyweightContribution = it },
                label = { Text("Bodyweight Contribution (%)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            
            // Unilateral/Bilateral Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Exercise Type",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = !isUnilateral,
                            onClick = { isUnilateral = false },
                            label = { Text("Bilateral") },
                            modifier = Modifier.weight(1f)
                        )
                        
                        FilterChip(
                            selected = isUnilateral,
                            onClick = { isUnilateral = true },
                            label = { Text("Unilateral") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
} 