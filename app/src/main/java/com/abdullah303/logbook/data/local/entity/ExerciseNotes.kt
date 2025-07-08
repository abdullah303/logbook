package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_notes",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = ["id"],
            childColumns = ["workout_exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoggedWorkout::class,
            parentColumns = ["id"],
            childColumns = ["logged_workout_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["workout_exercise_id"]),
        Index(value = ["logged_workout_id"])
    ]
)
data class ExerciseNotes(
    @PrimaryKey
    val id: String,
    val workout_exercise_id: String,
    val logged_workout_id: String?,
    val content: String
) 