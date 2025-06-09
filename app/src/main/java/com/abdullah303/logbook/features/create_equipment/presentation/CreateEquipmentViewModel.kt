package com.abdullah303.logbook.features.create_equipment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.model.WeightRange
import com.abdullah303.logbook.core.domain.model.validate
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEquipmentViewModel @Inject constructor(
    private val repository: EquipmentRepository
) : ViewModel() {

    private val _equipment = MutableStateFlow(Equipment(type = EquipmentType.BARBELL, isPinLoaded = true))
    val equipment: StateFlow<Equipment> = _equipment.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCurrentEquipment().collect { equipment ->
                _equipment.value = equipment
            }
        }
    }

    fun updateEquipment(equipment: Equipment) {
        viewModelScope.launch {
            repository.updateEquipment(equipment)
        }
    }

    fun updateName(name: String) {
        _equipment.update { it.copy(name = name) }
        updateEquipment(_equipment.value)
    }

    fun updateType(type: EquipmentType) {
        _equipment.update { it.copy(type = type) }
        updateEquipment(_equipment.value)
    }

    fun updateIsPinLoaded(isPinLoaded: Boolean) {
        _equipment.update { currentEquipment ->
            currentEquipment.copy(
                isPinLoaded = isPinLoaded,
                // Clear ranges when switching to plate loaded
                ranges = if (!isPinLoaded) null else currentEquipment.ranges,
                // Clear machine weight and loading pegs when switching to pin loaded
                machineWeight = if (isPinLoaded) null else currentEquipment.machineWeight,
                loadingPegs = if (isPinLoaded) null else currentEquipment.loadingPegs
            )
        }
        // Don't call updateEquipment here to prevent navigation issues
    }

    fun updateMachineWeight(weight: String) {
        _equipment.update { it.copy(machineWeight = weight) }
        updateEquipment(_equipment.value)
    }

    fun updateLoadingPegs(pegs: Int) {
        _equipment.update { it.copy(loadingPegs = pegs) }
        updateEquipment(_equipment.value)
    }

    fun updateBarWeight(weight: String) {
        _equipment.update { it.copy(barWeight = weight) }
        updateEquipment(_equipment.value)
    }

    fun updateRanges(ranges: List<WeightRange>) {
        _equipment.update { it.copy(ranges = ranges) }
        updateEquipment(_equipment.value)
    }

    fun saveEquipment() {
        val currentEquipment = _equipment.value
        
        if (currentEquipment.name.isBlank()) {
            _saveResult.value = SaveResult.Error("Equipment name is required")
            return
        }
        
        if (!currentEquipment.validate()) {
            _saveResult.value = SaveResult.Error("Please fill in all required fields")
            return
        }
        
        viewModelScope.launch {
            _isSaving.value = true
            try {
                repository.saveEquipment(currentEquipment)
                _saveResult.value = SaveResult.Success
                // Clear the current equipment after saving
                repository.clearEquipment()
            } catch (e: Exception) {
                _saveResult.value = SaveResult.Error("Failed to save equipment: ${e.message}")
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun clearSaveResult() {
        _saveResult.value = null
    }

    fun clearEquipment() {
        viewModelScope.launch {
            repository.clearEquipment()
        }
    }
}

sealed class SaveResult {
    object Success : SaveResult()
    data class Error(val message: String) : SaveResult()
} 