package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.abdullah303.logbook.data.model.LoggedWorkoutStatus
import java.time.LocalDateTime

@Entity(
    tableName = "logged_workouts",
    foreignKeys = [
        ForeignKey(
            entity = LoggedSplits::class,
            parentColumns = ["id"],
            childColumns = ["logged_split_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["logged_split_id", "workout_id"], unique = true),
        Index(value = ["logged_split_id"]),
        Index(value = ["workout_id"])
    ]
)
data class LoggedWorkout(
    @PrimaryKey
    val id: String,
    val logged_split_id: String,
    val workout_id: String,
    val logged_at: LocalDateTime?,
    val status: LoggedWorkoutStatus,
    val duration_seconds: Int?
) 