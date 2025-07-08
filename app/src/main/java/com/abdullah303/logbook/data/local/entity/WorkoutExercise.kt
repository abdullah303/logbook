package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_exercises",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SupersetGroup::class,
            parentColumns = ["id"],
            childColumns = ["superset_group_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["workout_id"]),
        Index(value = ["exercise_id"]),
        Index(value = ["superset_group_id"])
    ]
)
data class WorkoutExercise(
    @PrimaryKey
    val id: String,
    val workout_id: String,
    val exercise_id: String,
    val position: Int,
    val sets: Int,
    val reps_min: Int,
    val reps_max: Int,
    val rir: Int,
    val dropset: Boolean,
    val superset_group_id: String?
)
