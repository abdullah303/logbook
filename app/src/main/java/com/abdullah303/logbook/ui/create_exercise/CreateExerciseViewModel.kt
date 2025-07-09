package com.abdullah303.logbook.ui.create_exercise

import androidx.lifecycle.ViewModel
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.Muscles
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

    private val _equipment = MutableStateFlow<List<EquipmentType>>(emptyList())
    val equipment: StateFlow<List<EquipmentType>> = _equipment.asStateFlow()

    private val _primaryMuscles = MutableStateFlow<List<Muscles>>(emptyList())
    val primaryMuscles: StateFlow<List<Muscles>> = _primaryMuscles.asStateFlow()

    private val _auxiliaryMuscles = MutableStateFlow<List<Muscles>>(emptyList())
    val auxiliaryMuscles: StateFlow<List<Muscles>> = _auxiliaryMuscles.asStateFlow()

    private val _bodyweightContribution = MutableStateFlow(0f)
    val bodyweightContribution: StateFlow<Float> = _bodyweightContribution.asStateFlow()

    private val _setupInfo = MutableStateFlow("")
    val setupInfo: StateFlow<String> = _setupInfo.asStateFlow()
    
    /**
     * updates the exercise name
     */
    fun updateExerciseName(name: String) {
        _exerciseName.value = name
    }

    /**
     * updates the equipment
     */
    fun updateEquipment(equipment: List<EquipmentType>) {
        _equipment.value = equipment
    }

    /**
     * updates the primary muscles
     */
    fun updatePrimaryMuscles(muscles: List<Muscles>) {
        _primaryMuscles.value = muscles
    }

    /**
     * updates the auxiliary muscles
     */
    fun updateAuxiliaryMuscles(muscles: List<Muscles>) {
        _auxiliaryMuscles.value = muscles
    }

    /**
     * updates the bodyweight contribution
     */
    fun updateBodyweightContribution(contribution: Float) {
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