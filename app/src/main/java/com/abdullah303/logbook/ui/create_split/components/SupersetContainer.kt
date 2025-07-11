package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.ui.create_split.WorkoutExercise
import com.abdullah303.logbook.data.model.Side
import sh.calvin.reorderable.ReorderableColumn


// component that wraps exercises in a superset with a colored container
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SupersetContainer(
    supersetGroupId: String,
    exercises: List<WorkoutExercise>,
    onUpdateExercise: (String, Int?, Pair<Int, Int>?, Float?) -> Unit,
    onToggleUnilateral: (String) -> Unit,
    onUpdateSideOrder: (String, List<Side>) -> Unit,
    onDeleteExercise: (String) -> Unit,
    onCreateSuperset: (String) -> Unit,
    onRemoveFromSuperset: (String) -> Unit,
    onAddToSuperset: (String, String) -> Unit,
    onReorderWithinSuperset: (String, Int, Int) -> Unit = { _, _, _ -> },
    onDeleteSuperset: (String) -> Unit = { },
    availableSupersets: List<String> = emptyList(),
    dragHandleModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    draggedExerciseId: String? = null,
    onExerciseDraggedOut: (String) -> Unit = { },
    isFocused: Boolean = false,
    onFocusChanged: (String, Boolean) -> Unit = { _, _ -> }
) {
    // generate a consistent color for this superset group
    val supersetColor = getSupersetColor(supersetGroupId)
    
    // determine container state: compressed < focused < drop target
    val isCompressed = !isFocused && !isExpanded
    val isDropTarget = isExpanded && !isFocused  // being dragged over but not focused
    
    // smooth animated expansion/compression based on state
    val animatedScale by animateFloatAsState(
        targetValue = when {
            isFocused -> 1.02f       // focused for interaction (shows all exercises)
            isDropTarget -> 1.05f    // compact drop target (doesn't show exercises)
            else -> 0.96f            // compressed when not active
        },
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 400f
        ),
        label = "container_scale"
    )
    
    val animatedPadding by animateDpAsState(
        targetValue = when {
            isFocused -> 6.dp      // focused padding (shows all exercises)
            isDropTarget -> 4.dp   // compact drop target padding
            else -> 2.dp           // compressed padding
        },
        animationSpec = spring(
            dampingRatio = 0.9f,
            stiffness = 300f
        ),
        label = "container_padding"
    )
    
    val animatedRounding by animateDpAsState(
        targetValue = when {
            isFocused -> 16.dp     // focused rounding (shows all exercises)
            isDropTarget -> 12.dp  // compact drop target rounding
            else -> 8.dp           // compressed rounding
        },
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 400f
        ),
        label = "container_rounding"
    )
    
    val animatedContentPadding by animateDpAsState(
        targetValue = when {
            isFocused -> 12.dp     // focused content padding (shows all exercises)
            isDropTarget -> 12.dp  // compact drop target content padding
            else -> 8.dp           // compressed content padding
        },
        animationSpec = spring(
            dampingRatio = 0.9f,
            stiffness = 300f
        ),
        label = "content_padding"
    )
    
    // animate opacity for different states
    val contentOpacity by animateFloatAsState(
        targetValue = when {
            isFocused -> 1f        // full opacity when focused (shows all exercises)
            isDropTarget -> 1f     // full opacity for drop target
            else -> 0.7f           // reduced opacity when compressed
        },
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 300f
        ),
        label = "content_opacity"
    )
    
    val backgroundColor = when {
        isFocused -> supersetColor.copy(alpha = 0.15f)     // focused (shows all exercises)
        isDropTarget -> supersetColor.copy(alpha = 0.20f)  // compact drop target
        else -> supersetColor.copy(alpha = 0.06f)          // compressed
    }
    val borderColor = when {
        isFocused -> supersetColor.copy(alpha = 0.5f)      // focused (shows all exercises) 
        isDropTarget -> supersetColor.copy(alpha = 0.7f)   // compact drop target
        else -> supersetColor.copy(alpha = 0.2f)           // compressed
    }
    val borderWidth = when {
        isFocused -> 2.dp        // focused (shows all exercises)
        isDropTarget -> 2.5.dp   // compact drop target
        else -> 1.dp             // compressed
    }
    
    // haptic feedback for drag interactions
    val hapticFeedback = LocalHapticFeedback.current
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = animatedPadding)
            .scale(animatedScale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // toggle focus state when clicked
                onFocusChanged(supersetGroupId, !isFocused)
            },
        color = backgroundColor,
        shape = RoundedCornerShape(animatedRounding),
        border = androidx.compose.foundation.BorderStroke(
            width = borderWidth,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(animatedContentPadding)
                .alpha(contentOpacity)
        ) {
            // superset header with options
            SupersetHeader(
                exerciseCount = exercises.size,
                supersetColor = supersetColor,
                onOptionsClick = { onDeleteSuperset(supersetGroupId) },
                dragHandleModifier = dragHandleModifier
            )
            
            // show content based on container state
            when {
                isFocused -> {
                    // focused view - show all exercises with full functionality
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // reorderable exercises within the superset - following best practices
                    ReorderableColumn(
                        modifier = Modifier.fillMaxWidth(),
                        list = exercises,
                        onSettle = { fromIndex, toIndex ->
                            // update the list when drag settles
                            onReorderWithinSuperset(supersetGroupId, fromIndex, toIndex)
                        },
                        onMove = {
                            // provide haptic feedback during drag
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) { index, exercise, isDragging ->
                        key(exercise.id) {
                            // create interaction source for drag handle
                            val interactionSource = remember { MutableInteractionSource() }
                            
                            // animate elevation when dragging
                            val elevation by animateDpAsState(
                                targetValue = if (isDragging) 8.dp else 0.dp,
                                animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 400f
                                ),
                                label = "drag_elevation"
                            )
                            
                            // animate scale when dragging
                            val scale by animateFloatAsState(
                                targetValue = if (isDragging) 1.02f else 1f,
                                animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 400f
                                ),
                                label = "drag_scale"
                            )
                            
                            Surface(
                                shadowElevation = elevation,
                                modifier = Modifier
                                    .scale(scale)
                                    .padding(vertical = 1.dp)
                            ) {
                                WorkoutExerciseCard(
                                    workoutExercise = exercise,
                                    onUpdateExercise = onUpdateExercise,
                                    onToggleUnilateral = onToggleUnilateral,
                                    onUpdateSideOrder = onUpdateSideOrder,
                                    onDeleteExercise = onDeleteExercise,
                                    onCreateSuperset = onCreateSuperset,
                                    onRemoveFromSuperset = onRemoveFromSuperset,
                                    onAddToSuperset = onAddToSuperset,
                                    availableSupersets = availableSupersets.filter { it != supersetGroupId },
                                    dragHandleModifier = Modifier
                                        .draggableHandle(
                                            interactionSource = interactionSource,
                                            onDragStarted = {
                                                // haptic feedback when drag starts
                                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                            },
                                            onDragStopped = {
                                                // haptic feedback when drag stops
                                                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            }
                                        )
                                        .clearAndSetSemantics { },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                
                isDropTarget -> {
                    // compact drop target view - exercise being dragged over but container not focused
                    Column {
                        CompressedSupersetIndicator(exerciseCount = exercises.size)
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        CompactDropZone(supersetColor = supersetColor)
                        
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                
                else -> {
                    // compressed view - default state
                    CompressedSupersetIndicator(exerciseCount = exercises.size)
                }
            }

        }
    }
}

 