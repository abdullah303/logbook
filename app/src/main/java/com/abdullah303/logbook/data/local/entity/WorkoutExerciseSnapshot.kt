package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_exercise_snapshots",
    foreignKeys = [
        ForeignKey(
            entity = LoggedWorkout::class,
            parentColumns = ["id"],
            childColumns = ["logged_workout_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = ["id"],
            childColumns = ["workout_exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["logged_workout_id"]),
        Index(value = ["workout_exercise_id"])
    ]
)
data class WorkoutExerciseSnapshot(
    @PrimaryKey
    val id: String,
    val logged_workout_id: String,
    val workout_exercise_id: String,
    val position: Int,
    val sets: Int,
    val reps_min: Int,
    val reps_max: Int,
    val rir: Int,
    val dropset: Boolean,
    val setup_info: List<String>?
) 