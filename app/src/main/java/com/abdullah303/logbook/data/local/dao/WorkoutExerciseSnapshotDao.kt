package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.WorkoutExerciseSnapshot
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseSnapshotDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>)
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE id = :id")
    suspend fun getById(id: String): WorkoutExerciseSnapshot?
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE id = :id")
    fun getByIdFlow(id: String): Flow<WorkoutExerciseSnapshot?>
    
    @Query("SELECT * FROM workout_exercise_snapshots ORDER BY position ASC")
    suspend fun getAll(): List<WorkoutExerciseSnapshot>
    
    @Query("SELECT * FROM workout_exercise_snapshots ORDER BY position ASC")
    fun getAllFlow(): Flow<List<WorkoutExerciseSnapshot>>
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId ORDER BY position ASC")
    suspend fun getByLoggedWorkoutId(loggedWorkoutId: String): List<WorkoutExerciseSnapshot>
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId ORDER BY position ASC")
    fun getByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<WorkoutExerciseSnapshot>>
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE workout_exercise_id = :workoutExerciseId ORDER BY position ASC")
    suspend fun getByWorkoutExerciseId(workoutExerciseId: String): List<WorkoutExerciseSnapshot>
    
    @Query("SELECT * FROM workout_exercise_snapshots WHERE workout_exercise_id = :workoutExerciseId ORDER BY position ASC")
    fun getByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<WorkoutExerciseSnapshot>>
    
    @Update
    suspend fun update(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    @Update
    suspend fun updateAll(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>)
    
    @Delete
    suspend fun delete(workoutExerciseSnapshot: WorkoutExerciseSnapshot)
    
    @Delete
    suspend fun deleteAll(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>)
    
    @Query("DELETE FROM workout_exercise_snapshots WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun deleteByLoggedWorkoutId(loggedWorkoutId: String)
    
    @Query("DELETE FROM workout_exercise_snapshots WHERE workout_exercise_id = :workoutExerciseId")
    suspend fun deleteByWorkoutExerciseId(workoutExerciseId: String)
    
    @Query("DELETE FROM workout_exercise_snapshots")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM workout_exercise_snapshots")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun getCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId")
    fun getCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM workout_exercise_snapshots WHERE workout_exercise_id = :workoutExerciseId")
    suspend fun getCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    @Query("SELECT MAX(position) FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun getMaxPositionByLoggedWorkoutId(loggedWorkoutId: String): Int?
    
    @Query("SELECT MAX(position) FROM workout_exercise_snapshots WHERE logged_workout_id = :loggedWorkoutId")
    fun getMaxPositionByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int?>
} 