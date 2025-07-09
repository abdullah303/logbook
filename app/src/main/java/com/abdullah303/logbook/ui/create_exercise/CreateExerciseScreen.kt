package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch
import com.abdullah303.logbook.ui.create_exercise.components.BodyweightContributionCard
import com.abdullah303.logbook.ui.create_exercise.components.EquipmentSelectionCard
import com.abdullah303.logbook.ui.create_exercise.components.EquipmentSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.ExerciseNameCard
import com.abdullah303.logbook.ui.create_exercise.components.MuscleSelectionCard
import com.abdullah303.logbook.ui.create_exercise.components.MuscleSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.SetupInfoCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    val equipment by viewModel.equipment.collectAsState()
    val primaryMuscles by viewModel.primaryMuscles.collectAsState()
    val auxiliaryMuscles by viewModel.auxiliaryMuscles.collectAsState()
    val bodyweightContribution by viewModel.bodyweightContribution.collectAsState()
    val setupInfo by viewModel.setupInfo.collectAsState()
    
    // bottom sheet state
    var showEquipmentSelection by remember { mutableStateOf(false) }
    var showPrimaryMuscleSelection by remember { mutableStateOf(false) }
    var showAuxiliaryMuscleSelection by remember { mutableStateOf(false) }
    val equipmentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val primaryMuscleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val auxiliaryMuscleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // emphasized title at the top (outside surface)
        Text(
            text = "Create Exercise",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // surface container for form components
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            shadowElevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // exercise name card
                ExerciseNameCard(
                    value = exerciseName,
                    onValueChange = { viewModel.updateExerciseName(it) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // equipment selection card
                EquipmentSelectionCard(
                    selectedEquipment = equipment,
                    onCardClick = {
                        showEquipmentSelection = true
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // primary muscles selection card
                MuscleSelectionCard(
                    title = "Primary Muscles",
                    selectedMuscles = primaryMuscles,
                    onCardClick = {
                        showPrimaryMuscleSelection = true
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    isPrimary = true
                )
                
                // auxiliary muscles selection card
                MuscleSelectionCard(
                    title = "Auxiliary Muscles",
                    selectedMuscles = auxiliaryMuscles,
                    onCardClick = {
                        showAuxiliaryMuscleSelection = true
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    isPrimary = false
                )
                
                // bodyweight contribution card
                BodyweightContributionCard(
                    value = bodyweightContribution,
                    onValueChange = { viewModel.updateBodyweightContribution(it) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // setup info card
                SetupInfoCard(
                    value = setupInfo,
                    onValueChange = { viewModel.updateSetupInfo(it) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
        
        // equipment selection bottom sheet
        if (showEquipmentSelection) {
            LaunchedEffect(showEquipmentSelection) {
                if (showEquipmentSelection) {
                    equipmentSheetState.show()
                }
            }
            EquipmentSelectionBottomSheet(
                selectedEquipment = equipment,
                onDismiss = {
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                },
                onConfirm = { selectedEquipment ->
                    viewModel.updateEquipment(selectedEquipment)
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                },
                sheetState = equipmentSheetState
            )
        }
        
        // primary muscle selection bottom sheet
        if (showPrimaryMuscleSelection) {
            LaunchedEffect(showPrimaryMuscleSelection) {
                if (showPrimaryMuscleSelection) {
                    primaryMuscleSheetState.show()
                }
            }
            MuscleSelectionBottomSheet(
                title = "Select Primary Muscles",
                selectedMuscles = primaryMuscles,
                onDismiss = {
                    showPrimaryMuscleSelection = false
                    scope.launch {
                        primaryMuscleSheetState.hide()
                    }
                },
                onConfirm = { selectedMuscles ->
                    viewModel.updatePrimaryMuscles(selectedMuscles)
                    showPrimaryMuscleSelection = false
                    scope.launch {
                        primaryMuscleSheetState.hide()
                    }
                },
                sheetState = primaryMuscleSheetState
            )
        }
        
        // auxiliary muscle selection bottom sheet
        if (showAuxiliaryMuscleSelection) {
            LaunchedEffect(showAuxiliaryMuscleSelection) {
                if (showAuxiliaryMuscleSelection) {
                    auxiliaryMuscleSheetState.show()
                }
            }
            MuscleSelectionBottomSheet(
                title = "Select Auxiliary Muscles",
                selectedMuscles = auxiliaryMuscles,
                onDismiss = {
                    showAuxiliaryMuscleSelection = false
                    scope.launch {
                        auxiliaryMuscleSheetState.hide()
                    }
                },
                onConfirm = { selectedMuscles ->
                    viewModel.updateAuxiliaryMuscles(selectedMuscles)
                    showAuxiliaryMuscleSelection = false
                    scope.launch {
                        auxiliaryMuscleSheetState.hide()
                    }
                },
                sheetState = auxiliaryMuscleSheetState
            )
        }
    }
} 