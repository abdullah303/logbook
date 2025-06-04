package com.abdullah303.logbook.features.exercise_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAllExercises().collect { exerciseList ->
                    _exercises.value = exerciseList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                // TODO: Handle error state
            }
        }
    }

    fun searchExercises(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (query.isBlank()) {
                    repository.getAllExercises().collect { exerciseList ->
                        _exercises.value = exerciseList
                    }
                } else {
                    repository.searchExercises(query).collect { exerciseList ->
                        _exercises.value = exerciseList
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshExercises() {
        loadExercises()
    }

    fun addTestExercise() {
        viewModelScope.launch {
            val testExercise = Exercise(
                name = "Test Exercise",
                equipment = "Barbell",
                primaryMuscle = "Chest",
                auxiliaryMuscles = "Triceps, Shoulders",
                bodyweightContribution = "None"
            )
            repository.saveExercise(testExercise)
            loadExercises()
        }
    }
} 