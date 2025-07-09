package com.abdullah303.logbook.ui.create_equipment.create_cable_stack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.data.repository.CableStackInfoRepository
import com.abdullah303.logbook.data.repository.EquipmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateCableStackViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val cableStackInfoRepository: CableStackInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCableStackUiState())
    val uiState: StateFlow<CreateCableStackUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateMinWeight(minWeight: String) {
        _uiState.value = _uiState.value.copy(minWeight = minWeight)
    }

    fun updateMaxWeight(maxWeight: String) {
        _uiState.value = _uiState.value.copy(maxWeight = maxWeight)
    }

    fun updateIncrement(increment: String) {
        _uiState.value = _uiState.value.copy(increment = increment)
    }

    fun updateWeightUnit(weightUnit: WeightUnit) {
        _uiState.value = _uiState.value.copy(weightUnit = weightUnit)
    }

    fun createCableStack(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value
        
        // validate inputs
        if (currentState.name.isBlank()) {
            onError("Cable stack name cannot be empty")
            return
        }
        
        val minWeight = try {
            BigDecimal(currentState.minWeight)
        } catch (e: NumberFormatException) {
            onError("Invalid minimum weight value")
            return
        }
        
        val maxWeight = try {
            BigDecimal(currentState.maxWeight)
        } catch (e: NumberFormatException) {
            onError("Invalid maximum weight value")
            return
        }
        
        val increment = try {
            BigDecimal(currentState.increment)
        } catch (e: NumberFormatException) {
            onError("Invalid increment value")
            return
        }
        
        // validate weight relationships
        if (minWeight < BigDecimal.ZERO) {
            onError("Minimum weight cannot be negative")
            return
        }
        
        if (maxWeight <= minWeight) {
            onError("Maximum weight must be greater than minimum weight")
            return
        }
        
        if (increment <= BigDecimal.ZERO) {
            onError("Increment must be greater than 0")
            return
        }
        
        if (increment > (maxWeight - minWeight)) {
            onError("Increment cannot be larger than the weight range")
            return
        }

        viewModelScope.launch {
            try {
                // create equipment
                val equipmentId = UUID.randomUUID().toString()
                val equipment = Equipment(
                    id = equipmentId,
                    name = currentState.name.trim(),
                    equipmentType = EquipmentType.CABLE_STACK,
                    weight_unit = currentState.weightUnit
                )
                
                // create cable stack info
                val cableStackInfo = CableStackInfo(
                    equipment_id = equipmentId,
                    min_weight = minWeight,
                    max_weight = maxWeight,
                    increment = increment
                )
                
                // insert both into database
                equipmentRepository.insertEquipment(equipment)
                cableStackInfoRepository.insertCableStackInfo(cableStackInfo)
                
                // reset state after successful creation
                resetState()
                
                onSuccess()
            } catch (e: Exception) {
                onError("Failed to create cable stack: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = CreateCableStackUiState()
    }

    fun onDismiss() {
        resetState()
    }
}

data class CreateCableStackUiState(
    val name: String = "",
    val minWeight: String = "0",
    val maxWeight: String = "100",
    val increment: String = "2.5",
    val weightUnit: WeightUnit = WeightUnit.KG
) 