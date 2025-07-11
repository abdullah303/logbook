package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.data.model.Muscles
import com.abdullah303.logbook.ui.components.GenericBottomSheet
import com.abdullah303.logbook.ui.create_split.ExerciseWithEquipment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListBottomSheet(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedMuscles: Set<Muscles>,
    onMuscleSelectionChange: (Set<Muscles>) -> Unit,
    exercises: List<ExerciseWithEquipment>,
    onExerciseClick: (ExerciseWithEquipment) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onNavigateToCreateExercise: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {

    GenericBottomSheet(
        title = "Exercise List",
        modifier = modifier,
        onDismiss = onDismiss,
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    ) {
        // search bar with add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Search exercises...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                IconButton(
                    onClick = onNavigateToCreateExercise,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Exercise",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // muscle filter chips
        MuscleChipGroup(
            selectedMuscles = selectedMuscles,
            onMuscleSelectionChanged = onMuscleSelectionChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // exercises list
        if (exercises.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isBlank() && selectedMuscles.isEmpty()) {
                        "No exercises available yet"
                    } else {
                        "No exercises match your filters"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn {
                items(exercises) { exerciseWithEquipment ->
                    ExerciseDisplayCard(
                        exerciseWithEquipment = exerciseWithEquipment,
                        onExerciseClick = onExerciseClick
                    )
                }
            }
        }
    }
} 