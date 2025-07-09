package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreateExerciseScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: CreateExerciseViewModel = hiltViewModel()
) {
    val navigationState = CreateExerciseScreenNavigation(
        onNavigateBack = onNavigateBack
    )

    val exerciseName by viewModel.exerciseName.collectAsState()
    val primaryMuscles by viewModel.primaryMuscles.collectAsState()
    val auxiliaryMuscles by viewModel.auxiliaryMuscles.collectAsState()
    val bodyweightContribution by viewModel.bodyweightContribution.collectAsState()
    val setupInfo by viewModel.setupInfo.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // emphasized title at the top
        Text(
            text = "Create Exercise",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // exercise name field
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { viewModel.updateExerciseName(it) },
            label = { Text("Exercise Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // primary muscles field
        OutlinedTextField(
            value = primaryMuscles,
            onValueChange = { viewModel.updatePrimaryMuscles(it) },
            label = { Text("Primary Muscles") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // auxiliary muscles field
        OutlinedTextField(
            value = auxiliaryMuscles,
            onValueChange = { viewModel.updateAuxiliaryMuscles(it) },
            label = { Text("Auxiliary Muscles") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // bodyweight contribution field
        OutlinedTextField(
            value = bodyweightContribution,
            onValueChange = { viewModel.updateBodyweightContribution(it) },
            label = { Text("Bodyweight Contribution") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // setup info field
        OutlinedTextField(
            value = setupInfo,
            onValueChange = { viewModel.updateSetupInfo(it) },
            label = { Text("Setup Info") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            minLines = 3,
            maxLines = 5
        )
    }
} 