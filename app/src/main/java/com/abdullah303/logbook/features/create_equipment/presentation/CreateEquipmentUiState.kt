package com.abdullah303.logbook.features.create_equipment.presentation

import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.model.WeightRange

data class CreateEquipmentUiState(
    val name: String = "",
    val type: EquipmentType = EquipmentType.BARBELL,
    val isPinLoaded: Boolean? = true,
    val machineWeight: Float? = null,
    val loadingPegs: Int = 2,
    val barWeight: Float? = null,
    val ranges: List<WeightRange> = emptyList(),
    val isSaving: Boolean = false,
    val saveResult: SaveResult? = null
) {
    fun toEquipment(): Equipment {
        return Equipment(
            name = name,
            type = type,
            isPinLoaded = isPinLoaded,
            machineWeight = machineWeight,
            loadingPegs = loadingPegs.takeIf { it > 0 },
            barWeight = barWeight,
            ranges = ranges.takeIf { it.isNotEmpty() }
        )
    }

    companion object {
        fun fromEquipment(equipment: Equipment): CreateEquipmentUiState {
            return CreateEquipmentUiState(
                name = equipment.name,
                type = equipment.type,
                isPinLoaded = equipment.isPinLoaded,
                machineWeight = equipment.machineWeight,
                loadingPegs = equipment.loadingPegs ?: 2,
                barWeight = equipment.barWeight,
                ranges = equipment.ranges ?: emptyList()
            )
        }
    }
} 