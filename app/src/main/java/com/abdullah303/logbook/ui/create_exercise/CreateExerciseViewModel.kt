package com.abdullah303.logbook.ui.create_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.Muscles
import com.abdullah303.logbook.data.model.ResistanceMachineType
import com.abdullah303.logbook.data.repository.BarbellInfoRepository
import com.abdullah303.logbook.data.repository.CableStackInfoRepository
import com.abdullah303.logbook.data.repository.EquipmentRepository
import com.abdullah303.logbook.data.repository.ExerciseRepository
import com.abdullah303.logbook.data.repository.PinLoadedInfoRepository
import com.abdullah303.logbook.data.repository.PlateLoadedInfoRepository
import com.abdullah303.logbook.data.repository.ResistanceMachineInfoRepository
import com.abdullah303.logbook.data.repository.SmithMachineInfoRepository
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.ui.create_exercise.components.BarbellConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.CableStackConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.ResistanceMachineConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.SmithMachineConfiguration
import com.abdullah303.logbook.ui.create_exercise.components.PinLoadedEquipmentInfo
import com.abdullah303.logbook.ui.create_exercise.components.PlateLoadedEquipmentInfo
import com.abdullah303.logbook.ui.create_exercise.components.toPinLoadedEquipmentInfo
import com.abdullah303.logbook.ui.create_exercise.components.toPlateLoadedEquipmentInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject


/**
 * viewmodel for managing create exercise screen state and business logic
 */
@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val exerciseRepository: ExerciseRepository,
    private val barbellInfoRepository: BarbellInfoRepository,
    private val smithMachineInfoRepository: SmithMachineInfoRepository,
    private val cableStackInfoRepository: CableStackInfoRepository,
    private val resistanceMachineInfoRepository: ResistanceMachineInfoRepository,
    private val pinLoadedInfoRepository: PinLoadedInfoRepository,
    private val plateLoadedInfoRepository: PlateLoadedInfoRepository
) : ViewModel() {
    
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

    private val _setupInfo = MutableStateFlow<List<String>>(emptyList())
    val setupInfo: StateFlow<List<String>> = _setupInfo.asStateFlow()

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
    
    // add save success state
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    // add save loading state
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    init {
        loadEquipmentConfigurations()
    }
    
    /**
     * loads all equipment configurations from the database
     */
    private fun loadEquipmentConfigurations() {
        viewModelScope.launch {
            loadBarbellConfigurations()
            loadSmithMachineConfigurations()
            loadCableStackConfigurations()
            loadResistanceMachineConfigurations()
        }
    }
    
    /**
     * loads barbell configurations from the database
     */
    private fun loadBarbellConfigurations() {
        viewModelScope.launch {
            combine(
                equipmentRepository.getEquipmentByType(EquipmentType.BARBELL),
                barbellInfoRepository.getAllBarbellInfoFlow()
            ) { equipmentList, barbellInfoList ->
                equipmentList.mapNotNull { equipment ->
                    val barbellInfo = barbellInfoList.find { it.equipment_id == equipment.id }
                    if (barbellInfo != null) {
                        BarbellConfiguration(equipment, barbellInfo)
                    } else null
                }
            }.collect { configurations ->
                _barbellConfigurations.value = configurations
            }
        }
    }
    
    /**
     * loads smith machine configurations from the database
     */
    private fun loadSmithMachineConfigurations() {
        viewModelScope.launch {
            combine(
                equipmentRepository.getEquipmentByType(EquipmentType.SMITH_MACHINE),
                smithMachineInfoRepository.getAllSmithMachineInfoFlow()
            ) { equipmentList, smithMachineInfoList ->
                equipmentList.mapNotNull { equipment ->
                    val smithMachineInfo = smithMachineInfoList.find { it.equipment_id == equipment.id }
                    if (smithMachineInfo != null) {
                        SmithMachineConfiguration(equipment, smithMachineInfo)
                    } else null
                }
            }.collect { configurations ->
                _smithMachineConfigurations.value = configurations
            }
        }
    }
    
    /**
     * loads cable stack configurations from the database
     */
    private fun loadCableStackConfigurations() {
        viewModelScope.launch {
            combine(
                equipmentRepository.getEquipmentByType(EquipmentType.CABLE_STACK),
                cableStackInfoRepository.getAllCableStackInfoFlow()
            ) { equipmentList, cableStackInfoList ->
                equipmentList.mapNotNull { equipment ->
                    val cableStackInfo = cableStackInfoList.find { it.equipment_id == equipment.id }
                    if (cableStackInfo != null) {
                        CableStackConfiguration(equipment, cableStackInfo)
                    } else null
                }
            }.collect { configurations ->
                _cableStackConfigurations.value = configurations
            }
        }
    }
    
    /**
     * loads resistance machine configurations from the database
     */
    private fun loadResistanceMachineConfigurations() {
        viewModelScope.launch {
            combine(
                equipmentRepository.getEquipmentByType(EquipmentType.RESISTANCE_MACHINE),
                resistanceMachineInfoRepository.getAllResistanceMachineInfoFlow(),
                pinLoadedInfoRepository.getAllPinLoadedInfoFlow(),
                plateLoadedInfoRepository.getAllPlateLoadedInfoFlow()
            ) { equipmentList, resistanceMachineInfoList, pinLoadedInfoList, plateLoadedInfoList ->
                equipmentList.mapNotNull { equipment ->
                    val resistanceMachineInfo = resistanceMachineInfoList.find { it.equipment_id == equipment.id }
                    if (resistanceMachineInfo != null) {
                        // determine the specific type of resistance machine
                        val specificInfo = when (resistanceMachineInfo.type) {
                            ResistanceMachineType.PIN_LOADED -> {
                                pinLoadedInfoList.find { it.resistance_machine_id == resistanceMachineInfo.equipment_id }
                                    ?.toPinLoadedEquipmentInfo(resistanceMachineInfo)
                            }
                            ResistanceMachineType.PLATE_LOADED -> {
                                plateLoadedInfoList.find { it.resistance_machine_id == resistanceMachineInfo.equipment_id }
                                    ?.toPlateLoadedEquipmentInfo(resistanceMachineInfo)
                            }
                        }
                        if (specificInfo != null) {
                            ResistanceMachineConfiguration(equipment, specificInfo)
                        } else null
                    } else null
                }
            }.collect { configurations ->
                _resistanceMachineConfigurations.value = configurations
            }
        }
    }

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
    fun updateSetupInfo(info: List<String>) {
        _setupInfo.value = info
    }
    
    /**
     * adds a new setup info line
     */
    fun addSetupInfoLine() {
        _setupInfo.value = _setupInfo.value + ""
    }
    
    /**
     * updates a specific setup info line
     */
    fun updateSetupInfoLine(index: Int, value: String) {
        val currentList = _setupInfo.value.toMutableList()
        if (index < currentList.size) {
            currentList[index] = value
            _setupInfo.value = currentList
        }
    }
    
    /**
     * removes a setup info line
     */
    fun removeSetupInfoLine(index: Int) {
        val currentList = _setupInfo.value.toMutableList()
        if (index < currentList.size) {
            currentList.removeAt(index)
            _setupInfo.value = currentList
        }
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
     * clears all selected custom equipment configurations
     */
    fun clearAllCustomEquipment() {
        _selectedSmithMachine.value = null
        _selectedBarbell.value = null
        _selectedCableStack.value = null
        _selectedResistanceMachine.value = null
    }
    
    /**
     * refreshes equipment configurations after new equipment is created
     */
    fun refreshEquipmentConfigurations() {
        loadEquipmentConfigurations()
    }
    

    
    /**
     * saves the exercise to the database
     */
    fun saveExercise() {
        viewModelScope.launch {
            try {
                _isSaving.value = true
                
                // validate required fields
                if (_exerciseName.value.isBlank()) {
                    // todo: handle validation error
                    return@launch
                }
                
                // determine equipment id based on selected configurations
                val equipmentId = getSelectedEquipmentId()
                if (equipmentId == null) {
                    // todo: handle no equipment selected error
                    return@launch
                }
                
                // create exercise entity
                val exercise = Exercise(
                    id = UUID.randomUUID().toString(),
                    name = _exerciseName.value.trim(),
                    equipment_id = equipmentId,
                    primaryMuscles = _primaryMuscles.value,
                    auxiliaryMuscles = _auxiliaryMuscles.value,
                    bodyweightContribution = BigDecimal(_bodyweightContribution.value.toString()),
                    setup_info = if (_setupInfo.value.isNotEmpty()) {
                        _setupInfo.value.filter { it.isNotBlank() } // filter out empty strings
                    } else null
                )
                
                // save to database
                exerciseRepository.insertExercise(exercise)
                
                // set success state
                _saveSuccess.value = true
                
            } catch (e: Exception) {
                // todo: handle save error
                e.printStackTrace()
            } finally {
                _isSaving.value = false
            }
        }
    }
    
    /**
     * determines the equipment id based on selected configurations
     * prioritizes specific equipment configurations over general equipment types
     */
    private fun getSelectedEquipmentId(): String? {
        // check for specific equipment configurations first
        _selectedBarbell.value?.let { return it.equipment.id }
        _selectedSmithMachine.value?.let { return it.equipment.id }
        _selectedCableStack.value?.let { return it.equipment.id }
        _selectedResistanceMachine.value?.let { return it.equipment.id }
        
        // if no specific equipment is selected, we need to handle general equipment types
        // for now, return null to indicate no equipment selected
        // todo: implement logic for general equipment types if needed
        return null
    }
    
    /**
     * resets the save success state
     */
    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }
} 