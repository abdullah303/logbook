package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.abdullah303.logbook.data.model.Muscles
import java.math.BigDecimal

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["id"],
            childColumns = ["equipment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["equipment_id"])
    ]
)
data class Exercise(
    @PrimaryKey
    val id: String,
    val name: String,
    val equipment_id: String,
    val primaryMuscles: List<Muscles>,
    val auxiliaryMuscles: List<Muscles>,
    val bodyweightContribution: BigDecimal,
    val setup_info: List<String>?
)
