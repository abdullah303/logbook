package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.abdullah303.logbook.data.model.WeightUnit
import java.math.BigDecimal

@Entity(
    tableName = "settings",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"], unique = true)]
)
data class Settings(
    @PrimaryKey
    val id: String,
    val userId: String,
    val displayUnit: WeightUnit,
    val restTimerSeconds: Int,
    val defaultWarmupSets: Int,
    val plateSizes: List<BigDecimal>
) 