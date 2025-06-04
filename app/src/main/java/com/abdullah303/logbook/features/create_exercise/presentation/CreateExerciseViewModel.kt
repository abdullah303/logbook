package com.abdullah303.logbook.features.create_exercise.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _exercise = MutableStateFlow(Exercise())
    val exercise: StateFlow<Exercise> = _exercise.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCurrentExercise().collect { exercise ->
                _exercise.value = exercise
            }
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.updateExercise(exercise)
        }
    }

    fun updateName(name: String) {
        _exercise.update { it.copy(name = name) }
        updateExercise(_exercise.value)
    }

    fun updateEquipment(equipment: String) {
        _exercise.update { it.copy(equipment = equipment) }
        updateExercise(_exercise.value)
    }

    fun updatePrimaryMuscle(muscle: String) {
        _exercise.update { it.copy(primaryMuscle = muscle) }
        updateExercise(_exercise.value)
    }

    fun updateAuxiliaryMuscles(muscles: String) {
        _exercise.update { it.copy(auxiliaryMuscles = muscles) }
        updateExercise(_exercise.value)
    }

    fun updateBodyweightContribution(contribution: String) {
        _exercise.update { it.copy(bodyweightContribution = contribution) }
        updateExercise(_exercise.value)
    }

    fun clearExercise() {
        viewModelScope.launch {
            repository.clearExercise()
        }
    }
} 