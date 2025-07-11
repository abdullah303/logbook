package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BarbellDisplayCard(
    title: String,
    selectedBarbell: BarbellConfiguration?,
    onCardClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    // convert barbell configuration to unified equipment configuration
    val unifiedConfiguration = selectedBarbell?.let { barbellConfig ->
        barbellConfig.equipment.withBarbellInfo(barbellConfig.barbellInfo)
    }

    WeightEquipmentDisplayCard(
        title = title,
        selectedConfiguration = unifiedConfiguration,
        onCardClick = onCardClick,
        onClearSelection = onClearSelection,
        modifier = modifier
    )
} 