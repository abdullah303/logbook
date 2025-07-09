package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.data.model.Muscles

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseDisplayCard(
    exerciseWithEquipment: com.abdullah303.logbook.ui.create_split.ExerciseWithEquipment,
    onExerciseClick: (com.abdullah303.logbook.ui.create_split.ExerciseWithEquipment) -> Unit,
    modifier: Modifier = Modifier
) {
    val exercise = exerciseWithEquipment.exercise
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onExerciseClick(exerciseWithEquipment) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // exercise name and equipment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                
                Text(
                    text = exerciseWithEquipment.equipmentName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal
                )
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // muscles with hashtag styling - different colors for primary and auxiliary
            if (exercise.primaryMuscles.isNotEmpty() || exercise.auxiliaryMuscles.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // primary muscles
                    if (exercise.primaryMuscles.isNotEmpty()) {
                        Text(
                            text = exercise.primaryMuscles.joinToString(" ") { muscle ->
                                "#${muscle.name.lowercase().replace("_", "")}"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // auxiliary muscles
                    if (exercise.auxiliaryMuscles.isNotEmpty()) {
                        Text(
                            text = exercise.auxiliaryMuscles.joinToString(" ") { muscle ->
                                "#${muscle.name.lowercase().replace("_", "")}"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
} 