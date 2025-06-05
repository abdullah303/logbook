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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val muscles = listOf(
        "Abs", "Biceps", "Triceps", "Chest", "Back", "Shoulders", "Quadriceps", "Hamstring", "Calves", "Gluteus", "Forearms", "Latissimus", "Core", "Neck", "Adductors", "Abductors"
    )
    val filterOptions = muscles.map { FilterOption(it) }
    var selectedIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredExercises = remember(exercises, selectedIndex, searchQuery) {
        exercises.filter {
            it.primaryMuscle.equals(muscles[selectedIndex], ignoreCase = true) &&
            it.name.contains(searchQuery, ignoreCase = true)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseItem(
    exercise: Exercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
} 