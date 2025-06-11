package com.abdullah303.logbook.features.exercise_list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.features.exercise_list.presentation.ExerciseListViewModel
import com.abdullah303.logbook.navigation.Screen
import com.abdullah303.logbook.core.ui.components.ContainedLoadingIndicator
import com.abdullah303.logbook.core.ui.components.FilterOption
import com.abdullah303.logbook.core.ui.components.FilterableListScreen
import com.abdullah303.logbook.core.utils.Muscle
import com.abdullah303.logbook.features.exercise_list.ui.components.ExerciseItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val filterOptions = remember {
        listOf(FilterOption("All")) + Muscle.values().map { FilterOption(it.displayName) }
    }
    var selectedIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredExercises = remember(exercises, selectedIndex, searchQuery) {
        exercises.filter { exercise ->
            val matchesSearch = exercise.name.contains(searchQuery, ignoreCase = true)
            val matchesMuscle = selectedIndex == 0 || // "All" category
                    Muscle.fromString(exercise.primaryMuscle)?.displayName == filterOptions[selectedIndex].label
            matchesSearch && matchesMuscle
        }
    }

    if (isLoading) {
        ContainedLoadingIndicator()
        return
    }

    FilterableListScreen(
        title = "Exercises",
        filterOptions = filterOptions,
        items = filteredExercises,
        selectedFilterIndex = selectedIndex,
        onFilterSelected = { selectedIndex = it },
        itemContent = { exercise ->
            ExerciseItem(
                exercise = exercise,
                onClick = {
                    // TODO: Navigate to exercise details or edit
                }
            )
        },
        fabContent = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreateExercise.route) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create Exercise"
                )
            }
        },
        modifier = modifier,
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it }
    )
} 