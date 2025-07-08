package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.ExerciseDao
import com.abdullah303.logbook.data.local.entity.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    
    override suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }
    
    override suspend fun insertAllExercises(exercises: List<Exercise>) {
        exerciseDao.insertAll(exercises)
    }
    
    override suspend fun getExerciseById(id: String): Exercise? {
        return exerciseDao.getById(id)
    }
    
    override fun getExerciseByIdFlow(id: String): Flow<Exercise?> {
        return exerciseDao.getByIdFlow(id)
    }
    
    override suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAll()
    }
    
    override fun getAllExercisesFlow(): Flow<List<Exercise>> {
        return exerciseDao.getAllFlow()
    }
    
    override suspend fun getExercisesByEquipmentId(equipmentId: String): List<Exercise> {
        return exerciseDao.getByEquipmentId(equipmentId)
    }
    
    override fun getExercisesByEquipmentIdFlow(equipmentId: String): Flow<List<Exercise>> {
        return exerciseDao.getByEquipmentIdFlow(equipmentId)
    }
    
    override suspend fun searchExercisesByName(searchQuery: String): List<Exercise> {
        return exerciseDao.searchByName(searchQuery)
    }
    
    override fun searchExercisesByNameFlow(searchQuery: String): Flow<List<Exercise>> {
        return exerciseDao.searchByNameFlow(searchQuery)
    }
    
    override suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise)
    }
    
    override suspend fun updateAllExercises(exercises: List<Exercise>) {
        exerciseDao.updateAll(exercises)
    }
    
    override suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }
    
    override suspend fun deleteAllExercises(exercises: List<Exercise>) {
        exerciseDao.deleteAll(exercises)
    }
    
    override suspend fun deleteExerciseById(id: String) {
        exerciseDao.deleteById(id)
    }
    
    override suspend fun deleteExercisesByEquipmentId(equipmentId: String) {
        exerciseDao.deleteByEquipmentId(equipmentId)
    }
    
    override suspend fun deleteAllExercises() {
        exerciseDao.deleteAll()
    }
    
    override suspend fun getExerciseCount(): Int {
        return exerciseDao.getCount()
    }
    
    override suspend fun getExerciseCountByEquipmentId(equipmentId: String): Int {
        return exerciseDao.getCountByEquipmentId(equipmentId)
    }
} 