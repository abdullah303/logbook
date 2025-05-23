package com.abdullah303.logbook.features.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.home.data.ExerciseInstance
import com.abdullah303.logbook.features.home.data.ExerciseTemplate

@Composable
fun ExerciseListItem(
    exercise: ExerciseInstance,
    exerciseTemplate: ExerciseTemplate,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
            .padding(12.dp)
    ) {
        Text(
            text = exerciseTemplate.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MetricChip(
                text = "${exercise.sets} sets"
            )
            MetricChip(
                text = "${exercise.targetRepRange.first}-${exercise.targetRepRange.last} reps"
            )
            MetricChip(
                text = "RIR ${exercise.targetRIR}"
            )
        }
    }
} 