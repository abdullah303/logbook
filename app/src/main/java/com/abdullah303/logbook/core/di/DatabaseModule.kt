package com.abdullah303.logbook.core.di

import android.content.Context
import androidx.room.Room
import com.abdullah303.logbook.core.data.local.ExerciseDao
import com.abdullah303.logbook.core.data.local.ExerciseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideExerciseDatabase(
        @ApplicationContext context: Context
    ): ExerciseDatabase {
        return Room.databaseBuilder(
            context,
            ExerciseDatabase::class.java,
            ExerciseDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    fun provideExerciseDao(database: ExerciseDatabase): ExerciseDao {
        return database.exerciseDao()
    }
} 