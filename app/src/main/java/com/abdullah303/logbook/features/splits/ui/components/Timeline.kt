package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.splits.data.ExerciseInstance
import com.abdullah303.logbook.features.splits.data.ExerciseTemplate

@Composable
fun Timeline(
    exercises: List<ExerciseInstance>,
    exerciseTemplates: List<ExerciseTemplate>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Timeline container
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(24.dp)
                .align(Alignment.CenterStart)
        ) {
            // Vertical line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 11.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            )
        }
        
        // Exercise items with dots
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(exercises.size) { index ->
                val exercise = exercises[index]
                val exerciseTemplate = exerciseTemplates.find { it.id == exercise.exerciseTemplateId }
                exerciseTemplate?.let {
                    Box(
                        modifier = Modifier.height(48.dp)
                    ) {
                        // Dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.CenterStart)
                                .offset(x = (-24).dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(1.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        
                        // Exercise item
                        ExerciseItem(
                            exercise = exercise,
                            exerciseTemplate = it,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
} 