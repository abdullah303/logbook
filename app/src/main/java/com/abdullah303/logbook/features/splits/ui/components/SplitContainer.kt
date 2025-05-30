package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.splits.data.ExerciseTemplate
import com.abdullah303.logbook.features.splits.data.Split

@Composable
fun SplitContainer(
    split: Split,
    exerciseTemplates: List<ExerciseTemplate>,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Split Title and Settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = split.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cycle ${split.currentIteration}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(
                    onClick = onSettingsClick
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Split Settings",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Workout Carousel
            WorkoutCarousel(
                workouts = split.workouts,
                        exerciseTemplates = exerciseTemplates,
                split = split,
                modifier = Modifier.fillMaxWidth()
                    )
        }
    }
} 