package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.ui.components.ValueSelectionBottomSheet
import com.abdullah303.logbook.ui.components.ValueSelectionType
import com.abdullah303.logbook.ui.create_split.WorkoutExercise
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutExerciseCard(
    workoutExercise: WorkoutExercise,
    onUpdateExercise: (String, Int?, Int?, Float?) -> Unit,
    modifier: Modifier = Modifier,
    dragHandleModifier: Modifier = Modifier
) {
    var showSetsSelection by remember { mutableStateOf(false) }
    var showRepsSelection by remember { mutableStateOf(false) }
    var showRirSelection by remember { mutableStateOf(false) }
    
    val setsBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val repsBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val rirBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // exercise name, equipment, and drag handle in one line
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = workoutExercise.exercise.name,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = workoutExercise.equipmentName,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // drag handle
                IconButton(
                    onClick = { /* drag handle doesn't need click action */ },
                    modifier = dragHandleModifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DragHandle,
                        contentDescription = "Reorder",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            // hashtag styled muscles
            val exercise = workoutExercise.exercise
            if (exercise.primaryMuscles.isNotEmpty() || exercise.auxiliaryMuscles.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // primary muscles
                    if (exercise.primaryMuscles.isNotEmpty()) {
                        Text(
                            text = exercise.primaryMuscles.joinToString(" ") { muscle ->
                                "#${muscle.name.lowercase().replace("_", "")}"
                            },
                            style = MaterialTheme.typography.labelSmall,
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
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // compact form selections for sets, reps, and rir
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // sets selection - compact version
                CompactFormSelectionCard(
                    title = "Sets",
                    value = "${workoutExercise.sets}",
                    onSelectionClick = { showSetsSelection = true },
                    modifier = Modifier.weight(1f)
                )
                
                // reps selection - compact version
                CompactFormSelectionCard(
                    title = "Reps",
                    value = "${workoutExercise.reps}",
                    onSelectionClick = { showRepsSelection = true },
                    modifier = Modifier.weight(1f)
                )
                
                // rir selection - compact version
                CompactFormSelectionCard(
                    title = "RIR",
                    value = if (workoutExercise.rir % 1.0f == 0.0f) {
                        "${workoutExercise.rir.toInt()}"
                    } else {
                        "${workoutExercise.rir}"
                    },
                    onSelectionClick = { showRirSelection = true },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    
    // sets selection bottom sheet
    if (showSetsSelection) {
        ValueSelectionBottomSheet(
            title = "Number of Sets",
            selectionType = ValueSelectionType.SingleInteger(
                values = (1..10).toList(),
                initialValue = workoutExercise.sets
            ),
            sheetState = setsBottomSheetState,
            onDismiss = {
                showSetsSelection = false
            },
            onConfirm = { value ->
                onUpdateExercise(workoutExercise.id, value, null, null)
                showSetsSelection = false
            }
        )
    }
    
    // reps selection bottom sheet
    if (showRepsSelection) {
        ValueSelectionBottomSheet(
            title = "Number of Reps",
            selectionType = ValueSelectionType.SingleInteger(
                values = (1..50).toList(),
                initialValue = workoutExercise.reps
            ),
            sheetState = repsBottomSheetState,
            onDismiss = {
                showRepsSelection = false
            },
            onConfirm = { value ->
                onUpdateExercise(workoutExercise.id, null, value, null)
                showRepsSelection = false
            }
        )
    }
    
    // rir selection bottom sheet  
    if (showRirSelection) {
        val rirValues = buildList {
            for (i in 0..10) {
                add(i.toFloat())
                if (i < 10) {
                    add(i + 0.5f)
                }
            }
        }
        
        ValueSelectionBottomSheet(
            title = "RIR (Reps in Reserve)",
            selectionType = ValueSelectionType.SingleFloat(
                values = rirValues,
                initialValue = workoutExercise.rir,
                formatter = { value ->
                    if (value % 1f == 0f) value.toInt().toString()
                    else String.format("%.1f", value)
                }
            ),
            sheetState = rirBottomSheetState,
            onDismiss = {
                showRirSelection = false
            },
            onConfirm = { value ->
                onUpdateExercise(workoutExercise.id, null, null, value)
                showRirSelection = false
            }
        )
    }
}

@Composable
private fun CompactFormSelectionCard(
    title: String,
    value: String,
    onSelectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onSelectionClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
} 