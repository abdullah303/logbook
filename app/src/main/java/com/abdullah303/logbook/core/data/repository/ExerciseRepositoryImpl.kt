package com.abdullah303.logbook.core.data.repository

import com.abdullah303.logbook.core.data.local.ExerciseDao
import com.abdullah303.logbook.core.data.local.toDomainModel
import com.abdullah303.logbook.core.data.local.toEntity
import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    
    // Current exercise for create/edit flow (in-memory state)
    private val _currentExercise = MutableStateFlow(Exercise())
    
    override fun getCurrentExercise(): Flow<Exercise> = _currentExercise.asStateFlow()
    
    override suspend fun updateExercise(exercise: Exercise) {
        _currentExercise.value = exercise
    }
    
    override suspend fun clearExercise() {
        _currentExercise.value = Exercise()
    }
    
    // Database operations for permanent storage
    override fun getAllExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAllExercises().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getExerciseById(id: String): Exercise? {
        return exerciseDao.getExerciseById(id)?.toDomainModel()
    }
    
    override suspend fun saveExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise.toEntity())
    }
    
    override suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise.toEntity())
    }
    
    override suspend fun deleteExerciseById(id: String) {
        exerciseDao.deleteExerciseById(id)
    }
    
    override fun searchExercises(searchQuery: String): Flow<List<Exercise>> {
        return exerciseDao.searchExercises(searchQuery).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override fun getExercisesByPrimaryMuscle(muscle: String): Flow<List<Exercise>> {
        return exerciseDao.getExercisesByPrimaryMuscle(muscle).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override fun getExercisesByEquipment(equipment: String): Flow<List<Exercise>> {
        return exerciseDao.getExercisesByEquipment(equipment).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getExerciseCount(): Int {
        return exerciseDao.getExerciseCount()
    }
} 