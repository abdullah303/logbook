package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "pin_loaded_info",
    foreignKeys = [
        ForeignKey(
            entity = ResistanceMachineInfo::class,
            parentColumns = ["equipment_id"],
            childColumns = ["resistance_machine_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PinLoadedInfo(
    @PrimaryKey
    val resistance_machine_id: String,
    val min_weight: BigDecimal,
    val max_weight: BigDecimal,
    val increment: BigDecimal
) 