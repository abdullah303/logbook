package com.abdullah303.logbook.features.home.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.core.utils.MuscleHeatmapImage
import com.abdullah303.logbook.core.utils.WorkoutMuscleHeatmapImage
import com.abdullah303.logbook.core.utils.countTotalSets
import com.abdullah303.logbook.features.home.data.ExerciseTemplate
import com.abdullah303.logbook.features.home.data.Split
import com.abdullah303.logbook.features.home.data.Workout

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
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
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
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${workout.countTotalSets()} sets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                color = MaterialTheme.colorScheme.outlineVariant
            )
            
            // Exercises List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workout.exercises) { exercise ->
                    val exerciseTemplate = exerciseTemplates.find { it.id == exercise.exerciseTemplateId }
                    exerciseTemplate?.let {
                        ExerciseListItem(
                            exercise = exercise,
                            exerciseTemplate = it
                        )
                    }
                }
            }
        }
    }
} 