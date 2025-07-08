package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.WorkoutDao
import com.abdullah303.logbook.data.local.entity.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {
    
    override suspend fun insertWorkout(workout: Workout) {
        workoutDao.insert(workout)
    }
    
    override suspend fun insertAllWorkouts(workoutsList: List<Workout>) {
        workoutDao.insertAll(workoutsList)
    }
    
    override suspend fun getWorkoutById(id: String): Workout? {
        return workoutDao.getById(id)
    }
    
    override fun getWorkoutByIdFlow(id: String): Flow<Workout?> {
        return workoutDao.getByIdFlow(id)
    }
    
    override suspend fun getAllWorkouts(): List<Workout> {
        return workoutDao.getAll()
    }
    
    override fun getAllWorkoutsFlow(): Flow<List<Workout>> {
        return workoutDao.getAllFlow()
    }
    
    override suspend fun getWorkoutsBySplitId(splitId: String): List<Workout> {
        return workoutDao.getBySplitId(splitId)
    }
    
    override fun getWorkoutsBySplitIdFlow(splitId: String): Flow<List<Workout>> {
        return workoutDao.getBySplitIdFlow(splitId)
    }
    
    override fun searchWorkoutsByName(searchQuery: String): Flow<List<Workout>> {
        return workoutDao.searchByNameFlow(searchQuery)
    }
    
    override fun searchWorkoutsByNameInSplit(splitId: String, searchQuery: String): Flow<List<Workout>> {
        return workoutDao.searchByNameInSplitFlow(splitId, searchQuery)
    }
    
    override suspend fun updateWorkout(workout: Workout) {
        workoutDao.update(workout)
    }
    
    override suspend fun updateAllWorkouts(workouts: List<Workout>) {
        workoutDao.updateAll(workouts)
    }
    
    override suspend fun deleteWorkout(workout: Workout) {
        workoutDao.delete(workout)
    }
    
    override suspend fun deleteAllWorkouts(workouts: List<Workout>) {
        workoutDao.deleteAll(workouts)
    }
    
    override suspend fun deleteWorkoutById(id: String) {
        workoutDao.deleteById(id)
    }
    
    override suspend fun deleteWorkoutsBySplitId(splitId: String) {
        workoutDao.deleteBySplitId(splitId)
    }
    
    override suspend fun deleteAllWorkouts() {
        workoutDao.deleteAll()
    }
    
    override suspend fun getWorkoutsCount(): Int {
        return workoutDao.getCount()
    }
    
    override suspend fun getWorkoutsCountBySplitId(splitId: String): Int {
        return workoutDao.getCountBySplitId(splitId)
    }
    
    override fun getWorkoutsCountBySplitIdFlow(splitId: String): Flow<Int> {
        return workoutDao.getCountBySplitIdFlow(splitId)
    }
    
    override suspend fun getMaxPositionBySplitId(splitId: String): Int? {
        return workoutDao.getMaxPositionBySplitId(splitId)
    }
    
    override fun getMaxPositionBySplitIdFlow(splitId: String): Flow<Int?> {
        return workoutDao.getMaxPositionBySplitIdFlow(splitId)
    }
} 