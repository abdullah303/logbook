package com.abdullah303.logbook.ui.create_split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.launch

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.navigation.NavController
import com.abdullah303.logbook.ui.components.BubbleContextMenu
import com.abdullah303.logbook.ui.create_split.components.ExerciseListBottomSheet
import com.abdullah303.logbook.ui.create_split.components.DayButtonGroup
import com.abdullah303.logbook.ui.create_split.components.DayContainer
import com.abdullah303.logbook.ui.components.InlineEditableText
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.ui.create_split.CreateSplitWorkoutExercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSplitScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToCreateExercise: () -> Unit = {},
    onSaveComplete: () -> Unit = {},
    viewModel: CreateSplitViewModel = hiltViewModel(),
    navController: NavController? = null
) {
    val navigationState = CreateSplitScreenNavigation(
        onNavigateBack = onNavigateBack,
        onNavigateToCreateExercise = onNavigateToCreateExercise,
        onSaveComplete = onSaveComplete
    )

    val splitTitle by viewModel.splitTitle.collectAsState()
    val days by viewModel.days.collectAsState()
    val selectedDayIndex by viewModel.selectedDayIndex.collectAsState()
    val dayExercises by viewModel.dayExercises.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    
    // exercise-related state
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedMuscles by viewModel.selectedMuscles.collectAsState()
    val filteredExercises by viewModel.filteredExercises.collectAsState()

    var showMenu by remember { mutableStateOf(false) }
    var menuAnchorOffset by remember { mutableStateOf(Offset.Zero) }
    var contextMenuDayIndex by remember { mutableStateOf<Int?>(null) }
    var renamingDayIndex by remember { mutableStateOf<Int?>(null) }
    var showExerciseList by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val dayButtonPositions = remember { mutableStateMapOf<Int, Offset>() }
    val snackbarHostState = remember { SnackbarHostState() }

    // observe navigation arguments for newly created exercise
    LaunchedEffect(navController) {
        navController?.let { nav ->
            // check for newly created exercise ID in saved state handle
            val exerciseId = nav.currentBackStackEntry?.savedStateHandle?.get<String>("newlyCreatedExerciseId")
            exerciseId?.let { id ->
                // add the exercise to the current day
                viewModel.addExerciseById(id)
                // clear the saved state
                nav.currentBackStackEntry?.savedStateHandle?.remove<String>("newlyCreatedExerciseId")
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // title and save button row
            Row(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InlineEditableText(
                    text = splitTitle,
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.9f),
                    onTextChange = { viewModel.updateSplitTitle(it) },
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true,
                    onDone = {
                        // keyboard will be dismissed automatically
                    }
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // save button
                Button(
                    onClick = {
                        viewModel.saveSplit { success ->
                            scope.launch {
                                if (success) {
                                    snackbarHostState.showSnackbar("Split saved successfully!")
                                    navigationState.onSaveComplete()
                                } else {
                                    snackbarHostState.showSnackbar("Failed to save split")
                                }
                            }
                        }
                    },
                    enabled = !isSaving && splitTitle.isNotBlank(),
                    modifier = Modifier
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save Split",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Save")
                        }
                    }
                }
            }

            DayButtonGroup(
                days = days,
                selectedDayIndex = selectedDayIndex,
                onDaySelected = { viewModel.selectDay(it) },
                onAddDay = { viewModel.addDay() },
                onLongPressDay = { index, _ ->
                    contextMenuDayIndex = index
                    menuAnchorOffset = dayButtonPositions[index] ?: Offset.Zero
                    showMenu = true
                },
                onPositionUpdate = { index, offset ->
                    dayButtonPositions[index] = offset
                },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                renamingIndex = renamingDayIndex,
                onRenameDay = { index, newName ->
                    viewModel.renameDay(index, newName)
                },
                onRenameDayFinished = {
                    renamingDayIndex = null
                }
            )

            // day container that takes the rest of the screen
            if (days.isNotEmpty() && selectedDayIndex < days.size) {
                DayContainer(
                    dayName = days[selectedDayIndex],
                    workoutExercises = dayExercises[selectedDayIndex] ?: emptyList(),
                    onUpdateExercise = viewModel::updateWorkoutExercise,
                    onReorderExercises = { fromIndex, toIndex ->
                        viewModel.reorderWorkoutExercises(selectedDayIndex, fromIndex, toIndex)
                    },
                    onToggleUnilateral = viewModel::toggleUnilateral,
                    onUpdateSideOrder = viewModel::updateSideOrder,
                    onDeleteExercise = viewModel::removeWorkoutExercise,
                    onCreateSuperset = viewModel::createSuperset,
                    onRemoveFromSuperset = viewModel::removeFromSuperset,
                    onAddToSuperset = viewModel::addToSuperset
                )
            }
        }

        BubbleContextMenu(
            showMenu = showMenu,
            onDismiss = { showMenu = false },
            anchorOffset = menuAnchorOffset,
            buttonWidth = 120.dp, // approx width from DayButtonGroup
            onRename = {
                renamingDayIndex = contextMenuDayIndex
            },
            onDelete = {
                contextMenuDayIndex?.let { viewModel.deleteDay(it) }
            },
            canDelete = days.size > 1
        )

        // floating action button to open exercise list
        FloatingActionButton(
            onClick = {
                showExerciseList = true
                // do not call bottomSheetState.show() here
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Exercise",
                modifier = Modifier.size(24.dp)
            )
        }

        // snackbar host for showing save messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // animate bottom sheet open after it is composed
        if (showExerciseList) {
            LaunchedEffect(showExerciseList) {
                if (showExerciseList) {
                    bottomSheetState.show()
                }
            }
            ExerciseListBottomSheet(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::updateSearchQuery,
                selectedMuscles = selectedMuscles,
                onMuscleSelectionChange = viewModel::updateSelectedMuscles,
                exercises = filteredExercises,
                onExerciseClick = { exerciseWithEquipment ->
                    viewModel.selectExercise(exerciseWithEquipment)
                    showExerciseList = false
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                onDismiss = {
                    showExerciseList = false
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                onNavigateToCreateExercise = {
                    showExerciseList = false
                    scope.launch {
                        bottomSheetState.hide()
                    }
                    navigationState.onNavigateToCreateExercise()
                },
                sheetState = bottomSheetState,
                maxHeightFraction = 0.75f
            )
        }
    }
} 