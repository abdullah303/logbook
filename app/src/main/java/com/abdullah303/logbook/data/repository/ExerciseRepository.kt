package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    
    suspend fun insertExercise(exercise: Exercise)
    
    suspend fun insertAllExercises(exercises: List<Exercise>)
    
    suspend fun getExerciseById(id: String): Exercise?
    
    fun getExerciseByIdFlow(id: String): Flow<Exercise?>
    
    suspend fun getAllExercises(): List<Exercise>
    
    fun getAllExercisesFlow(): Flow<List<Exercise>>
    
    suspend fun getExercisesByEquipmentId(equipmentId: String): List<Exercise>
    
    fun getExercisesByEquipmentIdFlow(equipmentId: String): Flow<List<Exercise>>
    
    suspend fun searchExercisesByName(searchQuery: String): List<Exercise>
    
    fun searchExercisesByNameFlow(searchQuery: String): Flow<List<Exercise>>
    
    suspend fun updateExercise(exercise: Exercise)
    
    suspend fun updateAllExercises(exercises: List<Exercise>)
    
    suspend fun deleteExercise(exercise: Exercise)
    
    suspend fun deleteAllExercises(exercises: List<Exercise>)
    
    suspend fun deleteExerciseById(id: String)
    
    suspend fun deleteExercisesByEquipmentId(equipmentId: String)
    
    suspend fun deleteAllExercises()
    
    suspend fun getExerciseCount(): Int
    
    suspend fun getExerciseCountByEquipmentId(equipmentId: String): Int
} 