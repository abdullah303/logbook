package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "logged_splits",
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
data class LoggedSplits(
    @PrimaryKey
    val id: String,
    val split_id: String,
    val run_number: Int,
    val started_at: LocalDateTime,
    val completed_at: LocalDateTime?
) 