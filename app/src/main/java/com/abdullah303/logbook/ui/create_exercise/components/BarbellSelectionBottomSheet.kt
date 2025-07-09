package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import com.abdullah303.logbook.data.local.entity.Equipment

// data class to combine equipment and barbell info
data class BarbellConfiguration(
    val equipment: Equipment,
    val barbellInfo: BarbellInfo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarbellSelectionBottomSheet(
    barbellConfigurations: List<BarbellConfiguration>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectBarbell: (BarbellConfiguration) -> Unit = {},
    onNavigateToCreateBarbell: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    // convert barbell configurations to generic weight equipment configurations
    val weightEquipmentConfigurations = barbellConfigurations.map { barbellConfig ->
        barbellConfig.equipment.withBarbellInfo(barbellConfig.barbellInfo)
    }

    WeightEquipmentSelectionBottomSheet(
        title = "Barbell Configurations",
        configurations = weightEquipmentConfigurations,
        modifier = modifier,
        onDismiss = onDismiss,
        onSelectConfiguration = { weightConfig ->
            // convert back to barbell configuration
            val originalBarbellConfig = barbellConfigurations.find { 
                it.equipment.id == weightConfig.equipment.id 
            }
            originalBarbellConfig?.let { onSelectBarbell(it) }
        },
        onNavigateToCreateConfiguration = onNavigateToCreateBarbell,
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )
} 