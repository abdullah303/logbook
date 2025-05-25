package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.core.utils.WorkoutMuscleHeatmapImage
import com.abdullah303.logbook.core.utils.countTotalSets
import com.abdullah303.logbook.features.splits.data.ExerciseTemplate
import com.abdullah303.logbook.features.splits.data.Split
import com.abdullah303.logbook.features.splits.data.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(
    workout: Workout,
    exerciseTemplates: List<ExerciseTemplate>,
    split: Split,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(320.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Workout Title Section with muscle image positioned in top right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                // Workout info on the left
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 88.dp) // Leave space for the muscle image
                ) {
                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${workout.countTotalSets()} sets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                
                // Muscle image positioned in top right
                WorkoutMuscleHeatmapImage(
                    workout = workout,
                    heatmapColor = "FF5722", // Material Design Deep Orange
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.TopEnd)
                )
            }
            
            // Divider
            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
            
            // Timeline with exercise items
            Timeline(
                exercises = workout.exercises,
                exerciseTemplates = exerciseTemplates,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
} 