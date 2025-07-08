package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.LoggedWorkout
import com.abdullah303.logbook.data.model.LoggedWorkoutStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface LoggedWorkoutRepository {
    
    suspend fun insertLoggedWorkout(loggedWorkout: LoggedWorkout)
    
    suspend fun insertAllLoggedWorkouts(loggedWorkoutsList: List<LoggedWorkout>)
    
    suspend fun getLoggedWorkoutById(id: String): LoggedWorkout?
    
    fun getLoggedWorkoutByIdFlow(id: String): Flow<LoggedWorkout?>
    
    suspend fun getAllLoggedWorkouts(): List<LoggedWorkout>
    
    fun getAllLoggedWorkoutsFlow(): Flow<List<LoggedWorkout>>
    
    suspend fun getLoggedWorkoutsByLoggedSplitId(loggedSplitId: String): List<LoggedWorkout>
    
    fun getLoggedWorkoutsByLoggedSplitIdFlow(loggedSplitId: String): Flow<List<LoggedWorkout>>
    
    suspend fun getLoggedWorkoutsByWorkoutId(workoutId: String): List<LoggedWorkout>
    
    fun getLoggedWorkoutsByWorkoutIdFlow(workoutId: String): Flow<List<LoggedWorkout>>
    
    suspend fun getLoggedWorkoutsByStatus(status: LoggedWorkoutStatus): List<LoggedWorkout>
    
    fun getLoggedWorkoutsByStatusFlow(status: LoggedWorkoutStatus): Flow<List<LoggedWorkout>>
    
    suspend fun getLoggedWorkoutsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<LoggedWorkout>
    
    fun getLoggedWorkoutsByDateRangeFlow(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<LoggedWorkout>>
    
    suspend fun getLoggedWorkoutByLoggedSplitIdAndWorkoutId(loggedSplitId: String, workoutId: String): LoggedWorkout?
    
    fun getLoggedWorkoutByLoggedSplitIdAndWorkoutIdFlow(loggedSplitId: String, workoutId: String): Flow<LoggedWorkout?>
    
    suspend fun updateLoggedWorkout(loggedWorkout: LoggedWorkout)
    
    suspend fun updateAllLoggedWorkouts(loggedWorkouts: List<LoggedWorkout>)
    
    suspend fun deleteLoggedWorkout(loggedWorkout: LoggedWorkout)
    
    suspend fun deleteAllLoggedWorkouts(loggedWorkouts: List<LoggedWorkout>)
    
    suspend fun deleteLoggedWorkoutById(id: String)
    
    suspend fun deleteLoggedWorkoutsByLoggedSplitId(loggedSplitId: String)
    
    suspend fun deleteLoggedWorkoutsByWorkoutId(workoutId: String)
    
    suspend fun deleteAllLoggedWorkouts()
    
    suspend fun getLoggedWorkoutsCount(): Int
    
    suspend fun getLoggedWorkoutsCountByLoggedSplitId(loggedSplitId: String): Int
    
    fun getLoggedWorkoutsCountByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int>
    
    suspend fun getLoggedWorkoutsCountByWorkoutId(workoutId: String): Int
    
    fun getLoggedWorkoutsCountByWorkoutIdFlow(workoutId: String): Flow<Int>
    
    suspend fun getLoggedWorkoutsCountByStatus(status: LoggedWorkoutStatus): Int
    
    fun getLoggedWorkoutsCountByStatusFlow(status: LoggedWorkoutStatus): Flow<Int>
    
    suspend fun getTotalDurationByLoggedSplitId(loggedSplitId: String): Int?
    
    fun getTotalDurationByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int?>
    
    suspend fun getAverageDurationByWorkoutId(workoutId: String): Double?
    
    fun getAverageDurationByWorkoutIdFlow(workoutId: String): Flow<Double?>
} 