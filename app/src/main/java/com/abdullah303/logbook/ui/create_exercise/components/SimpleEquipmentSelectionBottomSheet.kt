package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.Equipment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleEquipmentSelectionBottomSheet(
    title: String,
    equipment: List<Equipment>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectEquipment: (Equipment) -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    // convert simple equipment to unified equipment configurations
    val unifiedConfigurations = equipment.map { equipment ->
        equipment.asSimpleEquipment()
    }

    EquipmentSelectionBottomSheet(
        title = title,
        configurations = unifiedConfigurations,
        modifier = modifier,
        onDismiss = onDismiss,
        onSelectConfiguration = { unifiedConfig ->
            onSelectEquipment(unifiedConfig.equipment)
        },
        onNavigateToCreateConfiguration = {
            // simple equipment doesn't support creation, so do nothing
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction,
        showCreateButton = false // hide create button for simple equipment
    )
} 