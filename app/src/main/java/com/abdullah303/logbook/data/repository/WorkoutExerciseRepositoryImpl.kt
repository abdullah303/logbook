package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.WorkoutExerciseDao
import com.abdullah303.logbook.data.local.entity.WorkoutExercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutExerciseRepositoryImpl @Inject constructor(
    private val workoutExerciseDao: WorkoutExerciseDao
) : WorkoutExerciseRepository {
    
    override suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise) {
        workoutExerciseDao.insert(workoutExercise)
    }
    
    override suspend fun insertAllWorkoutExercises(workoutExercisesList: List<WorkoutExercise>) {
        workoutExerciseDao.insertAll(workoutExercisesList)
    }
    
    override suspend fun getWorkoutExerciseById(id: String): WorkoutExercise? {
        return workoutExerciseDao.getById(id)
    }
    
    override fun getWorkoutExerciseByIdFlow(id: String): Flow<WorkoutExercise?> {
        return workoutExerciseDao.getByIdFlow(id)
    }
    
    override suspend fun getAllWorkoutExercises(): List<WorkoutExercise> {
        return workoutExerciseDao.getAll()
    }
    
    override fun getAllWorkoutExercisesFlow(): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getAllFlow()
    }
    
    override suspend fun getWorkoutExercisesByWorkoutId(workoutId: String): List<WorkoutExercise> {
        return workoutExerciseDao.getByWorkoutId(workoutId)
    }
    
    override fun getWorkoutExercisesByWorkoutIdFlow(workoutId: String): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun getWorkoutExercisesByExerciseId(exerciseId: String): List<WorkoutExercise> {
        return workoutExerciseDao.getByExerciseId(exerciseId)
    }
    
    override fun getWorkoutExercisesByExerciseIdFlow(exerciseId: String): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getByExerciseIdFlow(exerciseId)
    }
    
    override suspend fun getWorkoutExercisesBySupersetGroupId(supersetGroupId: String): List<WorkoutExercise> {
        return workoutExerciseDao.getBySupersetGroupId(supersetGroupId)
    }
    
    override fun getWorkoutExercisesBySupersetGroupIdFlow(supersetGroupId: String): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getBySupersetGroupIdFlow(supersetGroupId)
    }
    
    override suspend fun getWorkoutExercisesWithoutSupersetGroup(): List<WorkoutExercise> {
        return workoutExerciseDao.getWithoutSupersetGroup()
    }
    
    override fun getWorkoutExercisesWithoutSupersetGroupFlow(): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getWithoutSupersetGroupFlow()
    }
    
    override suspend fun getWorkoutExercisesByWorkoutIdWithoutSupersetGroup(workoutId: String): List<WorkoutExercise> {
        return workoutExerciseDao.getByWorkoutIdWithoutSupersetGroup(workoutId)
    }
    
    override fun getWorkoutExercisesByWorkoutIdWithoutSupersetGroupFlow(workoutId: String): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getByWorkoutIdWithoutSupersetGroupFlow(workoutId)
    }
    
    override suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise) {
        workoutExerciseDao.update(workoutExercise)
    }
    
    override suspend fun updateAllWorkoutExercises(workoutExercises: List<WorkoutExercise>) {
        workoutExerciseDao.updateAll(workoutExercises)
    }
    
    override suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise) {
        workoutExerciseDao.delete(workoutExercise)
    }
    
    override suspend fun deleteAllWorkoutExercises(workoutExercises: List<WorkoutExercise>) {
        workoutExerciseDao.deleteAll(workoutExercises)
    }
    
    override suspend fun deleteWorkoutExerciseById(id: String) {
        workoutExerciseDao.deleteById(id)
    }
    
    override suspend fun deleteWorkoutExercisesByWorkoutId(workoutId: String) {
        workoutExerciseDao.deleteByWorkoutId(workoutId)
    }
    
    override suspend fun deleteWorkoutExercisesByExerciseId(exerciseId: String) {
        workoutExerciseDao.deleteByExerciseId(exerciseId)
    }
    
    override suspend fun deleteWorkoutExercisesBySupersetGroupId(supersetGroupId: String) {
        workoutExerciseDao.deleteBySupersetGroupId(supersetGroupId)
    }
    
    override suspend fun deleteAllWorkoutExercises() {
        workoutExerciseDao.deleteAll()
    }
    
    override suspend fun getWorkoutExercisesCount(): Int {
        return workoutExerciseDao.getCount()
    }
    
    override suspend fun getWorkoutExercisesCountByWorkoutId(workoutId: String): Int {
        return workoutExerciseDao.getCountByWorkoutId(workoutId)
    }
    
    override fun getWorkoutExercisesCountByWorkoutIdFlow(workoutId: String): Flow<Int> {
        return workoutExerciseDao.getCountByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun getWorkoutExercisesCountByExerciseId(exerciseId: String): Int {
        return workoutExerciseDao.getCountByExerciseId(exerciseId)
    }
    
    override suspend fun getWorkoutExercisesCountBySupersetGroupId(supersetGroupId: String): Int {
        return workoutExerciseDao.getCountBySupersetGroupId(supersetGroupId)
    }
    
    override suspend fun getMaxPositionByWorkoutId(workoutId: String): Int? {
        return workoutExerciseDao.getMaxPositionByWorkoutId(workoutId)
    }
    
    override fun getMaxPositionByWorkoutIdFlow(workoutId: String): Flow<Int?> {
        return workoutExerciseDao.getMaxPositionByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun getMaxPositionBySupersetGroupId(supersetGroupId: String): Int? {
        return workoutExerciseDao.getMaxPositionBySupersetGroupId(supersetGroupId)
    }
    
    override fun getMaxPositionBySupersetGroupIdFlow(supersetGroupId: String): Flow<Int?> {
        return workoutExerciseDao.getMaxPositionBySupersetGroupIdFlow(supersetGroupId)
    }
} 