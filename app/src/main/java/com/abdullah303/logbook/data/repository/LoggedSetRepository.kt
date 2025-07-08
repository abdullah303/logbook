package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.LoggedSet
import kotlinx.coroutines.flow.Flow

interface LoggedSetRepository {
    
    suspend fun insertLoggedSet(loggedSet: LoggedSet)
    
    suspend fun insertAllLoggedSets(loggedSetsList: List<LoggedSet>)
    
    suspend fun getLoggedSetById(id: String): LoggedSet?
    
    fun getLoggedSetByIdFlow(id: String): Flow<LoggedSet?>
    
    suspend fun getAllLoggedSets(): List<LoggedSet>
    
    fun getAllLoggedSetsFlow(): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByLoggedWorkoutId(loggedWorkoutId: String): List<LoggedSet>
    
    fun getLoggedSetsByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByExerciseSnapshotId(exerciseSnapshotId: String): List<LoggedSet>
    
    fun getLoggedSetsByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): List<LoggedSet>
    
    fun getLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsBySetNumber(setNumber: Int): List<LoggedSet>
    
    fun getLoggedSetsBySetNumberFlow(setNumber: Int): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByWeightNotNull(): List<LoggedSet>
    
    fun getLoggedSetsByWeightNotNullFlow(): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByRepsNotNull(): List<LoggedSet>
    
    fun getLoggedSetsByRepsNotNullFlow(): Flow<List<LoggedSet>>
    
    suspend fun getLoggedSetsByRirNotNull(): List<LoggedSet>
    
    fun getLoggedSetsByRirNotNullFlow(): Flow<List<LoggedSet>>
    
    suspend fun updateLoggedSet(loggedSet: LoggedSet)
    
    suspend fun updateAllLoggedSets(loggedSets: List<LoggedSet>)
    
    suspend fun deleteLoggedSet(loggedSet: LoggedSet)
    
    suspend fun deleteAllLoggedSets(loggedSets: List<LoggedSet>)
    
    suspend fun deleteLoggedSetById(id: String)
    
    suspend fun deleteLoggedSetsByLoggedWorkoutId(loggedWorkoutId: String)
    
    suspend fun deleteLoggedSetsByExerciseSnapshotId(exerciseSnapshotId: String)
    
    suspend fun deleteLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String)
    
    suspend fun deleteAllLoggedSets()
    
    suspend fun getLoggedSetsCount(): Int
    
    fun getLoggedSetsCountFlow(): Flow<Int>
    
    suspend fun getLoggedSetsCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    fun getLoggedSetsCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    suspend fun getLoggedSetsCountByExerciseSnapshotId(exerciseSnapshotId: String): Int
    
    fun getLoggedSetsCountByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<Int>
    
    suspend fun getLoggedSetsCountByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): Int
    
    fun getLoggedSetsCountByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int>
    
    suspend fun getMaxSetNumber(loggedWorkoutId: String, exerciseSnapshotId: String): Int?
    
    fun getMaxSetNumberFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int?>
    
    suspend fun getAverageWeight(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    fun getAverageWeightFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
    
    suspend fun getAverageReps(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    fun getAverageRepsFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
    
    suspend fun getAverageRir(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    fun getAverageRirFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
} 