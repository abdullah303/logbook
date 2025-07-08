package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "splits")
data class Splits(
    @PrimaryKey
    val id: String,
    val user_id: String,
    val name: String
) 