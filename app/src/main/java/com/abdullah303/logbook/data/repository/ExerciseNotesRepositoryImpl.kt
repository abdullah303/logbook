package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.ExerciseNotesDao
import com.abdullah303.logbook.data.local.entity.ExerciseNotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseNotesRepositoryImpl @Inject constructor(
    private val exerciseNotesDao: ExerciseNotesDao
) : ExerciseNotesRepository {
    
    override suspend fun insertExerciseNotes(exerciseNotes: ExerciseNotes) {
        exerciseNotesDao.insert(exerciseNotes)
    }
    
    override suspend fun insertAllExerciseNotes(exerciseNotes: List<ExerciseNotes>) {
        exerciseNotesDao.insertAll(exerciseNotes)
    }
    
    override suspend fun getExerciseNotesById(id: String): ExerciseNotes? {
        return exerciseNotesDao.getById(id)
    }
    
    override fun getExerciseNotesByIdFlow(id: String): Flow<ExerciseNotes?> {
        return exerciseNotesDao.getByIdFlow(id)
    }
    
    override suspend fun getAllExerciseNotes(): List<ExerciseNotes> {
        return exerciseNotesDao.getAll()
    }
    
    override fun getAllExerciseNotesFlow(): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getAllFlow()
    }
    
    override suspend fun getExerciseNotesByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes> {
        return exerciseNotesDao.getByWorkoutExerciseId(workoutExerciseId)
    }
    
    override fun getExerciseNotesByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getByWorkoutExerciseIdFlow(workoutExerciseId)
    }
    
    override suspend fun getExerciseNotesByLoggedWorkoutId(loggedWorkoutId: String): List<ExerciseNotes> {
        return exerciseNotesDao.getByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getExerciseNotesByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String): List<ExerciseNotes> {
        return exerciseNotesDao.getByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId, loggedWorkoutId)
    }
    
    override fun getExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutIdFlow(workoutExerciseId: String, loggedWorkoutId: String): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getByWorkoutExerciseIdAndLoggedWorkoutIdFlow(workoutExerciseId, loggedWorkoutId)
    }
    
    override suspend fun getTemplateExerciseNotes(): List<ExerciseNotes> {
        return exerciseNotesDao.getTemplateNotes()
    }
    
    override fun getTemplateExerciseNotesFlow(): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getTemplateNotesFlow()
    }
    
    override suspend fun getTemplateExerciseNotesByWorkoutExerciseId(workoutExerciseId: String): List<ExerciseNotes> {
        return exerciseNotesDao.getTemplateNotesByWorkoutExerciseId(workoutExerciseId)
    }
    
    override fun getTemplateExerciseNotesByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<ExerciseNotes>> {
        return exerciseNotesDao.getTemplateNotesByWorkoutExerciseIdFlow(workoutExerciseId)
    }
    
    override suspend fun updateExerciseNotes(exerciseNotes: ExerciseNotes) {
        exerciseNotesDao.update(exerciseNotes)
    }
    
    override suspend fun updateAllExerciseNotes(exerciseNotes: List<ExerciseNotes>) {
        exerciseNotesDao.updateAll(exerciseNotes)
    }
    
    override suspend fun deleteExerciseNotes(exerciseNotes: ExerciseNotes) {
        exerciseNotesDao.delete(exerciseNotes)
    }
    
    override suspend fun deleteAllExerciseNotes(exerciseNotes: List<ExerciseNotes>) {
        exerciseNotesDao.deleteAll(exerciseNotes)
    }
    
    override suspend fun deleteExerciseNotesById(id: String) {
        exerciseNotesDao.deleteById(id)
    }
    
    override suspend fun deleteExerciseNotesByWorkoutExerciseId(workoutExerciseId: String) {
        exerciseNotesDao.deleteByWorkoutExerciseId(workoutExerciseId)
    }
    
    override suspend fun deleteExerciseNotesByLoggedWorkoutId(loggedWorkoutId: String) {
        exerciseNotesDao.deleteByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override suspend fun deleteExerciseNotesByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId: String, loggedWorkoutId: String) {
        exerciseNotesDao.deleteByWorkoutExerciseIdAndLoggedWorkoutId(workoutExerciseId, loggedWorkoutId)
    }
    
    override suspend fun deleteAllExerciseNotes() {
        exerciseNotesDao.deleteAll()
    }
    
    override suspend fun getExerciseNotesCount(): Int {
        return exerciseNotesDao.getCount()
    }
    
    override suspend fun getExerciseNotesCountByWorkoutExerciseId(workoutExerciseId: String): Int {
        return exerciseNotesDao.getCountByWorkoutExerciseId(workoutExerciseId)
    }
    
    override fun getExerciseNotesCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int> {
        return exerciseNotesDao.getCountByWorkoutExerciseIdFlow(workoutExerciseId)
    }
    
    override suspend fun getExerciseNotesCountByLoggedWorkoutId(loggedWorkoutId: String): Int {
        return exerciseNotesDao.getCountByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getExerciseNotesCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int> {
        return exerciseNotesDao.getCountByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getTemplateExerciseNotesCountByWorkoutExerciseId(workoutExerciseId: String): Int {
        return exerciseNotesDao.getTemplateNotesCountByWorkoutExerciseId(workoutExerciseId)
    }
    
    override fun getTemplateExerciseNotesCountByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<Int> {
        return exerciseNotesDao.getTemplateNotesCountByWorkoutExerciseIdFlow(workoutExerciseId)
    }
} 