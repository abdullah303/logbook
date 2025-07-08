package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.LoggedWorkout
import com.abdullah303.logbook.data.model.LoggedWorkoutStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface LoggedWorkoutDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loggedWorkout: LoggedWorkout)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(loggedWorkouts: List<LoggedWorkout>)
    
    @Query("SELECT * FROM logged_workouts WHERE id = :id")
    suspend fun getById(id: String): LoggedWorkout?
    
    @Query("SELECT * FROM logged_workouts WHERE id = :id")
    fun getByIdFlow(id: String): Flow<LoggedWorkout?>
    
    @Query("SELECT * FROM logged_workouts ORDER BY logged_at DESC")
    suspend fun getAll(): List<LoggedWorkout>
    
    @Query("SELECT * FROM logged_workouts ORDER BY logged_at DESC")
    fun getAllFlow(): Flow<List<LoggedWorkout>>
    
    @Query("SELECT * FROM logged_workouts WHERE logged_split_id = :loggedSplitId ORDER BY logged_at DESC")
    suspend fun getByLoggedSplitId(loggedSplitId: String): List<LoggedWorkout>
    
    @Query("SELECT * FROM logged_workouts WHERE logged_split_id = :loggedSplitId ORDER BY logged_at DESC")
    fun getByLoggedSplitIdFlow(loggedSplitId: String): Flow<List<LoggedWorkout>>
    
    @Query("SELECT * FROM logged_workouts WHERE workout_id = :workoutId ORDER BY logged_at DESC")
    suspend fun getByWorkoutId(workoutId: String): List<LoggedWorkout>
    
    @Query("SELECT * FROM logged_workouts WHERE workout_id = :workoutId ORDER BY logged_at DESC")
    fun getByWorkoutIdFlow(workoutId: String): Flow<List<LoggedWorkout>>
    
    @Query("SELECT * FROM logged_workouts WHERE status = :status ORDER BY logged_at DESC")
    suspend fun getByStatus(status: LoggedWorkoutStatus): List<LoggedWorkout>
    
    @Query("SELECT * FROM logged_workouts WHERE status = :status ORDER BY logged_at DESC")
    fun getByStatusFlow(status: LoggedWorkoutStatus): Flow<List<LoggedWorkout>>
    
    @Query("SELECT * FROM logged_workouts WHERE logged_at BETWEEN :startDate AND :endDate ORDER BY logged_at DESC")
    suspend fun getByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<LoggedWorkout>
    
    @Query("SELECT * FROM logged_workouts WHERE logged_at BETWEEN :startDate AND :endDate ORDER BY logged_at DESC")
    fun getByDateRangeFlow(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<LoggedWorkout>>
    
    @Query("SELECT * FROM logged_workouts WHERE logged_split_id = :loggedSplitId AND workout_id = :workoutId")
    suspend fun getByLoggedSplitIdAndWorkoutId(loggedSplitId: String, workoutId: String): LoggedWorkout?
    
    @Query("SELECT * FROM logged_workouts WHERE logged_split_id = :loggedSplitId AND workout_id = :workoutId")
    fun getByLoggedSplitIdAndWorkoutIdFlow(loggedSplitId: String, workoutId: String): Flow<LoggedWorkout?>
    
    @Update
    suspend fun update(loggedWorkout: LoggedWorkout)
    
    @Update
    suspend fun updateAll(loggedWorkouts: List<LoggedWorkout>)
    
    @Delete
    suspend fun delete(loggedWorkout: LoggedWorkout)
    
    @Delete
    suspend fun deleteAll(loggedWorkouts: List<LoggedWorkout>)
    
    @Query("DELETE FROM logged_workouts WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM logged_workouts WHERE logged_split_id = :loggedSplitId")
    suspend fun deleteByLoggedSplitId(loggedSplitId: String)
    
    @Query("DELETE FROM logged_workouts WHERE workout_id = :workoutId")
    suspend fun deleteByWorkoutId(workoutId: String)
    
    @Query("DELETE FROM logged_workouts")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM logged_workouts")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE logged_split_id = :loggedSplitId")
    suspend fun getCountByLoggedSplitId(loggedSplitId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE logged_split_id = :loggedSplitId")
    fun getCountByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE workout_id = :workoutId")
    suspend fun getCountByWorkoutId(workoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE workout_id = :workoutId")
    fun getCountByWorkoutIdFlow(workoutId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE status = :status")
    suspend fun getCountByStatus(status: LoggedWorkoutStatus): Int
    
    @Query("SELECT COUNT(*) FROM logged_workouts WHERE status = :status")
    fun getCountByStatusFlow(status: LoggedWorkoutStatus): Flow<Int>
    
    @Query("SELECT SUM(duration_seconds) FROM logged_workouts WHERE logged_split_id = :loggedSplitId AND duration_seconds IS NOT NULL")
    suspend fun getTotalDurationByLoggedSplitId(loggedSplitId: String): Int?
    
    @Query("SELECT SUM(duration_seconds) FROM logged_workouts WHERE logged_split_id = :loggedSplitId AND duration_seconds IS NOT NULL")
    fun getTotalDurationByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int?>
    
    @Query("SELECT AVG(duration_seconds) FROM logged_workouts WHERE workout_id = :workoutId AND duration_seconds IS NOT NULL")
    suspend fun getAverageDurationByWorkoutId(workoutId: String): Double?
    
    @Query("SELECT AVG(duration_seconds) FROM logged_workouts WHERE workout_id = :workoutId AND duration_seconds IS NOT NULL")
    fun getAverageDurationByWorkoutIdFlow(workoutId: String): Flow<Double?>
} 