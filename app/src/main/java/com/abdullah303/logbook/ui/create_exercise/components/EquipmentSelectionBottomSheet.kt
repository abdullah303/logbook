package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.ui.components.GenericBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentSelectionBottomSheet(
    selectedEquipment: List<EquipmentType>,
    onDismiss: () -> Unit,
    onConfirm: (List<EquipmentType>) -> Unit,
    onNavigateToSmithMachine: () -> Unit,
    onNavigateToBarbell: () -> Unit,
    onNavigateToCableStack: () -> Unit,
    onNavigateToResistanceMachine: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    // group equipment into 2 columns for better layout
    val equipmentColumns = EquipmentType.entries.chunked((EquipmentType.entries.size + 1) / 2)
    
    GenericBottomSheet(
        title = "Select Equipment",
        modifier = modifier,
        onDismiss = onDismiss,
        sheetState = sheetState,
        maxHeightFraction = 0.75f
    ) {
        Column {
            // equipment selection grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                equipmentColumns.forEach { columnEquipment ->
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(columnEquipment) { equipment ->
                            EquipmentCard(
                                equipment = equipment,
                                isSelected = selectedEquipment.contains(equipment),
                                onToggle = {
                                    // special handling for equipment with configurations
                                    when (equipment) {
                                        EquipmentType.SMITH_MACHINE -> {
                                            onNavigateToSmithMachine()
                                        }
                                        EquipmentType.BARBELL -> {
                                            onNavigateToBarbell()
                                        }
                                        EquipmentType.CABLE_STACK -> {
                                            onNavigateToCableStack()
                                        }
                                        EquipmentType.RESISTANCE_MACHINE -> {
                                            onNavigateToResistanceMachine()
                                        }
                                        else -> {
                                            // single select - replace current selection
                                            val newSelection = if (selectedEquipment.contains(equipment)) {
                                                emptyList<EquipmentType>()
                                            } else {
                                                listOf(equipment)
                                            }
                                            onConfirm(newSelection)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EquipmentCard(
    equipment: EquipmentType,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onToggle,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = equipment.name.lowercase().replace('_', ' '),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
} 