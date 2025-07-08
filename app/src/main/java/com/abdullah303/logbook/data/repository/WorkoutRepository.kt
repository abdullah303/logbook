package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    
    suspend fun insertWorkout(workout: Workout)
    
    suspend fun insertAllWorkouts(workoutsList: List<Workout>)
    
    suspend fun getWorkoutById(id: String): Workout?
    
    fun getWorkoutByIdFlow(id: String): Flow<Workout?>
    
    suspend fun getAllWorkouts(): List<Workout>
    
    fun getAllWorkoutsFlow(): Flow<List<Workout>>
    
    suspend fun getWorkoutsBySplitId(splitId: String): List<Workout>
    
    fun getWorkoutsBySplitIdFlow(splitId: String): Flow<List<Workout>>
    
    fun searchWorkoutsByName(searchQuery: String): Flow<List<Workout>>
    
    fun searchWorkoutsByNameInSplit(splitId: String, searchQuery: String): Flow<List<Workout>>
    
    suspend fun updateWorkout(workout: Workout)
    
    suspend fun updateAllWorkouts(workouts: List<Workout>)
    
    suspend fun deleteWorkout(workout: Workout)
    
    suspend fun deleteAllWorkouts(workouts: List<Workout>)
    
    suspend fun deleteWorkoutById(id: String)
    
    suspend fun deleteWorkoutsBySplitId(splitId: String)
    
    suspend fun deleteAllWorkouts()
    
    suspend fun getWorkoutsCount(): Int
    
    suspend fun getWorkoutsCountBySplitId(splitId: String): Int
    
    fun getWorkoutsCountBySplitIdFlow(splitId: String): Flow<Int>
    
    suspend fun getMaxPositionBySplitId(splitId: String): Int?
    
    fun getMaxPositionBySplitIdFlow(splitId: String): Flow<Int?>
} 