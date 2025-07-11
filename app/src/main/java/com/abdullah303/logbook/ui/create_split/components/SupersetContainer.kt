package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.abdullah303.logbook.ui.create_split.CreateSplitWorkoutExercise
import com.abdullah303.logbook.data.model.Side
import sh.calvin.reorderable.ReorderableItem

// component that wraps exercises in a superset with a colored container
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SupersetContainer(
    supersetGroupId: String,
    exercises: List<CreateSplitWorkoutExercise>,
    onUpdateExercise: (String, Int?, Pair<Int, Int>?, Float?) -> Unit,
    onToggleUnilateral: (String) -> Unit,
    onUpdateSideOrder: (String, List<Side>) -> Unit,
    onDeleteExercise: (String) -> Unit,
    onCreateSuperset: (String) -> Unit,
    onRemoveFromSuperset: (String) -> Unit,
    onAddToSuperset: (String, String) -> Unit,
    availableSupersets: List<String> = emptyList(),
    dragHandleModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    isDragTarget: Boolean = false,
    onDragEnter: (String) -> Unit = {},
    onDragExit: (String) -> Unit = {}
) {
    // generate a consistent color for this superset group
    val supersetColor = getSupersetColor(supersetGroupId)
    var containerPosition by remember { mutableStateOf(Offset.Zero) }
    var containerSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .onGloballyPositioned { coordinates ->
                containerPosition = coordinates.positionInRoot()
                containerSize = coordinates.size.toSize()
            }
            .pointerInput(dragHandleModifier, supersetGroupId) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        val pos = change.position
                        if (pos.x in 0f..containerSize.width && pos.y in 0f..containerSize.height) {
                            onDragEnter(supersetGroupId)
                        } else {
                            onDragExit(supersetGroupId)
                        }
                    },
                    onDragEnd = {
                        onDragExit(supersetGroupId)
                    }
                )
            }
            .then(
                if (isDragTarget) {
                    Modifier.clickable {
                        // click handler while dragging is optional
                    }
                } else Modifier
            ),
        color = if (isDragTarget) {
            supersetColor.copy(alpha = 0.25f)
        } else {
            supersetColor.copy(alpha = 0.1f)
        },
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isDragTarget) 3.dp else 2.dp,
            color = if (isDragTarget) {
                supersetColor.copy(alpha = 0.8f)
            } else {
                supersetColor.copy(alpha = 0.4f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // superset header with drag handle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FitnessCenter,
                        contentDescription = null,
                        tint = supersetColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "superset",
                        style = MaterialTheme.typography.labelMedium,
                        color = supersetColor,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "(${exercises.size} exercises)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // drag handle for the entire superset
                IconButton(
                    onClick = { /* drag handle doesn't need click action */ },
                    modifier = dragHandleModifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DragHandle,
                        contentDescription = "Reorder superset",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // exercises in the superset
            exercises.forEach { exercise ->
                WorkoutExerciseCard(
                    workoutExercise = exercise,
                    onUpdateExercise = onUpdateExercise,
                    onToggleUnilateral = onToggleUnilateral,
                    onUpdateSideOrder = onUpdateSideOrder,
                    onDeleteExercise = onDeleteExercise,
                    onCreateSuperset = onCreateSuperset,
                    onRemoveFromSuperset = onRemoveFromSuperset,
                    onAddToSuperset = onAddToSuperset,
                    availableSupersets = availableSupersets.filter { it != supersetGroupId }, // exclude current superset
                    dragHandleModifier = Modifier, // no drag handle for exercises in superset
                    modifier = Modifier.padding(vertical = 1.dp)
                )
            }
            
            // drop zone hint when being dragged over
            if (isDragTarget) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    color = supersetColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 2.dp,
                        color = supersetColor.copy(alpha = 0.6f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "drop here to add to superset",
                            style = MaterialTheme.typography.labelSmall,
                            color = supersetColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

 