package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.WorkoutExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutExercise: WorkoutExercise)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(workoutExercises: List<WorkoutExercise>)
    
    @Query("SELECT * FROM workout_exercises WHERE id = :id")
    suspend fun getById(id: String): WorkoutExercise?
    
    @Query("SELECT * FROM workout_exercises WHERE id = :id")
    fun getByIdFlow(id: String): Flow<WorkoutExercise?>
    
    @Query("SELECT * FROM workout_exercises ORDER BY position ASC")
    suspend fun getAll(): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises ORDER BY position ASC")
    fun getAllFlow(): Flow<List<WorkoutExercise>>
    
    @Query("SELECT * FROM workout_exercises WHERE workout_id = :workoutId ORDER BY position ASC")
    suspend fun getByWorkoutId(workoutId: String): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises WHERE workout_id = :workoutId ORDER BY position ASC")
    fun getByWorkoutIdFlow(workoutId: String): Flow<List<WorkoutExercise>>
    
    @Query("SELECT * FROM workout_exercises WHERE exercise_id = :exerciseId ORDER BY position ASC")
    suspend fun getByExerciseId(exerciseId: String): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises WHERE exercise_id = :exerciseId ORDER BY position ASC")
    fun getByExerciseIdFlow(exerciseId: String): Flow<List<WorkoutExercise>>
    
    @Query("SELECT * FROM workout_exercises WHERE superset_group_id = :supersetGroupId ORDER BY position ASC")
    suspend fun getBySupersetGroupId(supersetGroupId: String): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises WHERE superset_group_id = :supersetGroupId ORDER BY position ASC")
    fun getBySupersetGroupIdFlow(supersetGroupId: String): Flow<List<WorkoutExercise>>
    
    @Query("SELECT * FROM workout_exercises WHERE superset_group_id IS NULL ORDER BY position ASC")
    suspend fun getWithoutSupersetGroup(): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises WHERE superset_group_id IS NULL ORDER BY position ASC")
    fun getWithoutSupersetGroupFlow(): Flow<List<WorkoutExercise>>
    
    @Query("SELECT * FROM workout_exercises WHERE workout_id = :workoutId AND superset_group_id IS NULL ORDER BY position ASC")
    suspend fun getByWorkoutIdWithoutSupersetGroup(workoutId: String): List<WorkoutExercise>
    
    @Query("SELECT * FROM workout_exercises WHERE workout_id = :workoutId AND superset_group_id IS NULL ORDER BY position ASC")
    fun getByWorkoutIdWithoutSupersetGroupFlow(workoutId: String): Flow<List<WorkoutExercise>>
    
    @Update
    suspend fun update(workoutExercise: WorkoutExercise)
    
    @Update
    suspend fun updateAll(workoutExercises: List<WorkoutExercise>)
    
    @Delete
    suspend fun delete(workoutExercise: WorkoutExercise)
    
    @Delete
    suspend fun deleteAll(workoutExercises: List<WorkoutExercise>)
    
    @Query("DELETE FROM workout_exercises WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM workout_exercises WHERE workout_id = :workoutId")
    suspend fun deleteByWorkoutId(workoutId: String)
    
    @Query("DELETE FROM workout_exercises WHERE exercise_id = :exerciseId")
    suspend fun deleteByExerciseId(exerciseId: String)
    
    @Query("DELETE FROM workout_exercises WHERE superset_group_id = :supersetGroupId")
    suspend fun deleteBySupersetGroupId(supersetGroupId: String)
    
    @Query("DELETE FROM workout_exercises")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM workout_exercises")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM workout_exercises WHERE workout_id = :workoutId")
    suspend fun getCountByWorkoutId(workoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM workout_exercises WHERE workout_id = :workoutId")
    fun getCountByWorkoutIdFlow(workoutId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM workout_exercises WHERE exercise_id = :exerciseId")
    suspend fun getCountByExerciseId(exerciseId: String): Int
    
    @Query("SELECT COUNT(*) FROM workout_exercises WHERE superset_group_id = :supersetGroupId")
    suspend fun getCountBySupersetGroupId(supersetGroupId: String): Int
    
    @Query("SELECT MAX(position) FROM workout_exercises WHERE workout_id = :workoutId")
    suspend fun getMaxPositionByWorkoutId(workoutId: String): Int?
    
    @Query("SELECT MAX(position) FROM workout_exercises WHERE workout_id = :workoutId")
    fun getMaxPositionByWorkoutIdFlow(workoutId: String): Flow<Int?>
    
    @Query("SELECT MAX(position) FROM workout_exercises WHERE superset_group_id = :supersetGroupId")
    suspend fun getMaxPositionBySupersetGroupId(supersetGroupId: String): Int?
    
    @Query("SELECT MAX(position) FROM workout_exercises WHERE superset_group_id = :supersetGroupId")
    fun getMaxPositionBySupersetGroupIdFlow(supersetGroupId: String): Flow<Int?>
} 