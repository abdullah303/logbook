package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.ui.create_equipment.create_resistance_machine.CreateResistanceMachineBottomSheet
import kotlinx.coroutines.launch

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
    onResistanceMachineCreated: (ResistanceMachineConfiguration) -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var showCreateResistanceMachine by remember { mutableStateOf(false) }
    val createResistanceMachineSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
    // convert resistance machine configurations to unified equipment configurations
    val unifiedConfigurations = resistanceMachineConfigurations.map { resistanceMachineConfig ->
        EquipmentConfiguration.ResistanceMachineEquipment(
            equipment = resistanceMachineConfig.equipment,
            resistanceMachineInfo = resistanceMachineConfig.resistanceMachineInfo
        )
    }

    EquipmentSelectionBottomSheet(
        title = "Resistance Machine Configurations",
        configurations = unifiedConfigurations,
        modifier = modifier,
        onDismiss = onDismiss,
        onSelectConfiguration = { unifiedConfig ->
            // convert back to resistance machine configuration
            val originalResistanceMachineConfig = resistanceMachineConfigurations.find { 
                it.equipment.id == unifiedConfig.equipment.id 
            }
            originalResistanceMachineConfig?.let { onSelectResistanceMachine(it) }
        },
        onNavigateToCreateConfiguration = {
            showCreateResistanceMachine = true
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )

    // show create resistance machine bottom sheet when needed
    if (showCreateResistanceMachine) {
        LaunchedEffect(showCreateResistanceMachine) {
            if (showCreateResistanceMachine) {
                createResistanceMachineSheetState.show()
            }
        }
        CreateResistanceMachineBottomSheet(
            onDismiss = {
                showCreateResistanceMachine = false
                scope.launch {
                    createResistanceMachineSheetState.hide()
                }
            },
            onResistanceMachineCreated = { resistanceMachineConfiguration ->
                onResistanceMachineCreated(resistanceMachineConfiguration)
                showCreateResistanceMachine = false
                scope.launch {
                    createResistanceMachineSheetState.hide()
                }
            },
            sheetState = createResistanceMachineSheetState,
            maxHeightFraction = maxHeightFraction
        )
    }
} 