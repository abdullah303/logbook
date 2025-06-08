package com.abdullah303.logbook.core.di

import com.abdullah303.logbook.core.data.repository.EquipmentRepositoryImpl
import com.abdullah303.logbook.core.data.repository.ExerciseRepositoryImpl
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindExerciseRepository(
        exerciseRepositoryImpl: ExerciseRepositoryImpl
    ): ExerciseRepository
    
    @Binds
    @Singleton
    abstract fun bindEquipmentRepository(
        equipmentRepositoryImpl: EquipmentRepositoryImpl
    ): EquipmentRepository
} 