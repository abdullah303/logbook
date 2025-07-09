package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SmithMachineDisplayCard(
    title: String,
    selectedSmithMachine: SmithMachineConfiguration?,
    onCardClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    // convert smith machine configuration to unified equipment configuration
    val unifiedConfiguration = selectedSmithMachine?.let { smithConfig ->
        smithConfig.equipment.withSmithMachineInfo(smithConfig.smithMachineInfo)
    }

    WeightEquipmentDisplayCard(
        title = title,
        selectedConfiguration = unifiedConfiguration,
        onCardClick = onCardClick,
        onClearSelection = onClearSelection,
        modifier = modifier
    )
} 