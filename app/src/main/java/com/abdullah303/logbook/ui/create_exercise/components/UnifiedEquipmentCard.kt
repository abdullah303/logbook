package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.model.EquipmentType

@Composable
fun UnifiedEquipmentCard(
    selectedEquipment: List<EquipmentType>,
    selectedBarbell: BarbellConfiguration?,
    selectedSmithMachine: SmithMachineConfiguration?,
    selectedCableStack: CableStackConfiguration?,
    selectedResistanceMachine: ResistanceMachineConfiguration?,
    onCardClick: () -> Unit,
    onBarbellClick: () -> Unit = {},
    onSmithMachineClick: () -> Unit = {},
    onCableStackClick: () -> Unit = {},
    onResistanceMachineClick: () -> Unit = {},
    onClearCustomEquipment: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Equipment",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    when {
                        selectedBarbell != null -> onBarbellClick()
                        selectedSmithMachine != null -> onSmithMachineClick()
                        selectedCableStack != null -> onCableStackClick()
                        selectedResistanceMachine != null -> onResistanceMachineClick()
                        else -> onCardClick()
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceDim
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            when {
                // show custom equipment configurations if any are selected
                selectedBarbell != null -> {
                    CustomEquipmentContent(
                        title = selectedBarbell.equipment.name,
                        configuration = selectedBarbell.equipment.withBarbellInfo(selectedBarbell.barbellInfo),
                        onClear = onClearCustomEquipment
                    )
                }
                selectedSmithMachine != null -> {
                    CustomEquipmentContent(
                        title = selectedSmithMachine.equipment.name,
                        configuration = selectedSmithMachine.equipment.withSmithMachineInfo(selectedSmithMachine.smithMachineInfo),
                        onClear = onClearCustomEquipment
                    )
                }
                selectedCableStack != null -> {
                    CustomEquipmentContent(
                        title = selectedCableStack.equipment.name,
                        configuration = selectedCableStack.equipment.withCableStackInfo(selectedCableStack.cableStackInfo),
                        onClear = onClearCustomEquipment
                    )
                }
                selectedResistanceMachine != null -> {
                    CustomEquipmentContent(
                        title = selectedResistanceMachine.equipment.name,
                        configuration = EquipmentConfiguration.ResistanceMachineEquipment(
                            equipment = selectedResistanceMachine.equipment,
                            resistanceMachineInfo = selectedResistanceMachine.resistanceMachineInfo
                        ),
                        onClear = onClearCustomEquipment
                    )
                }
                // show simple equipment if selected
                selectedEquipment.isNotEmpty() -> {
                    Text(
                        text = selectedEquipment.first().name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                // show default state
                else -> {
                    Text(
                        text = "Tap to select equipment",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomEquipmentContent(
    title: String,
    configuration: EquipmentConfiguration,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // display configuration details
            val displayInfo = configuration.getDisplayInfo()
            if (displayInfo.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                
                displayInfo.forEach { info ->
                    Text(
                        text = info,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        IconButton(
            onClick = onClear
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear selection",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 