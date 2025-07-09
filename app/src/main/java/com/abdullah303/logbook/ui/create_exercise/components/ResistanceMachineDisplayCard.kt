package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ResistanceMachineDisplayCard(
    title: String,
    selectedResistanceMachine: ResistanceMachineConfiguration?,
    onCardClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    // convert resistance machine configuration to unified equipment configuration
    val unifiedConfiguration = selectedResistanceMachine?.let { resistanceMachineConfig ->
        EquipmentConfiguration.ResistanceMachineEquipment(
            equipment = resistanceMachineConfig.equipment,
            resistanceMachineInfo = resistanceMachineConfig.resistanceMachineInfo
        )
    }

    EquipmentDisplayCard(
        title = title,
        selectedConfiguration = unifiedConfiguration,
        onCardClick = onCardClick,
        onClearSelection = onClearSelection,
        modifier = modifier
    )
} 