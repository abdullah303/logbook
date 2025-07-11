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
import com.abdullah303.logbook.data.model.Side
import com.abdullah303.logbook.ui.create_split.CreateSplitWorkoutExercise
import kotlinx.coroutines.launch
import com.abdullah303.logbook.ui.create_split.components.SideOrderRow
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WorkoutExerciseCard(
    workoutExercise: CreateSplitWorkoutExercise,
    onUpdateExercise: (String, Int?, Pair<Int, Int>?, Float?) -> Unit,
    onToggleUnilateral: (String) -> Unit,
    onUpdateSideOrder: (String, List<Side>) -> Unit,
    onDeleteExercise: (String) -> Unit,
    onCreateSuperset: (String) -> Unit,
    onRemoveFromSuperset: (String) -> Unit,
    onAddToSuperset: (String, String) -> Unit,
    availableSupersets: List<String> = emptyList(),
    onDragStart: (String) -> Unit = {},
    onDragEnd: () -> Unit = {},
    modifier: Modifier = Modifier,
    dragHandleModifier: Modifier = Modifier
) {
    var showSetsSelection by remember { mutableStateOf(false) }
    var showRepsSelection by remember { mutableStateOf(false) }
    var showRirSelection by remember { mutableStateOf(false) }
    var showDropdownMenu by remember { mutableStateOf(false) }
    
    val setsBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val repsBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val rirBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
                .combinedClickable(
                    onClick = { /* normal click does nothing */ },
                    onLongClick = { showDropdownMenu = true }
                ),
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

                // show side order row when unilateral - moved here under muscles
                if (workoutExercise.isUnilateral) {
                    Spacer(modifier = Modifier.height(4.dp))
                    SideOrderRow(
                        sides = workoutExercise.sides,
                        onReorder = { newOrder ->
                            onUpdateSideOrder(workoutExercise.id, newOrder)
                        }
                    )
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
                    
                    // reps selection - compact version with range display
                    CompactFormSelectionCard(
                        title = "Reps",
                        value = "${workoutExercise.repMin}-${workoutExercise.repMax}",
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
        
        // improved dropdown menu
        DropdownMenu(
            expanded = showDropdownMenu,
            onDismissRequest = { showDropdownMenu = false },
            modifier = Modifier.padding(4.dp)
        ) {
            // unilateral/bilateral option
            DropdownMenuItem(
                text = { 
                    Text(
                        text = if (workoutExercise.isUnilateral) "switch to bilateral" else "switch to unilateral",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.SwapHoriz,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    onToggleUnilateral(workoutExercise.id)
                    showDropdownMenu = false
                }
            )
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            
            // dropsets placeholder
            DropdownMenuItem(
                text = { 
                    Text(
                        text = "dropsets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.FitnessCenter,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    // todo: implement dropsets
                    showDropdownMenu = false
                },
                enabled = false
            )
            
            // supersets placeholder
            DropdownMenuItem(
                text = { 
                    Text(
                        text = if (workoutExercise.supersetGroupId != null) "remove from superset" else "create superset",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.FitnessCenter,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    if (workoutExercise.supersetGroupId != null) {
                        onRemoveFromSuperset(workoutExercise.id)
                    } else {
                        onCreateSuperset(workoutExercise.id)
                    }
                    showDropdownMenu = false
                }
            )
            
            // show option to add to existing superset if there are any and this exercise is not in one
            if (availableSupersets.isNotEmpty() && workoutExercise.supersetGroupId == null) {
                availableSupersets.forEach { supersetId ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = "add to superset",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.FitnessCenter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = {
                            onAddToSuperset(workoutExercise.id, supersetId)
                            showDropdownMenu = false
                        }
                    )
                }
            }
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            
            // delete option
            DropdownMenuItem(
                text = { 
                    Text(
                        text = "delete exercise",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    onDeleteExercise(workoutExercise.id)
                    showDropdownMenu = false
                }
            )
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
    
    // reps selection bottom sheet - now using RangeSelection
    if (showRepsSelection) {
        ValueSelectionBottomSheet(
            title = "Rep Range",
            selectionType = ValueSelectionType.RangeSelection(
                values = (1..50).toList(),
                initialRange = Pair(workoutExercise.repMin, workoutExercise.repMax),
                rangeFormatter = { min, max -> "$min-$max" }
            ),
            sheetState = repsBottomSheetState,
            onDismiss = {
                showRepsSelection = false
            },
            onConfirm = { rangeString ->
                // parse the range string (e.g., "8-12") back to a pair
                val parts = rangeString.split("-")
                if (parts.size == 2) {
                    val min = parts[0].toIntOrNull() ?: workoutExercise.repMin
                    val max = parts[1].toIntOrNull() ?: workoutExercise.repMax
                    onUpdateExercise(workoutExercise.id, null, Pair(min, max), null)
                }
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