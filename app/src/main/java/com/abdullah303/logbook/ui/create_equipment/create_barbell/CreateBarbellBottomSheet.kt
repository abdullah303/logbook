package com.abdullah303.logbook.ui.create_equipment.create_barbell

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.ui.components.FormInputCard
import com.abdullah303.logbook.ui.components.FormSelectionCard
import com.abdullah303.logbook.ui.components.GenericBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionType
import com.abdullah303.logbook.ui.components.WeightUnitButtonGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBarbellBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onBarbellCreated: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f,
    viewModel: CreateBarbellViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showWeightSelection by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val weightSelectionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // generate weight values based on unit
    val weightValues = remember(uiState.weightUnit) {
        when (uiState.weightUnit) {
            WeightUnit.KG -> (0..200).map { it * 0.5f }.filter { it >= 0.5f } // 0.5kg to 100kg in 0.5kg increments
            WeightUnit.LB -> (0..440).map { it * 0.5f }.filter { it >= 1f } // 1lb to 220lb in 0.5lb increments
        }
    }

    GenericBottomSheet(
        title = "create barbell",
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
                    // barbell name input card
                    FormInputCard(
                        title = "barbell name",
                        value = uiState.name,
                        onValueChange = { name ->
                            errorMessage = null // clear any previous error when user types
                            viewModel.updateName(name)
                        },
                        placeholder = "enter barbell name..."
                    )

                    // barbell weight selection card with unit toggle
                    FormSelectionCard(
                        title = "bar weight",
                        value = if (uiState.weight.isNotBlank()) "${uiState.weight} ${uiState.weightUnit.name.lowercase()}" else "select weight...",
                        onSelectionClick = { 
                            errorMessage = null // clear any previous error when user interacts
                            showWeightSelection = true 
                        },
                        headerContent = {
                            WeightUnitButtonGroup(
                                selectedUnit = uiState.weightUnit,
                                onUnitSelected = viewModel::updateWeightUnit,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
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
                    viewModel.createBarbell(
                        onSuccess = {
                            onBarbellCreated()
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
                enabled = uiState.name.isNotBlank() && uiState.weight.isNotBlank(),
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
                    text = "create barbell",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }

    // weight selection bottom sheet
    if (showWeightSelection) {
        ValueSelectionBottomSheet(
            title = "select weight",
            selectionType = ValueSelectionType.WeightSelection(
                values = weightValues,
                initialValue = uiState.weight.toFloatOrNull() ?: weightValues.first(),
                unit = uiState.weightUnit.name.lowercase(),
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString() 
                    else String.format("%.1f", value)
                }
            ),
            sheetState = weightSelectionSheetState,
            onDismiss = { showWeightSelection = false },
            onConfirm = { weight ->
                viewModel.updateWeight(weight.toString())
                showWeightSelection = false
            }
        )
    }
} 