package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.WorkoutExerciseSnapshot
import kotlinx.coroutines.flow.Flow

interface WorkoutExerciseSnapshotRepository {
    
    suspend fun insertWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    suspend fun insertAllWorkoutExerciseSnapshots(workoutExerciseSnapshotsList: List<WorkoutExerciseSnapshot>)
    
    suspend fun getWorkoutExerciseSnapshotById(id: String): WorkoutExerciseSnapshot?
    
    fun getWorkoutExerciseSnapshotByIdFlow(id: String): Flow<WorkoutExerciseSnapshot?>
    
    suspend fun getAllWorkoutExerciseSnapshots(): List<WorkoutExerciseSnapshot>
    
    fun getAllWorkoutExerciseSnapshotsFlow(): Flow<List<WorkoutExerciseSnapshot>>
    
    suspend fun getWorkoutExerciseSnapshotsByLoggedWorkoutId(loggedWorkoutId: String): List<WorkoutExerciseSnapshot>
    
    fun getWorkoutExerciseSnapshotsByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<WorkoutExerciseSnapshot>>
    
    suspend fun getWorkoutExerciseSnapshotsByWorkoutExerciseId(workoutExerciseId: String): List<WorkoutExerciseSnapshot>
    
    fun getWorkoutExerciseSnapshotsByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<WorkoutExerciseSnapshot>>
    
    suspend fun updateWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    suspend fun updateAllWorkoutExerciseSnapshots(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>)
    
    suspend fun deleteWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    suspend fun deleteAllWorkoutExerciseSnapshots(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>)
    
    suspend fun deleteWorkoutExerciseSnapshotById(id: String)
    
    suspend fun deleteWorkoutExerciseSnapshotsByLoggedWorkoutId(loggedWorkoutId: String)
    
    suspend fun deleteWorkoutExerciseSnapshotsByWorkoutExerciseId(workoutExerciseId: String)
    
    suspend fun deleteAllWorkoutExerciseSnapshots()
    
    suspend fun getWorkoutExerciseSnapshotsCount(): Int
    
    suspend fun getWorkoutExerciseSnapshotsCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    fun getWorkoutExerciseSnapshotsCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    suspend fun getWorkoutExerciseSnapshotsCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    suspend fun getMaxPositionByLoggedWorkoutId(loggedWorkoutId: String): Int?
    
    fun getMaxPositionByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int?>
} 