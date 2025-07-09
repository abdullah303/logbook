package com.abdullah303.logbook.ui.create_split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import com.abdullah303.logbook.ui.components.BubbleContextMenu
import com.abdullah303.logbook.ui.create_split.components.ExerciseListBottomSheet
import com.abdullah303.logbook.ui.create_split.components.DayButtonGroup
import com.abdullah303.logbook.ui.create_split.components.DayContainer
import com.abdullah303.logbook.ui.components.InlineEditableText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSplitScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToCreateExercise: () -> Unit = {},
    viewModel: CreateSplitViewModel = hiltViewModel()
) {
    val navigationState = CreateSplitScreenNavigation(
        onNavigateBack = onNavigateBack,
        onNavigateToCreateExercise = onNavigateToCreateExercise
    )

    val splitTitle by viewModel.splitTitle.collectAsState()
    val days by viewModel.days.collectAsState()
    val selectedDayIndex by viewModel.selectedDayIndex.collectAsState()
    
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

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InlineEditableText(
                text = splitTitle,
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
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
                    dayName = days[selectedDayIndex]
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