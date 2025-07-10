package com.abdullah303.logbook.ui.create_split.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.ui.create_split.WorkoutExercise
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayContainer(
    dayName: String,
    workoutExercises: List<WorkoutExercise>,
    onUpdateExercise: (String, Int?, Pair<Int, Int>?, Float?) -> Unit,
    onReorderExercises: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
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
                        text = "tap + to add exercises",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        } else {
            // show exercises list with reorderable functionality
            val lazyListState = rememberLazyListState()
            val reorderableLazyListState = rememberReorderableLazyListState(
                lazyListState = lazyListState
            ) { from, to ->
                // find the actual indices in the workoutExercises list
                val fromIndex = workoutExercises.indexOfFirst { it.id == from.key }
                val toIndex = workoutExercises.indexOfFirst { it.id == to.key }
                
                if (fromIndex != -1 && toIndex != -1) {
                    onReorderExercises(fromIndex, toIndex)
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
                            .padding(bottom = 12.dp)
                    )
                }
                
                // reorderable exercise cards
                itemsIndexed(
                    items = workoutExercises,
                    key = { _, workoutExercise -> workoutExercise.id }
                ) { _, workoutExercise ->
                    ReorderableItem(
                        state = reorderableLazyListState,
                        key = workoutExercise.id
                    ) { isDragging ->
                        val interactionSource = remember { MutableInteractionSource() }
                        
                        WorkoutExerciseCard(
                            workoutExercise = workoutExercise,
                            onUpdateExercise = onUpdateExercise,
                            dragHandleModifier = Modifier
                                .draggableHandle(
                                    interactionSource = interactionSource
                                )
                                .clearAndSetSemantics { }
                        )
                    }
                }
            }
        }
    }
} 