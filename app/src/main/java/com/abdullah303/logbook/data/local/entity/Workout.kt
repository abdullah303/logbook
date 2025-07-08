package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workouts",
    foreignKeys = [
        ForeignKey(
            entity = Splits::class,
            parentColumns = ["id"],
            childColumns = ["split_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["split_id"])
    ]
)
data class Workout(
    @PrimaryKey
    val id: String,
    val split_id: String,
    val name: String,
    val position: Int
) 