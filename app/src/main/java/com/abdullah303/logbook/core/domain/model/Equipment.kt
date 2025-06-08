package com.abdullah303.logbook.core.domain.model

import com.abdullah303.logbook.core.domain.model.EquipmentType

data class Equipment(
    val id: String = "",
    val name: String = "",
    val type: EquipmentType,
    // Cable Stack specific
    val ranges: List<WeightRange>? = null,
    // Resistance Machine specific
    val isPinLoaded: Boolean? = null,
    val machineWeight: String? = null,
    val loadingPegs: Int? = null,
    // Smith Machine specific
    val barWeight: String? = null
)

data class WeightRange(
    val weight: String,
    val minReps: String,
    val maxReps: String
)

// Extension functions to validate equipment based on type
fun Equipment.validate(): Boolean {
    return when (type) {
        EquipmentType.CABLE_STACK -> {
            name.isNotBlank() && !ranges.isNullOrEmpty()
        }
        EquipmentType.RESISTANCE_MACHINE -> {
            name.isNotBlank() && when (isPinLoaded) {
                true -> !ranges.isNullOrEmpty()
                false -> machineWeight?.isNotBlank() == true && loadingPegs != null
                null -> false
            }
        }
        EquipmentType.SMITH_MACHINE -> {
            name.isNotBlank() && barWeight?.isNotBlank() == true
        }
        else -> name.isNotBlank() // For other equipment types, just name is required
    }
} 