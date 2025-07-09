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
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.ui.create_equipment.create_barbell.CreateBarbellBottomSheet
import kotlinx.coroutines.launch

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
    onBarbellCreated: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var showCreateBarbell by remember { mutableStateOf(false) }
    val createBarbellSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
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
        onNavigateToCreateConfiguration = {
            showCreateBarbell = true
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )
    
    // show create barbell bottom sheet when needed
    if (showCreateBarbell) {
        LaunchedEffect(showCreateBarbell) {
            if (showCreateBarbell) {
                createBarbellSheetState.show()
            }
        }
        CreateBarbellBottomSheet(
            onDismiss = {
                showCreateBarbell = false
                scope.launch {
                    createBarbellSheetState.hide()
                }
            },
            onBarbellCreated = {
                onBarbellCreated()
                showCreateBarbell = false
                scope.launch {
                    createBarbellSheetState.hide()
                }
            },
            sheetState = createBarbellSheetState,
            maxHeightFraction = maxHeightFraction
        )
    }
} 