package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "dumbbell_ranges",
    foreignKeys = [
        ForeignKey(
            entity = Settings::class,
            parentColumns = ["id"],
            childColumns = ["settingsId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["settingsId"])]
)
data class DumbbellRange(
    @PrimaryKey
    val id: String,
    val settingsId: String,
    val startWeight: BigDecimal,
    val endWeight: BigDecimal,
    val increment: BigDecimal
) 