package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.LoggedSet
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedSetDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loggedSet: LoggedSet)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(loggedSets: List<LoggedSet>)
    
    @Query("SELECT * FROM logged_sets WHERE id = :id")
    suspend fun getById(id: String): LoggedSet?
    
    @Query("SELECT * FROM logged_sets WHERE id = :id")
    fun getByIdFlow(id: String): Flow<LoggedSet?>
    
    @Query("SELECT * FROM logged_sets ORDER BY set_number ASC")
    suspend fun getAll(): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets ORDER BY set_number ASC")
    fun getAllFlow(): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId ORDER BY set_number ASC")
    suspend fun getByLoggedWorkoutId(loggedWorkoutId: String): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId ORDER BY set_number ASC")
    fun getByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE exercise_snapshot_id = :exerciseSnapshotId ORDER BY set_number ASC")
    suspend fun getByExerciseSnapshotId(exerciseSnapshotId: String): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE exercise_snapshot_id = :exerciseSnapshotId ORDER BY set_number ASC")
    fun getByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId ORDER BY set_number ASC")
    suspend fun getByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId ORDER BY set_number ASC")
    fun getByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE set_number = :setNumber ORDER BY id ASC")
    suspend fun getBySetNumber(setNumber: Int): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE set_number = :setNumber ORDER BY id ASC")
    fun getBySetNumberFlow(setNumber: Int): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE weight IS NOT NULL ORDER BY weight DESC")
    suspend fun getByWeightNotNull(): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE weight IS NOT NULL ORDER BY weight DESC")
    fun getByWeightNotNullFlow(): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE reps IS NOT NULL ORDER BY reps DESC")
    suspend fun getByRepsNotNull(): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE reps IS NOT NULL ORDER BY reps DESC")
    fun getByRepsNotNullFlow(): Flow<List<LoggedSet>>
    
    @Query("SELECT * FROM logged_sets WHERE rir IS NOT NULL ORDER BY rir ASC")
    suspend fun getByRirNotNull(): List<LoggedSet>
    
    @Query("SELECT * FROM logged_sets WHERE rir IS NOT NULL ORDER BY rir ASC")
    fun getByRirNotNullFlow(): Flow<List<LoggedSet>>
    
    @Update
    suspend fun update(loggedSet: LoggedSet)
    
    @Update
    suspend fun updateAll(loggedSets: List<LoggedSet>)
    
    @Delete
    suspend fun delete(loggedSet: LoggedSet)
    
    @Delete
    suspend fun deleteAll(loggedSets: List<LoggedSet>)
    
    @Query("DELETE FROM logged_sets WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun deleteByLoggedWorkoutId(loggedWorkoutId: String)
    
    @Query("DELETE FROM logged_sets WHERE exercise_snapshot_id = :exerciseSnapshotId")
    suspend fun deleteByExerciseSnapshotId(exerciseSnapshotId: String)
    
    @Query("DELETE FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId")
    suspend fun deleteByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String)
    
    @Query("DELETE FROM logged_sets")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM logged_sets")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM logged_sets")
    fun getCountFlow(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun getCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId")
    fun getCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE exercise_snapshot_id = :exerciseSnapshotId")
    suspend fun getCountByExerciseSnapshotId(exerciseSnapshotId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE exercise_snapshot_id = :exerciseSnapshotId")
    fun getCountByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId")
    suspend fun getCountByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId")
    fun getCountByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int>
    
    @Query("SELECT MAX(set_number) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId")
    suspend fun getMaxSetNumber(loggedWorkoutId: String, exerciseSnapshotId: String): Int?
    
    @Query("SELECT MAX(set_number) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId")
    fun getMaxSetNumberFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int?>
    
    @Query("SELECT AVG(weight) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND weight IS NOT NULL")
    suspend fun getAverageWeight(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    @Query("SELECT AVG(weight) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND weight IS NOT NULL")
    fun getAverageWeightFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
    
    @Query("SELECT AVG(reps) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND reps IS NOT NULL")
    suspend fun getAverageReps(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    @Query("SELECT AVG(reps) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND reps IS NOT NULL")
    fun getAverageRepsFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
    
    @Query("SELECT AVG(rir) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND rir IS NOT NULL")
    suspend fun getAverageRir(loggedWorkoutId: String, exerciseSnapshotId: String): Double?
    
    @Query("SELECT AVG(rir) FROM logged_sets WHERE logged_workout_id = :loggedWorkoutId AND exercise_snapshot_id = :exerciseSnapshotId AND rir IS NOT NULL")
    fun getAverageRirFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?>
} 