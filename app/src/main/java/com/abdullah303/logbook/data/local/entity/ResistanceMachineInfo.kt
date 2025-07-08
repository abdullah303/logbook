package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.abdullah303.logbook.data.model.ResistanceMachineType

@Entity(
    tableName = "resistance_machine_info",
    foreignKeys = [
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["id"],
            childColumns = ["equipment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ResistanceMachineInfo(
    @PrimaryKey
    val equipment_id: String,
    val type: ResistanceMachineType
) 