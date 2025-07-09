package com.abdullah303.logbook.ui.create_equipment.create_cable_stack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.ui.components.FormInputCard
import com.abdullah303.logbook.ui.create_exercise.components.CableStackConfiguration
import com.abdullah303.logbook.ui.components.GenericBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionType
import com.abdullah303.logbook.ui.components.WeightRangeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCableStackBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onCableStackCreated: (CableStackConfiguration) -> Unit = { _ -> },
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f,
    viewModel: CreateCableStackViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMinWeightSelection by remember { mutableStateOf(false) }
    var showMaxWeightSelection by remember { mutableStateOf(false) }
    var showIncrementSelection by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val minWeightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val maxWeightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val incrementSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // generate min weight values: 0 to max field value, with increment step
    val minWeightValues = remember(uiState.weightUnit, uiState.maxWeight, uiState.increment) {
        val maxValue = uiState.maxWeight.toFloatOrNull() ?: 100f
        val increment = (uiState.increment.toFloatOrNull() ?: 0.5f).coerceAtLeast(0.5f)
        
        val steps = (maxValue / increment).toInt() + 1
        (0 until steps).map { it * increment }.filter { it <= maxValue }
    }

    // generate max weight values: min field value to 1000kg, with increment step
    val maxWeightValues = remember(uiState.weightUnit, uiState.minWeight, uiState.increment) {
        val minValue = uiState.minWeight.toFloatOrNull() ?: 0f
        val increment = (uiState.increment.toFloatOrNull() ?: 0.5f).coerceAtLeast(0.5f)
        val maxLimit = when (uiState.weightUnit) {
            WeightUnit.KG -> 1000f
            WeightUnit.LB -> 2200f // roughly 1000kg in lbs
        }
        
        val steps = ((maxLimit - minValue) / increment).toInt() + 1
        (0 until steps).map { minValue + (it * increment) }.filter { it <= maxLimit }
    }

    // generate increment values: 0 to 20 with 0.5 increment
    val incrementValues = remember(uiState.weightUnit) {
        (0..40).map { it * 0.5f }.filter { it <= 20f }
    }

    GenericBottomSheet(
        title = "create cable stack",
        modifier = modifier,
        onDismiss = {
            viewModel.onDismiss() // reset state when dismissed
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
            // sleek surface container with subtle gradient effect
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
                    // cable stack name input card
                    FormInputCard(
                        title = "cable stack name",
                        value = uiState.name,
                        onValueChange = { name ->
                            errorMessage = null // clear any previous error when user types
                            viewModel.updateName(name)
                        },
                        placeholder = "enter cable stack name..."
                    )

                    // weight range card with min, max, and increment
                    WeightRangeCard(
                        title = "weight range",
                        minValue = uiState.minWeight,
                        maxValue = uiState.maxWeight,
                        incrementValue = uiState.increment,
                        weightUnit = uiState.weightUnit,
                        onMinClick = { 
                            errorMessage = null // clear any previous error when user interacts
                            showMinWeightSelection = true 
                        },
                        onMaxClick = {
                            errorMessage = null // clear any previous error when user interacts
                            showMaxWeightSelection = true
                        },
                        onIncrementClick = {
                            errorMessage = null // clear any previous error when user interacts
                            showIncrementSelection = true
                        },
                        onWeightUnitChange = viewModel::updateWeightUnit
                    )
                    
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

            // sleek create button with gradient-like appearance
            Button(
                onClick = {
                    errorMessage = null // clear any previous error
                    viewModel.createCableStack(
                        onSuccess = { cableStackConfiguration ->
                            onCableStackCreated(cableStackConfiguration)
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
                enabled = uiState.name.isNotBlank() && 
                         uiState.minWeight.isNotBlank() && 
                         uiState.maxWeight.isNotBlank() && 
                         uiState.increment.isNotBlank(),
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
                    text = "create cable stack",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }

    // min weight selection bottom sheet
    if (showMinWeightSelection) {
        ValueSelectionBottomSheet(
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

    // max weight selection bottom sheet
    if (showMaxWeightSelection) {
        ValueSelectionBottomSheet(
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

    // increment selection bottom sheet
    if (showIncrementSelection) {
        ValueSelectionBottomSheet(
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
} 