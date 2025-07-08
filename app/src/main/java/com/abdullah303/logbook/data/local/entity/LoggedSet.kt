package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "logged_sets",
    foreignKeys = [
        ForeignKey(
            entity = LoggedWorkout::class,
            parentColumns = ["id"],
            childColumns = ["logged_workout_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutExerciseSnapshot::class,
            parentColumns = ["id"],
            childColumns = ["exercise_snapshot_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["logged_workout_id"]),
        Index(value = ["exercise_snapshot_id"])
    ]
)
data class LoggedSet(
    @PrimaryKey
    val id: String,
    val logged_workout_id: String,
    val exercise_snapshot_id: String,
    val set_number: Int,
    val weight: BigDecimal?,
    val reps: Int?,
    val rir: Int?
) 