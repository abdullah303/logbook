package com.abdullah303.logbook.ui.create_exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.abdullah303.logbook.ui.create_exercise.components.EquipmentSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.ExerciseNameCard
import com.abdullah303.logbook.ui.create_exercise.components.MuscleSelectionCard
import com.abdullah303.logbook.ui.create_exercise.components.MuscleSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.SetupInfoCard
import com.abdullah303.logbook.ui.create_exercise.components.BarbellSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.CableStackSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.ResistanceMachineSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.SmithMachineSelectionBottomSheet
import com.abdullah303.logbook.ui.create_exercise.components.UnifiedEquipmentCard
import com.abdullah303.logbook.ui.create_exercise.components.BarbellConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.SmithMachineConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.CableStackConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.ResistanceMachineConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.EquipmentConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.withBarbellInfo
import com.abdullah303.logbook.ui.create_exercise.components.withSmithMachineInfo
import com.abdullah303.logbook.ui.create_exercise.components.withCableStackInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdullah303.logbook.data.local.entity.Exercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExerciseScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: (String?) -> Unit = {},
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
    val smithMachineConfigurations by viewModel.smithMachineConfigurations.collectAsState()
    val selectedSmithMachine by viewModel.selectedSmithMachine.collectAsState()
    val barbellConfigurations by viewModel.barbellConfigurations.collectAsState()
    val selectedBarbell by viewModel.selectedBarbell.collectAsState()
    val cableStackConfigurations by viewModel.cableStackConfigurations.collectAsState()
    val selectedCableStack by viewModel.selectedCableStack.collectAsState()
    val resistanceMachineConfigurations by viewModel.resistanceMachineConfigurations.collectAsState()
    val selectedResistanceMachine by viewModel.selectedResistanceMachine.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val newlyCreatedExercise by viewModel.newlyCreatedExercise.collectAsState()
    
    // handle save success and newly created exercise
    LaunchedEffect(saveSuccess, newlyCreatedExercise) {
        if (saveSuccess && newlyCreatedExercise != null) {
            // reset the states
            viewModel.resetSaveSuccess()
            viewModel.resetNewlyCreatedExercise()
            // navigate back with the exercise ID
            onNavigateBack(newlyCreatedExercise!!.id)
        }
    }

    // bottom sheet state
    var showEquipmentSelection by remember { mutableStateOf(false) }
    var showPrimaryMuscleSelection by remember { mutableStateOf(false) }
    var showAuxiliaryMuscleSelection by remember { mutableStateOf(false) }
    var showSmithMachineSelection by remember { mutableStateOf(false) }
    var showBarbellSelection by remember { mutableStateOf(false) }
    var showCableStackSelection by remember { mutableStateOf(false) }
    var showResistanceMachineSelection by remember { mutableStateOf(false) }
    val equipmentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val primaryMuscleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val auxiliaryMuscleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val smithMachineSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val barbellSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val cableStackSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val resistanceMachineSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
                
                // unified equipment card
                UnifiedEquipmentCard(
                    selectedEquipment = equipment,
                    selectedBarbell = selectedBarbell,
                    selectedSmithMachine = selectedSmithMachine,
                    selectedCableStack = selectedCableStack,
                    selectedResistanceMachine = selectedResistanceMachine,
                    onCardClick = {
                        showEquipmentSelection = true
                    },
                    onBarbellClick = {
                        showBarbellSelection = true
                    },
                    onSmithMachineClick = {
                        showSmithMachineSelection = true
                    },
                    onCableStackClick = {
                        showCableStackSelection = true
                    },
                    onResistanceMachineClick = {
                        showResistanceMachineSelection = true
                    },
                    onClearCustomEquipment = {
                        viewModel.clearAllCustomEquipment()
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
                    onAddLine = { viewModel.addSetupInfoLine() },
                    onUpdateLine = { index, value -> viewModel.updateSetupInfoLine(index, value) },
                    onRemoveLine = { index -> viewModel.removeSetupInfoLine(index) },
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // confirm button
                Button(
                    onClick = { viewModel.saveExercise() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    enabled = !isSaving && exerciseName.isNotBlank() && (
                        selectedBarbell != null || 
                        selectedSmithMachine != null || 
                        selectedCableStack != null || 
                        selectedResistanceMachine != null ||
                        equipment.isNotEmpty()
                    )
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = if (isSaving) "Saving..." else "Create Exercise",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
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
                onNavigateToSmithMachine = {
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                    showSmithMachineSelection = true
                },
                onNavigateToBarbell = {
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                    showBarbellSelection = true
                },
                onNavigateToCableStack = {
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                    showCableStackSelection = true
                },
                onNavigateToResistanceMachine = {
                    showEquipmentSelection = false
                    scope.launch {
                        equipmentSheetState.hide()
                    }
                    showResistanceMachineSelection = true
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
        
        // smith machine selection bottom sheet
        if (showSmithMachineSelection) {
            LaunchedEffect(showSmithMachineSelection) {
                if (showSmithMachineSelection) {
                    smithMachineSheetState.show()
                }
            }
            SmithMachineSelectionBottomSheet(
                smithMachineConfigurations = smithMachineConfigurations,
                onDismiss = {
                    showSmithMachineSelection = false
                    scope.launch {
                        smithMachineSheetState.hide()
                    }
                },
                onSelectSmithMachine = { selectedConfiguration ->
                    viewModel.selectSmithMachine(selectedConfiguration)
                    showSmithMachineSelection = false
                    scope.launch {
                        smithMachineSheetState.hide()
                    }
                },
                onSmithMachineCreated = { smithMachineConfiguration ->
                    // refresh smith machine configurations after creation
                    viewModel.refreshEquipmentConfigurations()
                    // auto-select the newly created smith machine
                    viewModel.selectSmithMachine(smithMachineConfiguration)
                    showSmithMachineSelection = false
                    scope.launch {
                        smithMachineSheetState.hide()
                    }
                },
                sheetState = smithMachineSheetState
            )
        }
        
        // barbell selection bottom sheet
        if (showBarbellSelection) {
            LaunchedEffect(showBarbellSelection) {
                if (showBarbellSelection) {
                    barbellSheetState.show()
                }
            }
            BarbellSelectionBottomSheet(
                barbellConfigurations = barbellConfigurations,
                onDismiss = {
                    showBarbellSelection = false
                    scope.launch {
                        barbellSheetState.hide()
                    }
                },
                onSelectBarbell = { selectedConfiguration ->
                    viewModel.selectBarbell(selectedConfiguration)
                    showBarbellSelection = false
                    scope.launch {
                        barbellSheetState.hide()
                    }
                },
                onBarbellCreated = { barbellConfiguration ->
                    // refresh barbell configurations after creation
                    viewModel.refreshEquipmentConfigurations()
                    // auto-select the newly created barbell
                    viewModel.selectBarbell(barbellConfiguration)
                    showBarbellSelection = false
                    scope.launch {
                        barbellSheetState.hide()
                    }
                },
                sheetState = barbellSheetState
            )
        }
        
        // cable stack selection bottom sheet
        if (showCableStackSelection) {
            LaunchedEffect(showCableStackSelection) {
                if (showCableStackSelection) {
                    cableStackSheetState.show()
                }
            }
            CableStackSelectionBottomSheet(
                cableStackConfigurations = cableStackConfigurations,
                onDismiss = {
                    showCableStackSelection = false
                    scope.launch {
                        cableStackSheetState.hide()
                    }
                },
                onSelectCableStack = { selectedConfiguration ->
                    viewModel.selectCableStack(selectedConfiguration)
                    showCableStackSelection = false
                    scope.launch {
                        cableStackSheetState.hide()
                    }
                },
                onCableStackCreated = { cableStackConfiguration ->
                    // refresh cable stack configurations after creation
                    viewModel.refreshEquipmentConfigurations()
                    // auto-select the newly created cable stack
                    viewModel.selectCableStack(cableStackConfiguration)
                    showCableStackSelection = false
                    scope.launch {
                        cableStackSheetState.hide()
                    }
                },
                sheetState = cableStackSheetState
            )
        }
        
        // resistance machine selection bottom sheet
        if (showResistanceMachineSelection) {
            LaunchedEffect(showResistanceMachineSelection) {
                if (showResistanceMachineSelection) {
                    resistanceMachineSheetState.show()
                }
            }
            ResistanceMachineSelectionBottomSheet(
                resistanceMachineConfigurations = resistanceMachineConfigurations,
                onDismiss = {
                    showResistanceMachineSelection = false
                    scope.launch {
                        resistanceMachineSheetState.hide()
                    }
                },
                onSelectResistanceMachine = { selectedConfiguration ->
                    viewModel.selectResistanceMachine(selectedConfiguration)
                    showResistanceMachineSelection = false
                    scope.launch {
                        resistanceMachineSheetState.hide()
                    }
                },
                onResistanceMachineCreated = { resistanceMachineConfiguration ->
                    // refresh resistance machine configurations after creation
                    viewModel.refreshEquipmentConfigurations()
                    // auto-select the newly created resistance machine
                    viewModel.selectResistanceMachine(resistanceMachineConfiguration)
                    showResistanceMachineSelection = false
                    scope.launch {
                        resistanceMachineSheetState.hide()
                    }
                },
                sheetState = resistanceMachineSheetState
            )
        }
    }
} 