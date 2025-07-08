package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullah303.logbook.data.model.EquipmentType
import com.abdullah303.logbook.data.model.WeightUnit

@Entity(tableName = "equipment")
data class Equipment(
    @PrimaryKey
    val id: String,
    val name: String,
    val equipmentType: EquipmentType,
    val weight_unit: WeightUnit
) 