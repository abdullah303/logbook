package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.WorkoutExerciseSnapshotDao
import com.abdullah303.logbook.data.local.entity.WorkoutExerciseSnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutExerciseSnapshotRepositoryImpl @Inject constructor(
    private val workoutExerciseSnapshotDao: WorkoutExerciseSnapshotDao
) : WorkoutExerciseSnapshotRepository {
    
    override suspend fun insertWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot) {
        workoutExerciseSnapshotDao.insert(workoutExerciseSnapshot)
    }
    
    override suspend fun insertAllWorkoutExerciseSnapshots(workoutExerciseSnapshotsList: List<WorkoutExerciseSnapshot>) {
        workoutExerciseSnapshotDao.insertAll(workoutExerciseSnapshotsList)
    }
    
    override suspend fun getWorkoutExerciseSnapshotById(id: String): WorkoutExerciseSnapshot? {
        return workoutExerciseSnapshotDao.getById(id)
    }
    
    override fun getWorkoutExerciseSnapshotByIdFlow(id: String): Flow<WorkoutExerciseSnapshot?> {
        return workoutExerciseSnapshotDao.getByIdFlow(id)
    }
    
    override suspend fun getAllWorkoutExerciseSnapshots(): List<WorkoutExerciseSnapshot> {
        return workoutExerciseSnapshotDao.getAll()
    }
    
    override fun getAllWorkoutExerciseSnapshotsFlow(): Flow<List<WorkoutExerciseSnapshot>> {
        return workoutExerciseSnapshotDao.getAllFlow()
    }
    
    override suspend fun getWorkoutExerciseSnapshotsByLoggedWorkoutId(loggedWorkoutId: String): List<WorkoutExerciseSnapshot> {
        return workoutExerciseSnapshotDao.getByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getWorkoutExerciseSnapshotsByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<WorkoutExerciseSnapshot>> {
        return workoutExerciseSnapshotDao.getByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getWorkoutExerciseSnapshotsByWorkoutExerciseId(workoutExerciseId: String): List<WorkoutExerciseSnapshot> {
        return workoutExerciseSnapshotDao.getByWorkoutExerciseId(workoutExerciseId)
    }
    
    override fun getWorkoutExerciseSnapshotsByWorkoutExerciseIdFlow(workoutExerciseId: String): Flow<List<WorkoutExerciseSnapshot>> {
        return workoutExerciseSnapshotDao.getByWorkoutExerciseIdFlow(workoutExerciseId)
    }
    
    override suspend fun updateWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot) {
        workoutExerciseSnapshotDao.update(workoutExerciseSnapshot)
    }
    
    override suspend fun updateAllWorkoutExerciseSnapshots(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>) {
        workoutExerciseSnapshotDao.updateAll(workoutExerciseSnapshots)
    }
    
    override suspend fun deleteWorkoutExerciseSnapshot(workoutExerciseSnapshot: WorkoutExerciseSnapshot) {
        workoutExerciseSnapshotDao.delete(workoutExerciseSnapshot)
    }
    
    override suspend fun deleteAllWorkoutExerciseSnapshots(workoutExerciseSnapshots: List<WorkoutExerciseSnapshot>) {
        workoutExerciseSnapshotDao.deleteAll(workoutExerciseSnapshots)
    }
    
    override suspend fun deleteWorkoutExerciseSnapshotById(id: String) {
        workoutExerciseSnapshotDao.deleteById(id)
    }
    
    override suspend fun deleteWorkoutExerciseSnapshotsByLoggedWorkoutId(loggedWorkoutId: String) {
        workoutExerciseSnapshotDao.deleteByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override suspend fun deleteWorkoutExerciseSnapshotsByWorkoutExerciseId(workoutExerciseId: String) {
        workoutExerciseSnapshotDao.deleteByWorkoutExerciseId(workoutExerciseId)
    }
    
    override suspend fun deleteAllWorkoutExerciseSnapshots() {
        workoutExerciseSnapshotDao.deleteAll()
    }
    
    override suspend fun getWorkoutExerciseSnapshotsCount(): Int {
        return workoutExerciseSnapshotDao.getCount()
    }
    
    override suspend fun getWorkoutExerciseSnapshotsCountByLoggedWorkoutId(loggedWorkoutId: String): Int {
        return workoutExerciseSnapshotDao.getCountByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getWorkoutExerciseSnapshotsCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int> {
        return workoutExerciseSnapshotDao.getCountByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getWorkoutExerciseSnapshotsCountByWorkoutExerciseId(workoutExerciseId: String): Int {
        return workoutExerciseSnapshotDao.getCountByWorkoutExerciseId(workoutExerciseId)
    }
    
    override suspend fun getMaxPositionByLoggedWorkoutId(loggedWorkoutId: String): Int? {
        return workoutExerciseSnapshotDao.getMaxPositionByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getMaxPositionByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int?> {
        return workoutExerciseSnapshotDao.getMaxPositionByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
} 