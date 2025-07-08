package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.WorkoutExercise
import kotlinx.coroutines.flow.Flow

interface WorkoutExerciseRepository {
    
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise)
    
    suspend fun insertAllWorkoutExercises(workoutExercisesList: List<WorkoutExercise>)
    
    suspend fun getWorkoutExerciseById(id: String): WorkoutExercise?
    
    fun getWorkoutExerciseByIdFlow(id: String): Flow<WorkoutExercise?>
    
    suspend fun getAllWorkoutExercises(): List<WorkoutExercise>
    
    fun getAllWorkoutExercisesFlow(): Flow<List<WorkoutExercise>>
    
    suspend fun getWorkoutExercisesByWorkoutId(workoutId: String): List<WorkoutExercise>
    
    fun getWorkoutExercisesByWorkoutIdFlow(workoutId: String): Flow<List<WorkoutExercise>>
    
    suspend fun getWorkoutExercisesByExerciseId(exerciseId: String): List<WorkoutExercise>
    
    fun getWorkoutExercisesByExerciseIdFlow(exerciseId: String): Flow<List<WorkoutExercise>>
    
    suspend fun getWorkoutExercisesBySupersetGroupId(supersetGroupId: String): List<WorkoutExercise>
    
    fun getWorkoutExercisesBySupersetGroupIdFlow(supersetGroupId: String): Flow<List<WorkoutExercise>>
    
    suspend fun getWorkoutExercisesWithoutSupersetGroup(): List<WorkoutExercise>
    
    fun getWorkoutExercisesWithoutSupersetGroupFlow(): Flow<List<WorkoutExercise>>
    
    suspend fun getWorkoutExercisesByWorkoutIdWithoutSupersetGroup(workoutId: String): List<WorkoutExercise>
    
    fun getWorkoutExercisesByWorkoutIdWithoutSupersetGroupFlow(workoutId: String): Flow<List<WorkoutExercise>>
    
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise)
    
    suspend fun updateAllWorkoutExercises(workoutExercises: List<WorkoutExercise>)
    
    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise)
    
    suspend fun deleteAllWorkoutExercises(workoutExercises: List<WorkoutExercise>)
    
    suspend fun deleteWorkoutExerciseById(id: String)
    
    suspend fun deleteWorkoutExercisesByWorkoutId(workoutId: String)
    
    suspend fun deleteWorkoutExercisesByExerciseId(exerciseId: String)
    
    suspend fun deleteWorkoutExercisesBySupersetGroupId(supersetGroupId: String)
    
    suspend fun deleteAllWorkoutExercises()
    
    suspend fun getWorkoutExercisesCount(): Int
    
    suspend fun getWorkoutExercisesCountByWorkoutId(workoutId: String): Int
    
    fun getWorkoutExercisesCountByWorkoutIdFlow(workoutId: String): Flow<Int>
    
    suspend fun getWorkoutExercisesCountByExerciseId(exerciseId: String): Int
    
    suspend fun getWorkoutExercisesCountBySupersetGroupId(supersetGroupId: String): Int
    
    suspend fun getMaxPositionByWorkoutId(workoutId: String): Int?
    
    fun getMaxPositionByWorkoutIdFlow(workoutId: String): Flow<Int?>
    
    suspend fun getMaxPositionBySupersetGroupId(supersetGroupId: String): Int?
    
    fun getMaxPositionBySupersetGroupIdFlow(supersetGroupId: String): Flow<Int?>
} 