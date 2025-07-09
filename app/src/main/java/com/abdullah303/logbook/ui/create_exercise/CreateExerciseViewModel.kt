package com.abdullah303.logbook.ui.create_exercise

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * viewmodel for managing create exercise screen state and business logic
 */
@HiltViewModel
class CreateExerciseViewModel @Inject constructor() : ViewModel() {
    
    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _primaryMuscles = MutableStateFlow("")
    val primaryMuscles: StateFlow<String> = _primaryMuscles.asStateFlow()

    private val _auxiliaryMuscles = MutableStateFlow("")
    val auxiliaryMuscles: StateFlow<String> = _auxiliaryMuscles.asStateFlow()

    private val _bodyweightContribution = MutableStateFlow("")
    val bodyweightContribution: StateFlow<String> = _bodyweightContribution.asStateFlow()

    private val _setupInfo = MutableStateFlow("")
    val setupInfo: StateFlow<String> = _setupInfo.asStateFlow()
    
    /**
     * updates the exercise name
     */
    fun updateExerciseName(name: String) {
        _exerciseName.value = name
    }

    /**
     * updates the primary muscles
     */
    fun updatePrimaryMuscles(muscles: String) {
        _primaryMuscles.value = muscles
    }

    /**
     * updates the auxiliary muscles
     */
    fun updateAuxiliaryMuscles(muscles: String) {
        _auxiliaryMuscles.value = muscles
    }

    /**
     * updates the bodyweight contribution
     */
    fun updateBodyweightContribution(contribution: String) {
        _bodyweightContribution.value = contribution
    }

    /**
     * updates the setup info
     */
    fun updateSetupInfo(info: String) {
        _setupInfo.value = info
    }
    
    /**
     * saves the exercise (placeholder for future implementation)
     */
    fun saveExercise() {
        // todo: implement save logic
        // this will likely involve calling a repository to save the exercise
    }
} 