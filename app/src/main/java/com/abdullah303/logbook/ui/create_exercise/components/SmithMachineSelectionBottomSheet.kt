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
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import com.abdullah303.logbook.ui.create_equipment.create_smith_machine.CreateSmithMachineBottomSheet
import kotlinx.coroutines.launch

// data class to combine equipment and smith machine info
data class SmithMachineConfiguration(
    val equipment: Equipment,
    val smithMachineInfo: SmithMachineInfo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmithMachineSelectionBottomSheet(
    smithMachineConfigurations: List<SmithMachineConfiguration>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectSmithMachine: (SmithMachineConfiguration) -> Unit = {},
    onSmithMachineCreated: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var showCreateSmithMachine by remember { mutableStateOf(false) }
    val createSmithMachineSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
    // convert smith machine configurations to generic weight equipment configurations
    val weightEquipmentConfigurations = smithMachineConfigurations.map { smithConfig ->
        smithConfig.equipment.withSmithMachineInfo(smithConfig.smithMachineInfo)
    }

    WeightEquipmentSelectionBottomSheet(
        title = "Smith Machine Configurations",
        configurations = weightEquipmentConfigurations,
        modifier = modifier,
        onDismiss = onDismiss,
        onSelectConfiguration = { weightConfig ->
            // convert back to smith machine configuration
            val originalSmithConfig = smithMachineConfigurations.find { 
                it.equipment.id == weightConfig.equipment.id 
            }
            originalSmithConfig?.let { onSelectSmithMachine(it) }
        },
        onNavigateToCreateConfiguration = {
            showCreateSmithMachine = true
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )
    
    // show create smith machine bottom sheet when needed
    if (showCreateSmithMachine) {
        LaunchedEffect(showCreateSmithMachine) {
            if (showCreateSmithMachine) {
                createSmithMachineSheetState.show()
            }
        }
        CreateSmithMachineBottomSheet(
            onDismiss = {
                showCreateSmithMachine = false
                scope.launch {
                    createSmithMachineSheetState.hide()
                }
            },
            onSmithMachineCreated = {
                onSmithMachineCreated()
                showCreateSmithMachine = false
                scope.launch {
                    createSmithMachineSheetState.hide()
                }
            },
            sheetState = createSmithMachineSheetState,
            maxHeightFraction = maxHeightFraction
        )
    }
} 