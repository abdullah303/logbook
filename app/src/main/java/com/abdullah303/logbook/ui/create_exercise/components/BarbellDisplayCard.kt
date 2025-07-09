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
    // convert barbell configuration to generic weight equipment configuration
    val weightEquipmentConfig = selectedBarbell?.let { barbellConfig ->
        barbellConfig.equipment.withBarbellInfo(barbellConfig.barbellInfo)
    }

    WeightEquipmentDisplayCard(
        title = title,
        selectedConfiguration = weightEquipmentConfig,
        onCardClick = onCardClick,
        onClearSelection = onClearSelection,
        modifier = modifier
    )
} 