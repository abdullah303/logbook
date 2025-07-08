package com.abdullah303.logbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val weight: BigDecimal,
    val height: BigDecimal,
    val createdAt: LocalDateTime
) 