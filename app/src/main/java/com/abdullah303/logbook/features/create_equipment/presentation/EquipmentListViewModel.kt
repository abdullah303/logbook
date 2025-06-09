package com.abdullah303.logbook.features.create_equipment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EquipmentListUiState(
    val equipment: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EquipmentListViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipmentListUiState())
    val uiState: StateFlow<EquipmentListUiState> = _uiState.asStateFlow()

    init {
        loadEquipment()
    }

    private fun loadEquipment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                equipmentRepository.getAllEquipment()
                    .collect { equipment ->
                        _uiState.update { 
                            it.copy(
                                equipment = equipment,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load equipment"
                    )
                }
            }
        }
    }

    private fun getEquipmentTypeFromDisplayName(displayName: String): EquipmentType? {
        return when (displayName) {
            "Cable Stack" -> EquipmentType.CABLE_STACK
            "Resistance Machine" -> EquipmentType.RESISTANCE_MACHINE
            "Smith Machine" -> EquipmentType.SMITH_MACHINE
            else -> null
        }
    }

    fun filterEquipment(type: String, searchQuery: String): List<Equipment> {
        val equipmentType = getEquipmentTypeFromDisplayName(type)
        return uiState.value.equipment.filter { equipment ->
            equipment.type == equipmentType &&
            equipment.name.contains(searchQuery, ignoreCase = true)
        }
    }
} 