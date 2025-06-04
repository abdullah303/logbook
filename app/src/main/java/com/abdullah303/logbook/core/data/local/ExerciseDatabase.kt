package com.abdullah303.logbook.core.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [ExerciseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExerciseDatabase : RoomDatabase() {
    
    abstract fun exerciseDao(): ExerciseDao
    
    companion object {
        const val DATABASE_NAME = "exercise_database"
    }
} 