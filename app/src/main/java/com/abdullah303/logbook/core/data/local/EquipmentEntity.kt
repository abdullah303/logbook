package com.abdullah303.logbook.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.model.WeightRange
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val isPinLoaded: Boolean?,
    val machineWeight: Float?,
    val loadingPegs: Int?,
    val barWeight: Float?,
    val ranges: String? // JSON string of List<WeightRange>
)

// Extension functions to convert between Entity and Domain model
fun EquipmentEntity.toDomainModel(): Equipment {
    val rangesList = ranges?.let {
        val type = object : TypeToken<List<WeightRange>>() {}.type
        Gson().fromJson<List<WeightRange>>(it, type)
    }
    
    return Equipment(
        id = id,
        name = name,
        type = EquipmentType.valueOf(type),
        isPinLoaded = isPinLoaded,
        machineWeight = machineWeight,
        loadingPegs = loadingPegs,
        barWeight = barWeight,
        ranges = rangesList
    )
}

fun Equipment.toEntity(): EquipmentEntity {
    return EquipmentEntity(
        id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
        name = name,
        type = type.name,
        isPinLoaded = isPinLoaded,
        machineWeight = machineWeight,
        loadingPegs = loadingPegs,
        barWeight = barWeight,
        ranges = ranges?.let { Gson().toJson(it) }
    )
} 