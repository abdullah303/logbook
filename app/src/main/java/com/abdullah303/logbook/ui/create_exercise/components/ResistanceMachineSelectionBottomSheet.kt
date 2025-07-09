package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.ResistanceMachineType
import com.abdullah303.logbook.ui.components.GenericBottomSheet
import com.abdullah303.logbook.ui.create_equipment.create_resistance_machine.CreateResistanceMachineBottomSheet

// data class to combine equipment and resistance machine info
data class ResistanceMachineConfiguration(
    val equipment: Equipment,
    val resistanceMachineInfo: ResistanceMachineEquipmentInfo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResistanceMachineSelectionBottomSheet(
    resistanceMachineConfigurations: List<ResistanceMachineConfiguration>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectResistanceMachine: (ResistanceMachineConfiguration) -> Unit = {},
    onResistanceMachineCreated: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var searchQuery by remember { mutableStateOf("") }
    var showCreateResistanceMachine by remember { mutableStateOf(false) }
    val createResistanceMachineSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    // filter configurations based on search query
    val filteredConfigurations = resistanceMachineConfigurations.filter {
        it.equipment.name.contains(searchQuery, ignoreCase = true)
    }

    GenericBottomSheet(
        title = "Resistance Machine Configurations",
        modifier = modifier,
        onDismiss = onDismiss,
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    ) {
        // search bar with add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Search configurations...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                IconButton(
                    onClick = { showCreateResistanceMachine = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Resistance Machine Configuration",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // configurations list
        if (filteredConfigurations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) {
                        "No Resistance Machine configurations available yet"
                    } else {
                        "No configurations found matching \"$searchQuery\""
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredConfigurations) { configuration ->
                    ResistanceMachineConfigurationCard(
                        configuration = configuration,
                        onSelect = { onSelectResistanceMachine(configuration) }
                    )
                }
            }
        }
    }

    // create resistance machine bottom sheet
    if (showCreateResistanceMachine) {
        CreateResistanceMachineBottomSheet(
            onDismiss = { showCreateResistanceMachine = false },
            onResistanceMachineCreated = {
                onResistanceMachineCreated()
                showCreateResistanceMachine = false
            },
            sheetState = createResistanceMachineSheetState
        )
    }
}

@Composable
private fun ResistanceMachineConfigurationCard(
    configuration: ResistanceMachineConfiguration,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onSelect,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = configuration.equipment.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // show type
            Text(
                text = "Type: ${configuration.resistanceMachineInfo.type.name.lowercase().replace('_', ' ')}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(2.dp))
            
            // show type-specific information
            when (configuration.resistanceMachineInfo) {
                is PinLoadedEquipmentInfo -> {
                    val weightUnit = configuration.equipment.weight_unit.name.lowercase()
                    Text(
                        text = "Weight Range: ${configuration.resistanceMachineInfo.min_weight} - ${configuration.resistanceMachineInfo.max_weight} $weightUnit",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Increment: ${configuration.resistanceMachineInfo.increment} $weightUnit",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                is PlateLoadedEquipmentInfo -> {
                    val weightUnit = configuration.equipment.weight_unit.name.lowercase()
                    Text(
                        text = "Base Weight: ${configuration.resistanceMachineInfo.base_machine_weight} $weightUnit",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Number of Pegs: ${configuration.resistanceMachineInfo.num_pegs}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
} 