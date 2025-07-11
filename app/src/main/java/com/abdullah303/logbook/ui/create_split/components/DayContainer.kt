package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.abdullah303.logbook.ui.create_split.CreateSplitWorkoutExercise
import com.abdullah303.logbook.data.model.Side
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import kotlinx.coroutines.delay

// helper function to find the original exercise index for mixed list items
private fun findOriginalExerciseIndex(mixedItem: Any, workoutExercises: List<CreateSplitWorkoutExercise>): Int {
    return when (mixedItem) {
        is CreateSplitWorkoutExercise -> {
            // find the index of this individual exercise
            workoutExercises.indexOfFirst { it.id == mixedItem.id }
        }
        is Pair<*, *> -> {
            // this is a superset group, find the index of the first exercise in this group
            val (supersetKey, exercises) = mixedItem as Pair<String, List<CreateSplitWorkoutExercise>>
            val supersetGroupId = supersetKey.removePrefix("superset_")
            workoutExercises.indexOfFirst { it.supersetGroupId == supersetGroupId }
        }
        else -> -1
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayContainer(
    dayName: String,
    workoutExercises: List<CreateSplitWorkoutExercise>,
    onUpdateExercise: (String, Int?, Pair<Int, Int>?, Float?) -> Unit,
    onReorderExercises: (Int, Int) -> Unit,
    onToggleUnilateral: (String) -> Unit,
    onUpdateSideOrder: (String, List<Side>) -> Unit,
    onDeleteExercise: (String) -> Unit,
    onCreateSuperset: (String) -> Unit,
    onRemoveFromSuperset: (String) -> Unit,
    onAddToSuperset: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    // smooth animated surface elevation
    val animatedElevation by animateDpAsState(
        targetValue = 2.dp,
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 300f
        ),
        label = "surface_elevation"
    )
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = animatedElevation,
        shape = MaterialTheme.shapes.medium
    ) {
        if (workoutExercises.isEmpty()) {
            // show placeholder when no exercises
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dayName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Tap + to add exercises",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        } else {
            // show exercises list with enhanced position-based drag detection
            val lazyListState = rememberLazyListState()
            // create a mixed list of individual exercises and superset groups
            val groupedExercises = workoutExercises.groupBy { it.supersetGroupId }
            val supersetGroups = groupedExercises.filterKeys { it != null }
            val availableSupersets = supersetGroups.keys.filterNotNull()
            
            // create a mixed list maintaining original order
            val mixedItems = mutableListOf<Any>()
            val processedSupersets = mutableSetOf<String>()
            
            workoutExercises.forEach { exercise ->
                if (exercise.supersetGroupId != null && !processedSupersets.contains(exercise.supersetGroupId)) {
                    // add the entire superset group
                    val supersetExercises = supersetGroups[exercise.supersetGroupId] ?: emptyList()
                    mixedItems.add("superset_${exercise.supersetGroupId}" to supersetExercises)
                    processedSupersets.add(exercise.supersetGroupId)
                } else if (exercise.supersetGroupId == null) {
                    // add individual exercise
                    mixedItems.add(exercise)
                }
            }
            
            val reorderableLazyListState = rememberReorderableLazyListState(
                lazyListState = lazyListState
            ) { from, to ->
                // handle reordering for the mixed list - need to map back to original exercise indices
                val fromIndex = from.index - 1  // account for header
                val toIndex = to.index - 1      // account for header
                
                if (fromIndex >= 0 && toIndex >= 0 && fromIndex < mixedItems.size && toIndex < mixedItems.size) {
                    // find the original exercise indices that correspond to these mixed list items
                    val fromExerciseIndex = findOriginalExerciseIndex(mixedItems[fromIndex], workoutExercises)
                    val toExerciseIndex = findOriginalExerciseIndex(mixedItems[toIndex], workoutExercises)
                    
                    if (fromExerciseIndex != -1 && toExerciseIndex != -1) {
                        onReorderExercises(fromExerciseIndex, toExerciseIndex)
                    }
                }
            }
            
            // enhanced drag state with precise position tracking
            var draggedExerciseId by remember { mutableStateOf<String?>(null) }
            var draggedFromSupersetId by remember { mutableStateOf<String?>(null) }
            var draggedCardPosition by remember { mutableStateOf(Offset.Zero) }
            var draggedCardSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
            var supersetContainerBounds by remember { mutableStateOf<Map<String, Rect>>(emptyMap()) }
            var hoveredSupersetId by remember { mutableStateOf<String?>(null) }
            var isDraggedOutside by remember { mutableStateOf(false) }
            

            
            // calculate which superset the dragged card is over and if it's outside all containers
            LaunchedEffect(draggedCardPosition, supersetContainerBounds, draggedExerciseId) {
                if (draggedExerciseId != null && draggedCardPosition != Offset.Zero) {
                    val draggedCardCenter = Offset(
                        draggedCardPosition.x + draggedCardSize.width / 2,
                        draggedCardPosition.y + draggedCardSize.height / 2
                    )
                    
                    // find which superset container contains the dragged card center
                    val newHoveredSupersetId = supersetContainerBounds.entries.find { (supersetId, bounds) ->
                        // don't consider the superset the exercise came from
                        supersetId != draggedFromSupersetId && bounds.contains(draggedCardCenter)
                    }?.key
                    
                    hoveredSupersetId = newHoveredSupersetId
                    
                    // check if dragged outside all superset containers (for drag-out detection)
                    val isInsideAnyContainer = supersetContainerBounds.values.any { bounds ->
                        bounds.contains(draggedCardCenter)
                    }
                    isDraggedOutside = !isInsideAnyContainer && draggedFromSupersetId != null
                }
            }
            
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                // day name header
                item {
                    Text(
                        text = dayName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
                

                
                // display mixed items with enhanced position-based drag detection
                itemsIndexed(
                    items = mixedItems,
                    key = { _, item -> 
                        when (item) {
                            is CreateSplitWorkoutExercise -> item.id
                            is Pair<*, *> -> item.first as String
                            else -> "unknown_${item.hashCode()}"
                        }
                    }
                ) { _, item ->
                    when (item) {
                        is CreateSplitWorkoutExercise -> {
                            // individual exercise
                            ReorderableItem(
                                state = reorderableLazyListState,
                                key = item.id
                            ) { isDragging ->
                                val interactionSource = remember { MutableInteractionSource() }
                                
                                WorkoutExerciseCard(
                                    workoutExercise = item,
                                    onUpdateExercise = onUpdateExercise,
                                    onToggleUnilateral = onToggleUnilateral,
                                    onUpdateSideOrder = onUpdateSideOrder,
                                    onDeleteExercise = onDeleteExercise,
                                    onCreateSuperset = onCreateSuperset,
                                    onRemoveFromSuperset = onRemoveFromSuperset,
                                    onAddToSuperset = { exerciseId, supersetId ->
                                        onAddToSuperset(exerciseId, supersetId)
                                    },
                                    availableSupersets = availableSupersets,
                                    dragHandleModifier = Modifier
                                        .draggableHandle(
                                            interactionSource = interactionSource,
                                            onDragStarted = {
                                                draggedExerciseId = item.id
                                                draggedFromSupersetId = item.supersetGroupId
                                            },
                                            onDragStopped = {
                                                // handle different drop scenarios
                                                when {
                                                    // drop into a different superset
                                                    hoveredSupersetId != null && draggedExerciseId != null -> {
                                                        onAddToSuperset(draggedExerciseId!!, hoveredSupersetId!!)
                                                    }
                                                    // drag out of current superset
                                                    isDraggedOutside && draggedFromSupersetId != null && draggedExerciseId != null -> {
                                                        onRemoveFromSuperset(draggedExerciseId!!)
                                                    }
                                                }
                                                // reset drag state
                                                draggedExerciseId = null
                                                draggedFromSupersetId = null
                                                draggedCardPosition = Offset.Zero
                                                draggedCardSize = androidx.compose.ui.geometry.Size.Zero
                                                hoveredSupersetId = null
                                                isDraggedOutside = false
                                            }
                                        )
                                        .clearAndSetSemantics { },
                                    modifier = if (isDragging && item.id == draggedExerciseId) {
                                        Modifier.onGloballyPositioned { coordinates ->
                                            draggedCardPosition = coordinates.positionInRoot()
                                            draggedCardSize = coordinates.size.toSize()
                                        }
                                    } else {
                                        Modifier
                                    }
                                )
                            }
                        }
                        is Pair<*, *> -> {
                            // superset group
                            val (supersetKey, exercises) = item as Pair<String, List<CreateSplitWorkoutExercise>>
                            val supersetGroupId = supersetKey.removePrefix("superset_")
                            
                            // this superset is expanded if the dragged card is directly over it
                            val isExpanded = hoveredSupersetId == supersetGroupId && 
                                           draggedExerciseId != null &&
                                           !exercises.any { it.id == draggedExerciseId } // not dragging from this superset
                            
                            ReorderableItem(
                                state = reorderableLazyListState,
                                key = supersetKey
                            ) { isDragging ->
                                val interactionSource = remember { MutableInteractionSource() }
                                
                                SupersetContainer(
                                    supersetGroupId = supersetGroupId,
                                    exercises = exercises,
                                    onUpdateExercise = onUpdateExercise,
                                    onToggleUnilateral = onToggleUnilateral,
                                    onUpdateSideOrder = onUpdateSideOrder,
                                    onDeleteExercise = onDeleteExercise,
                                    onCreateSuperset = onCreateSuperset,
                                    onRemoveFromSuperset = onRemoveFromSuperset,
                                    onAddToSuperset = { exerciseId, targetSupersetId ->
                                        onAddToSuperset(exerciseId, targetSupersetId)
                                    },
                                    availableSupersets = availableSupersets,
                                    isDragTarget = isExpanded, // use isExpanded for isDragTarget
                                    dragHandleModifier = Modifier
                                        .draggableHandle(
                                            interactionSource = interactionSource,
                                            onDragStarted = {
                                                // when dragging the entire superset, clear individual exercise drag state
                                                draggedExerciseId = null
                                                draggedFromSupersetId = null
                                            }
                                        )
                                        .clearAndSetSemantics { },
                                    modifier = Modifier
                                        .onGloballyPositioned { coordinates ->
                                            // track this superset's bounds for precise collision detection
                                            val position = coordinates.positionInRoot()
                                            val size = coordinates.size.toSize()
                                            val bounds = Rect(
                                                offset = position,
                                                size = size
                                            )
                                            supersetContainerBounds = supersetContainerBounds + (supersetGroupId to bounds)
                                        }
                                        .padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 