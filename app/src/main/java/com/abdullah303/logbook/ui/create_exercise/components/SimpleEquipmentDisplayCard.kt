package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.Equipment

@Composable
fun SimpleEquipmentDisplayCard(
    title: String,
    selectedEquipment: Equipment?,
    onCardClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    // convert simple equipment to unified equipment configuration
    val unifiedConfiguration = selectedEquipment?.let { equipment ->
        equipment.asSimpleEquipment()
    }

    EquipmentDisplayCard(
        title = title,
        selectedConfiguration = unifiedConfiguration,
        onCardClick = onCardClick,
        onClearSelection = onClearSelection,
        modifier = modifier
    )
} 