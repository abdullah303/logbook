package com.abdullah303.logbook.ui.create_equipment.create_barbell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.WeightUnit
import com.abdullah303.logbook.data.repository.BarbellInfoRepository
import com.abdullah303.logbook.data.repository.EquipmentRepository
import com.abdullah303.logbook.ui.create_exercise.components.BarbellConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateBarbellViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val barbellInfoRepository: BarbellInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateBarbellUiState())
    val uiState: StateFlow<CreateBarbellUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)
    }

    fun updateWeightUnit(weightUnit: WeightUnit) {
        _uiState.value = _uiState.value.copy(weightUnit = weightUnit)
    }

    fun createBarbell(onSuccess: (BarbellConfiguration) -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value
        
        // validate inputs
        if (currentState.name.isBlank()) {
            onError("Barbell name cannot be empty")
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
                    equipmentType = EquipmentType.BARBELL,
                    weight_unit = currentState.weightUnit
                )
                
                // create barbell info
                val barbellInfo = BarbellInfo(
                    equipment_id = equipmentId,
                    bar_weight = weight
                )
                
                // insert both into database
                equipmentRepository.insertEquipment(equipment)
                barbellInfoRepository.insertBarbellInfo(barbellInfo)
                
                // create barbell configuration to return
                val barbellConfiguration = BarbellConfiguration(
                    equipment = equipment,
                    barbellInfo = barbellInfo
                )
                
                // reset state after successful creation
                resetState()
                
                onSuccess(barbellConfiguration)
            } catch (e: Exception) {
                onError("Failed to create barbell: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = CreateBarbellUiState()
    }

    fun onDismiss() {
        resetState()
    }
}

data class CreateBarbellUiState(
    val name: String = "",
    val weight: String = "20",
    val weightUnit: WeightUnit = WeightUnit.KG
) 