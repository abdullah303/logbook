package com.abdullah303.logbook.core.domain.model

data class Equipment(
    val id: String = "",
    val name: String = "",
    val type: EquipmentType,
    // Cable Stack specific
    val ranges: List<WeightRange>? = null,
    // Resistance Machine specific
    val isPinLoaded: Boolean? = null,
    val machineWeight: Float? = null,
    val loadingPegs: Int? = null,
    // Smith Machine specific
    val barWeight: Float? = null
)

data class WeightRange(
    val step: Float,
    val minWeight: Float,
    val maxWeight: Float
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
                false -> machineWeight != null && loadingPegs != null
                null -> false
            }
        }
        EquipmentType.SMITH_MACHINE -> {
            name.isNotBlank() && barWeight != null
        }
        else -> name.isNotBlank() // For other equipment types, just name is required
    }
} 