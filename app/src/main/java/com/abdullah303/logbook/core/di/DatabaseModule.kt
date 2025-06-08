package com.abdullah303.logbook.core.di

import android.content.Context
import androidx.room.Room
import com.abdullah303.logbook.core.data.local.AppDatabase
import com.abdullah303.logbook.core.data.local.EquipmentDao
import com.abdullah303.logbook.core.data.local.ExerciseDao
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao()
    }
    
    @Provides
    fun provideEquipmentDao(database: AppDatabase): EquipmentDao {
        return database.equipmentDao()
    }
} 