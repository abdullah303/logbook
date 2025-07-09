package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo

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
    onNavigateToCreateSmithMachine: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
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
        onNavigateToCreateConfiguration = onNavigateToCreateSmithMachine,
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )
} 