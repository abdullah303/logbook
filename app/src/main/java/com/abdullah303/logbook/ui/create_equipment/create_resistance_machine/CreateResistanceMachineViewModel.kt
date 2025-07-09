package com.abdullah303.logbook.ui.create_equipment.create_resistance_machine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.ResistanceMachineType
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.data.repository.EquipmentRepository
import com.abdullah303.logbook.data.repository.ResistanceMachineInfoRepository
import com.abdullah303.logbook.data.repository.PinLoadedInfoRepository
import com.abdullah303.logbook.data.repository.PlateLoadedInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateResistanceMachineViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val resistanceMachineInfoRepository: ResistanceMachineInfoRepository,
    private val pinLoadedInfoRepository: PinLoadedInfoRepository,
    private val plateLoadedInfoRepository: PlateLoadedInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateResistanceMachineUiState())
    val uiState: StateFlow<CreateResistanceMachineUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateMachineType(machineType: ResistanceMachineType) {
        _uiState.value = _uiState.value.copy(machineType = machineType)
    }

    fun updateWeightUnit(weightUnit: WeightUnit) {
        _uiState.value = _uiState.value.copy(weightUnit = weightUnit)
    }

    // pin loaded specific updates
    fun updateMinWeight(minWeight: String) {
        _uiState.value = _uiState.value.copy(minWeight = minWeight)
    }

    fun updateMaxWeight(maxWeight: String) {
        _uiState.value = _uiState.value.copy(maxWeight = maxWeight)
    }

    fun updateIncrement(increment: String) {
        _uiState.value = _uiState.value.copy(increment = increment)
    }

    // plate loaded specific updates
    fun updateNumberOfPegs(numPegs: Int) {
        _uiState.value = _uiState.value.copy(numPegs = numPegs)
    }

    fun updateBaseMachineWeight(baseWeight: String) {
        _uiState.value = _uiState.value.copy(baseMachineWeight = baseWeight)
    }

    fun createResistanceMachine(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value
        
        // validate name
        if (currentState.name.isBlank()) {
            onError("resistance machine name cannot be empty")
            return
        }
        
        // validate based on machine type
        when (currentState.machineType) {
            ResistanceMachineType.PIN_LOADED -> {
                if (!validatePinLoadedFields(currentState, onError)) return
            }
            ResistanceMachineType.PLATE_LOADED -> {
                if (!validatePlateLoadedFields(currentState, onError)) return
            }
        }

        viewModelScope.launch {
            try {
                val equipmentId = UUID.randomUUID().toString()
                
                // create equipment
                val equipment = Equipment(
                    id = equipmentId,
                    name = currentState.name.trim(),
                    equipmentType = EquipmentType.RESISTANCE_MACHINE,
                    weight_unit = currentState.weightUnit
                )
                
                // create resistance machine info
                val resistanceMachineInfo = ResistanceMachineInfo(
                    equipment_id = equipmentId,
                    type = currentState.machineType
                )
                
                // insert equipment and resistance machine info
                equipmentRepository.insertEquipment(equipment)
                resistanceMachineInfoRepository.insertResistanceMachineInfo(resistanceMachineInfo)
                
                // insert type-specific info
                when (currentState.machineType) {
                    ResistanceMachineType.PIN_LOADED -> {
                        val pinLoadedInfo = PinLoadedInfo(
                            resistance_machine_id = equipmentId,
                            min_weight = BigDecimal(currentState.minWeight),
                            max_weight = BigDecimal(currentState.maxWeight),
                            increment = BigDecimal(currentState.increment)
                        )
                        pinLoadedInfoRepository.insertPinLoadedInfo(pinLoadedInfo)
                    }
                    ResistanceMachineType.PLATE_LOADED -> {
                        val plateLoadedInfo = PlateLoadedInfo(
                            resistance_machine_id = equipmentId,
                            num_pegs = currentState.numPegs,
                            base_machine_weight = BigDecimal(currentState.baseMachineWeight)
                        )
                        plateLoadedInfoRepository.insertPlateLoadedInfo(plateLoadedInfo)
                    }
                }
                
                // reset state after successful creation
                resetState()
                
                onSuccess()
            } catch (e: Exception) {
                onError("failed to create resistance machine: ${e.message}")
            }
        }
    }

    private fun validatePinLoadedFields(state: CreateResistanceMachineUiState, onError: (String) -> Unit): Boolean {
        val minWeight = try {
            BigDecimal(state.minWeight)
        } catch (e: NumberFormatException) {
            onError("invalid minimum weight value")
            return false
        }
        
        val maxWeight = try {
            BigDecimal(state.maxWeight)
        } catch (e: NumberFormatException) {
            onError("invalid maximum weight value")
            return false
        }
        
        val increment = try {
            BigDecimal(state.increment)
        } catch (e: NumberFormatException) {
            onError("invalid increment value")
            return false
        }
        
        // validate weight relationships
        if (minWeight < BigDecimal.ZERO) {
            onError("minimum weight cannot be negative")
            return false
        }
        
        if (maxWeight <= minWeight) {
            onError("maximum weight must be greater than minimum weight")
            return false
        }
        
        if (increment <= BigDecimal.ZERO) {
            onError("increment must be greater than 0")
            return false
        }
        
        if (increment > (maxWeight - minWeight)) {
            onError("increment cannot be larger than the weight range")
            return false
        }
        
        return true
    }

    private fun validatePlateLoadedFields(state: CreateResistanceMachineUiState, onError: (String) -> Unit): Boolean {
        val baseWeight = try {
            BigDecimal(state.baseMachineWeight)
        } catch (e: NumberFormatException) {
            onError("invalid base machine weight value")
            return false
        }
        
        if (baseWeight < BigDecimal.ZERO) {
            onError("base machine weight cannot be negative")
            return false
        }
        
        if (state.numPegs <= 0) {
            onError("number of pegs must be greater than 0")
            return false
        }
        
        return true
    }

    fun resetState() {
        _uiState.value = CreateResistanceMachineUiState()
    }

    fun onDismiss() {
        resetState()
    }
}

data class CreateResistanceMachineUiState(
    val name: String = "",
    val machineType: ResistanceMachineType = ResistanceMachineType.PIN_LOADED,
    val weightUnit: WeightUnit = WeightUnit.KG,
    // pin loaded fields
    val minWeight: String = "0",
    val maxWeight: String = "100",
    val increment: String = "2.5",
    // plate loaded fields
    val numPegs: Int = 1,
    val baseMachineWeight: String = "0"
) 