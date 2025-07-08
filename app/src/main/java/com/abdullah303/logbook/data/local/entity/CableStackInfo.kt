package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "cable_stack_info",
    foreignKeys = [
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["id"],
            childColumns = ["equipment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CableStackInfo(
    @PrimaryKey
    val equipment_id: String,
    val min_weight: BigDecimal,
    val max_weight: BigDecimal,
    val increment: BigDecimal
) 