package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.ExerciseNotes
import kotlinx.coroutines.flow.Flow

interface ExerciseNotesRepository {
    
    suspend fun insertExerciseNotes(exerciseNotes: ExerciseNotes)
    
    suspend fun insertAllExerciseNotes(exerciseNotes: List<ExerciseNotes>)
    
    suspend fun getExerciseNotesById(id: String): ExerciseNotes?
    
    fun getExerciseNotesByIdFlow(id: String): Flow<ExerciseNotes?>
    
    suspend fun getAllExerciseNotes(): List<ExerciseNotes>
    
    fun getAllExerciseNotesFlow(): Flow<List<ExerciseNotes>>
    
    suspend fun getExerciseNotesByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes>
    
    fun getExerciseNotesByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>>
    
    suspend fun getExerciseNotesByLoggedWorkoutId(loggedWorkoutId: String): List<ExerciseNotes>
    
    fun getExerciseNotesByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<ExerciseNotes>>
    
    suspend fun getExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String): List<ExerciseNotes>
    
    fun getExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutIdFlow(workoutExerciseId: String, loggedWorkoutId: String): Flow<List<ExerciseNotes>>
    
    suspend fun getTemplateExerciseNotes(): List<ExerciseNotes>
    
    fun getTemplateExerciseNotesFlow(): Flow<List<ExerciseNotes>>
    
    suspend fun getTemplateExerciseNotesByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes>
    
    fun getTemplateExerciseNotesByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>>
    
    suspend fun updateExerciseNotes(exerciseNotes: ExerciseNotes)
    
    suspend fun updateAllExerciseNotes(exerciseNotes: List<ExerciseNotes>)
    
    suspend fun deleteExerciseNotes(exerciseNotes: ExerciseNotes)
    
    suspend fun deleteAllExerciseNotes(exerciseNotes: List<ExerciseNotes>)
    
    suspend fun deleteExerciseNotesById(id: String)
    
    suspend fun deleteExerciseNotesByWorkoutExerciseId(workoutExerciseId: String)
    
    suspend fun deleteExerciseNotesByLoggedWorkoutId(loggedWorkoutId: String)
    
    suspend fun deleteExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String)
    
    suspend fun deleteAllExerciseNotes()
    
    suspend fun getExerciseNotesCount(): Int
    
    suspend fun getExerciseNotesCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    fun getExerciseNotesCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int>
    
    suspend fun getExerciseNotesCountByLoggedWorkoutId(loggedWorkoutId: String): Int
    
    fun getExerciseNotesCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int>
    
    suspend fun getTemplateExerciseNotesCountByWorkoutExerciseId(workoutExerciseId: String): Int
    
    fun getTemplateExerciseNotesCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int>
} 