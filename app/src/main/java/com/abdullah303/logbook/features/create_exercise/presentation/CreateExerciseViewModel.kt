package com.abdullah303.logbook.features.create_exercise.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val equipmentRepository: EquipmentRepository
) : ViewModel() {

    private val _exercise = MutableStateFlow(Exercise())
    val exercise: StateFlow<Exercise> = _exercise.asStateFlow()

    private val _equipmentName = MutableStateFlow("")
    val equipmentName: StateFlow<String> = _equipmentName.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()

    init {
        viewModelScope.launch {
            exerciseRepository.getCurrentExercise().collect { exercise ->
                _exercise.value = exercise
                updateEquipmentName(exercise.equipment)
            }
        }
    }

    private fun updateEquipmentName(equipmentIdentifier: String) {
        viewModelScope.launch {
            if (isValidUUID(equipmentIdentifier)) {
                val equipment = equipmentRepository.getEquipmentById(equipmentIdentifier)
                _equipmentName.value = equipment?.name ?: ""
            } else {
                _equipmentName.value = equipmentIdentifier
            }
        }
    }

    private fun isValidUUID(uuid: String): Boolean {
        return try {
            UUID.fromString(uuid)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.updateExercise(exercise)
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

    fun saveExercise() {
        val currentExercise = _exercise.value
        
        if (currentExercise.name.isBlank()) {
            _saveResult.value = SaveResult.Error("Exercise name is required")
            return
        }
        
        viewModelScope.launch {
            _isSaving.value = true
            try {
                exerciseRepository.saveExercise(currentExercise)
                _saveResult.value = SaveResult.Success
                // Clear the current exercise after saving
                exerciseRepository.clearExercise()
            } catch (e: Exception) {
                _saveResult.value = SaveResult.Error("Failed to save exercise: ${e.message}")
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun clearSaveResult() {
        _saveResult.value = null
    }

    fun clearExercise() {
        viewModelScope.launch {
            exerciseRepository.clearExercise()
        }
    }
}

sealed class SaveResult {
    object Success : SaveResult()
    data class Error(val message: String) : SaveResult()
} 