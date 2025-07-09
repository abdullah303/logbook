package com.abdullah303.logbook.ui.create_equipment.create_smith_machine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.data.repository.SmithMachineInfoRepository
import com.abdullah303.logbook.data.repository.EquipmentRepository
import com.abdullah303.logbook.ui.create_exercise.components.SmithMachineConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateSmithMachineViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val smithMachineInfoRepository: SmithMachineInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateSmithMachineUiState())
    val uiState: StateFlow<CreateSmithMachineUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)
    }

    fun updateWeightUnit(weightUnit: WeightUnit) {
        _uiState.value = _uiState.value.copy(weightUnit = weightUnit)
    }

    fun createSmithMachine(onSuccess: (SmithMachineConfiguration) -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value
        
        // validate inputs
        if (currentState.name.isBlank()) {
            onError("Smith machine name cannot be empty")
            return
        }
        
        val weight = try {
            BigDecimal(currentState.weight)
        } catch (e: NumberFormatException) {
            onError("Invalid weight value")
            return
        }
        
        if (weight <= BigDecimal.ZERO) {
            onError("Weight must be greater than 0")
            return
        }

        viewModelScope.launch {
            try {
                // create equipment
                val equipmentId = UUID.randomUUID().toString()
                val equipment = Equipment(
                    id = equipmentId,
                    name = currentState.name.trim(),
                    equipmentType = EquipmentType.SMITH_MACHINE,
                    weight_unit = currentState.weightUnit
                )
                
                // create smith machine info
                val smithMachineInfo = SmithMachineInfo(
                    equipment_id = equipmentId,
                    bar_weight = weight
                )
                
                // insert both into database
                equipmentRepository.insertEquipment(equipment)
                smithMachineInfoRepository.insertSmithMachineInfo(smithMachineInfo)
                
                // create smith machine configuration to return
                val smithMachineConfiguration = SmithMachineConfiguration(
                    equipment = equipment,
                    smithMachineInfo = smithMachineInfo
                )
                
                // reset state after successful creation
                resetState()
                
                onSuccess(smithMachineConfiguration)
            } catch (e: Exception) {
                onError("Failed to create smith machine: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = CreateSmithMachineUiState()
    }

    fun onDismiss() {
        resetState()
    }
}

data class CreateSmithMachineUiState(
    val name: String = "",
    val weight: String = "10",
    val weightUnit: WeightUnit = WeightUnit.KG
) 