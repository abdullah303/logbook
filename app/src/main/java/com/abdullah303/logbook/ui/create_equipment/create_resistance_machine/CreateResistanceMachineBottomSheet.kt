package com.abdullah303.logbook.ui.create_equipment.create_resistance_machine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdullah303.logbook.data.model.ResistanceMachineType
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.ui.components.FormInputCard
import com.abdullah303.logbook.ui.components.FormSelectionCard
import com.abdullah303.logbook.ui.components.GenericBottomSheet
import com.abdullah303.logbook.ui.components.MachineTypeButtonGroup
import com.abdullah303.logbook.ui.components.ValueSelectionBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionType
import com.abdullah303.logbook.ui.components.WeightRangeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateResistanceMachineBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onResistanceMachineCreated: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f,
    viewModel: CreateResistanceMachineViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMinWeightSelection by remember { mutableStateOf(false) }
    var showMaxWeightSelection by remember { mutableStateOf(false) }
    var showIncrementSelection by remember { mutableStateOf(false) }
    var showPegCountSelection by remember { mutableStateOf(false) }
    var showBaseMachineWeightSelection by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val minWeightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val maxWeightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val incrementSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pegCountSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val baseMachineWeightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // generate values for selections
    val minWeightValues = remember(uiState.weightUnit, uiState.maxWeight, uiState.increment) {
        val maxValue = uiState.maxWeight.toFloatOrNull() ?: 100f
        val increment = (uiState.increment.toFloatOrNull() ?: 0.5f).coerceAtLeast(0.5f)
        val steps = (maxValue / increment).toInt() + 1
        (0 until steps).map { it * increment }.filter { it <= maxValue }
    }

    val maxWeightValues = remember(uiState.weightUnit, uiState.minWeight, uiState.increment) {
        val minValue = uiState.minWeight.toFloatOrNull() ?: 0f
        val increment = (uiState.increment.toFloatOrNull() ?: 0.5f).coerceAtLeast(0.5f)
        val maxLimit = when (uiState.weightUnit) {
            WeightUnit.KG -> 1000f
            WeightUnit.LB -> 2200f
        }
        val steps = ((maxLimit - minValue) / increment).toInt() + 1
        (0 until steps).map { minValue + (it * increment) }.filter { it <= maxLimit }
    }

    val incrementValues = remember(uiState.weightUnit) {
        (0..40).map { it * 0.5f }.filter { it <= 20f }
    }

    val pegCountValues = remember { (1..4).toList() }

    val baseMachineWeightValues = remember(uiState.weightUnit) {
        val maxLimit = when (uiState.weightUnit) {
            WeightUnit.KG -> 200f
            WeightUnit.LB -> 440f
        }
        val increment = 0.5f
        val steps = (maxLimit / increment).toInt() + 1
        (0 until steps).map { it * increment }.filter { it <= maxLimit }
    }

    GenericBottomSheet(
        title = "create resistance machine",
        modifier = modifier,
        onDismiss = {
            viewModel.onDismiss()
            onDismiss()
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp)
                ) {
                    // resistance machine name input
                    FormInputCard(
                        title = "resistance machine name",
                        value = uiState.name,
                        onValueChange = { name ->
                            errorMessage = null
                            viewModel.updateName(name)
                        },
                        placeholder = "enter resistance machine name..."
                    )

                    // machine type selection
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "machine type",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        MachineTypeButtonGroup(
                            selectedType = uiState.machineType,
                            onTypeSelected = { machineType ->
                                errorMessage = null
                                viewModel.updateMachineType(machineType)
                            }
                        )
                    }

                    // conditional fields based on machine type
                    when (uiState.machineType) {
                        ResistanceMachineType.PIN_LOADED -> {
                            WeightRangeCard(
                                title = "weight range",
                                minValue = uiState.minWeight,
                                maxValue = uiState.maxWeight,
                                incrementValue = uiState.increment,
                                weightUnit = uiState.weightUnit,
                                onMinClick = { 
                                    errorMessage = null
                                    showMinWeightSelection = true 
                                },
                                onMaxClick = {
                                    errorMessage = null
                                    showMaxWeightSelection = true
                                },
                                onIncrementClick = {
                                    errorMessage = null
                                    showIncrementSelection = true
                                },
                                onWeightUnitChange = viewModel::updateWeightUnit
                            )
                        }
                        ResistanceMachineType.PLATE_LOADED -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
                            ) {
                                // weight unit selection header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "configuration",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    com.abdullah303.logbook.ui.components.WeightUnitButtonGroup(
                                        selectedUnit = uiState.weightUnit,
                                        onUnitSelected = viewModel::updateWeightUnit
                                    )
                                }
                                
                                // number of pegs and base machine weight in a row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    FormSelectionCard(
                                        title = "pegs",
                                        value = uiState.numPegs.toString(),
                                        onSelectionClick = {
                                            errorMessage = null
                                            showPegCountSelection = true
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    
                                    FormSelectionCard(
                                        title = "base weight",
                                        value = "${uiState.baseMachineWeight} ${uiState.weightUnit.name.lowercase()}",
                                        onSelectionClick = {
                                            errorMessage = null
                                            showBaseMachineWeightSelection = true
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                    
                    // error message display
                    errorMessage?.let { error ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = error,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // create button
            Button(
                onClick = {
                    errorMessage = null
                    viewModel.createResistanceMachine(
                        onSuccess = {
                            onResistanceMachineCreated()
                            onDismiss()
                        },
                        onError = { error ->
                            errorMessage = error
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.name.isNotBlank() && when (uiState.machineType) {
                    ResistanceMachineType.PIN_LOADED -> {
                        uiState.minWeight.isNotBlank() && 
                        uiState.maxWeight.isNotBlank() && 
                        uiState.increment.isNotBlank()
                    }
                    ResistanceMachineType.PLATE_LOADED -> {
                        uiState.baseMachineWeight.isNotBlank() && 
                        uiState.numPegs > 0
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = "create resistance machine",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }

    // value selection bottom sheets
    if (showMinWeightSelection) {
        ValueSelectionBottomSheet<Float>(
            title = "select minimum weight",
            selectionType = ValueSelectionType.WeightSelection(
                values = minWeightValues,
                initialValue = uiState.minWeight.toFloatOrNull() ?: minWeightValues.first(),
                unit = uiState.weightUnit.name.lowercase(),
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString() 
                    else String.format("%.1f", value)
                }
            ),
            sheetState = minWeightSelectionSheetState,
            onDismiss = { showMinWeightSelection = false },
            onConfirm = { weight ->
                viewModel.updateMinWeight(weight.toString())
                showMinWeightSelection = false
            }
        )
    }

    if (showMaxWeightSelection) {
        ValueSelectionBottomSheet<Float>(
            title = "select maximum weight",
            selectionType = ValueSelectionType.WeightSelection(
                values = maxWeightValues,
                initialValue = uiState.maxWeight.toFloatOrNull() ?: maxWeightValues.firstOrNull() ?: 100f,
                unit = uiState.weightUnit.name.lowercase(),
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString() 
                    else String.format("%.1f", value)
                }
            ),
            sheetState = maxWeightSelectionSheetState,
            onDismiss = { showMaxWeightSelection = false },
            onConfirm = { weight ->
                viewModel.updateMaxWeight(weight.toString())
                showMaxWeightSelection = false
            }
        )
    }

    if (showIncrementSelection) {
        ValueSelectionBottomSheet<Float>(
            title = "select increment",
            selectionType = ValueSelectionType.WeightSelection(
                values = incrementValues,
                initialValue = uiState.increment.toFloatOrNull() ?: incrementValues.firstOrNull() ?: 0.5f,
                unit = uiState.weightUnit.name.lowercase(),
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString() 
                    else String.format("%.1f", value)
                }
            ),
            sheetState = incrementSelectionSheetState,
            onDismiss = { showIncrementSelection = false },
            onConfirm = { increment ->
                viewModel.updateIncrement(increment.toString())
                showIncrementSelection = false
            }
        )
    }

    if (showPegCountSelection) {
        ValueSelectionBottomSheet<Int>(
            title = "select number of pegs",
            selectionType = ValueSelectionType.SingleInteger(
                values = pegCountValues,
                initialValue = uiState.numPegs
            ),
            sheetState = pegCountSelectionSheetState,
            onDismiss = { showPegCountSelection = false },
            onConfirm = { pegs ->
                viewModel.updateNumberOfPegs(pegs)
                showPegCountSelection = false
            }
        )
    }

    if (showBaseMachineWeightSelection) {
        ValueSelectionBottomSheet<Float>(
            title = "select base machine weight",
            selectionType = ValueSelectionType.WeightSelection(
                values = baseMachineWeightValues,
                initialValue = uiState.baseMachineWeight.toFloatOrNull() ?: baseMachineWeightValues.first(),
                unit = uiState.weightUnit.name.lowercase(),
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString() 
                    else String.format("%.1f", value)
                }
            ),
            sheetState = baseMachineWeightSelectionSheetState,
            onDismiss = { showBaseMachineWeightSelection = false },
            onConfirm = { weight ->
                viewModel.updateBaseMachineWeight(weight.toString())
                showBaseMachineWeightSelection = false
            }
        )
    }
} 