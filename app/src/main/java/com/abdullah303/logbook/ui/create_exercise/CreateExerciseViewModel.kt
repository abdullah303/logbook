package com.abdullah303.logbook.ui.create_exercise

import androidx.lifecycle.ViewModel
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.Muscles
import com.abdullah303.logbook.ui.create_exercise.components.BarbellConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.CableStackConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.ResistanceMachineConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.SmithMachineConfiguration
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

    private val _smithMachineConfigurations = MutableStateFlow<List<SmithMachineConfiguration>>(emptyList())
    val smithMachineConfigurations: StateFlow<List<SmithMachineConfiguration>> = _smithMachineConfigurations.asStateFlow()

    private val _selectedSmithMachine = MutableStateFlow<SmithMachineConfiguration?>(null)
    val selectedSmithMachine: StateFlow<SmithMachineConfiguration?> = _selectedSmithMachine.asStateFlow()

    private val _barbellConfigurations = MutableStateFlow<List<BarbellConfiguration>>(emptyList())
    val barbellConfigurations: StateFlow<List<BarbellConfiguration>> = _barbellConfigurations.asStateFlow()

    private val _selectedBarbell = MutableStateFlow<BarbellConfiguration?>(null)
    val selectedBarbell: StateFlow<BarbellConfiguration?> = _selectedBarbell.asStateFlow()

    private val _cableStackConfigurations = MutableStateFlow<List<CableStackConfiguration>>(emptyList())
    val cableStackConfigurations: StateFlow<List<CableStackConfiguration>> = _cableStackConfigurations.asStateFlow()

    private val _selectedCableStack = MutableStateFlow<CableStackConfiguration?>(null)
    val selectedCableStack: StateFlow<CableStackConfiguration?> = _selectedCableStack.asStateFlow()

    private val _resistanceMachineConfigurations = MutableStateFlow<List<ResistanceMachineConfiguration>>(emptyList())
    val resistanceMachineConfigurations: StateFlow<List<ResistanceMachineConfiguration>> = _resistanceMachineConfigurations.asStateFlow()

    private val _selectedResistanceMachine = MutableStateFlow<ResistanceMachineConfiguration?>(null)
    val selectedResistanceMachine: StateFlow<ResistanceMachineConfiguration?> = _selectedResistanceMachine.asStateFlow()
    
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
     * updates the smith machine configurations
     */
    fun updateSmithMachineConfigurations(configurations: List<SmithMachineConfiguration>) {
        _smithMachineConfigurations.value = configurations
    }

    /**
     * selects a smith machine configuration
     */
    fun selectSmithMachine(configuration: SmithMachineConfiguration) {
        _selectedSmithMachine.value = configuration
    }

    /**
     * clears the selected smith machine
     */
    fun clearSelectedSmithMachine() {
        _selectedSmithMachine.value = null
    }

    /**
     * updates the barbell configurations
     */
    fun updateBarbellConfigurations(configurations: List<BarbellConfiguration>) {
        _barbellConfigurations.value = configurations
    }

    /**
     * selects a barbell configuration
     */
    fun selectBarbell(configuration: BarbellConfiguration) {
        _selectedBarbell.value = configuration
    }

    /**
     * clears the selected barbell
     */
    fun clearSelectedBarbell() {
        _selectedBarbell.value = null
    }

    /**
     * updates the cable stack configurations
     */
    fun updateCableStackConfigurations(configurations: List<CableStackConfiguration>) {
        _cableStackConfigurations.value = configurations
    }

    /**
     * selects a cable stack configuration
     */
    fun selectCableStack(configuration: CableStackConfiguration) {
        _selectedCableStack.value = configuration
    }

    /**
     * clears the selected cable stack
     */
    fun clearSelectedCableStack() {
        _selectedCableStack.value = null
    }

    /**
     * updates the resistance machine configurations
     */
    fun updateResistanceMachineConfigurations(configurations: List<ResistanceMachineConfiguration>) {
        _resistanceMachineConfigurations.value = configurations
    }

    /**
     * selects a resistance machine configuration
     */
    fun selectResistanceMachine(configuration: ResistanceMachineConfiguration) {
        _selectedResistanceMachine.value = configuration
    }

    /**
     * clears the selected resistance machine
     */
    fun clearSelectedResistanceMachine() {
        _selectedResistanceMachine.value = null
    }
    
    /**
     * saves the exercise (placeholder for future implementation)
     */
    fun saveExercise() {
        // todo: implement save logic
        // this will likely involve calling a repository to save the exercise
    }
} 