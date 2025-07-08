package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.ExerciseNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseNotesDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseNotes: ExerciseNotes)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseNotes: List<ExerciseNotes>)
    
    @Query("SELECT * FROM exercise_notes WHERE id = :id")
    suspend fun getById(id: String): ExerciseNotes?
    
    @Query("SELECT * FROM exercise_notes WHERE id = :id")
    fun getByIdFlow(id: String): Flow<ExerciseNotes?>
    
    @Query("SELECT * FROM exercise_notes ORDER BY id ASC")
    suspend fun getAll(): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes ORDER BY id ASC")
    fun getAllFlow(): Flow<List<ExerciseNotes>>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId")
    suspend fun getByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId")
    fun getByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>>
    
    @Query("SELECT * FROM exercise_notes WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun getByLoggedWorkoutId(loggedWorkoutId: String): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes WHERE logged_workout_id = :loggedWorkoutId")
    fun getByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<ExerciseNotes>>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id = :loggedWorkoutId")
    suspend fun getByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id = :loggedWorkoutId")
    fun getByWorkoutExerciseIdAndLoggedWorkoutIdFlow(workoutExerciseId: String, loggedWorkoutId: String): Flow<List<ExerciseNotes>>
    
    @Query("SELECT * FROM exercise_notes WHERE logged_workout_id IS NULL")
    suspend fun getTemplateNotes(): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes WHERE logged_workout_id IS NULL")
    fun getTemplateNotesFlow(): Flow<List<ExerciseNotes>>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id IS NULL")
    suspend fun getTemplateNotesByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes>
    
    @Query("SELECT * FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id IS NULL")
    fun getTemplateNotesByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>>
    
    @Update
    suspend fun update(exerciseNotes: ExerciseNotes)
    
    @Update
    suspend fun updateAll(exerciseNotes: List<ExerciseNotes>)
    
    @Delete
    suspend fun delete(exerciseNotes: ExerciseNotes)
    
    @Delete
    suspend fun deleteAll(exerciseNotes: List<ExerciseNotes>)
    
    @Query("DELETE FROM exercise_notes WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId")
    suspend fun deleteByWorkoutExerciseId(workoutExerciseId: String)
    
    @Query("DELETE FROM exercise_notes WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun deleteByLoggedWorkoutId(loggedWorkoutId: String)
    
    @Query("DELETE FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id = :loggedWorkoutId")
    suspend fun deleteByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String)
    
    @Query("DELETE FROM exercise_notes")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM exercise_notes")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId")
    suspend fun getCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId")
    fun getCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE logged_workout_id = :loggedWorkoutId")
    suspend fun getCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE logged_workout_id = :loggedWorkoutId")
    fun getCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id IS NULL")
    suspend fun getTemplateNotesCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    @Query("SELECT COUNT(*) FROM exercise_notes WHERE workout_exercise_id = :workoutExerciseId AND logged_workout_id IS NULL")
    fun getTemplateNotesCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int>
} 