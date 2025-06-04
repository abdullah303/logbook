package com.abdullah303.logbook.core.domain.repository

import com.abdullah303.logbook.core.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    // Current exercise for create/edit flow
    fun getCurrentExercise(): Flow<Exercise>
    suspend fun updateExercise(exercise: Exercise)
    suspend fun clearExercise()
    
    // Database operations for permanent storage
    fun getAllExercises(): Flow<List<Exercise>>
    suspend fun getExerciseById(id: String): Exercise?
    suspend fun saveExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)
    suspend fun deleteExerciseById(id: String)
    fun searchExercises(searchQuery: String): Flow<List<Exercise>>
    fun getExercisesByPrimaryMuscle(muscle: String): Flow<List<Exercise>>
    fun getExercisesByEquipment(equipment: String): Flow<List<Exercise>>
    suspend fun getExerciseCount(): Int
} 