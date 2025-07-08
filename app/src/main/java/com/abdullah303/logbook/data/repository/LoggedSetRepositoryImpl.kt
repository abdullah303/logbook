package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.LoggedSetDao
import com.abdullah303.logbook.data.local.entity.LoggedSet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggedSetRepositoryImpl @Inject constructor(
    private val loggedSetDao: LoggedSetDao
) : LoggedSetRepository {
    
    override suspend fun insertLoggedSet(loggedSet: LoggedSet) {
        loggedSetDao.insert(loggedSet)
    }
    
    override suspend fun insertAllLoggedSets(loggedSetsList: List<LoggedSet>) {
        loggedSetDao.insertAll(loggedSetsList)
    }
    
    override suspend fun getLoggedSetById(id: String): LoggedSet? {
        return loggedSetDao.getById(id)
    }
    
    override fun getLoggedSetByIdFlow(id: String): Flow<LoggedSet?> {
        return loggedSetDao.getByIdFlow(id)
    }
    
    override suspend fun getAllLoggedSets(): List<LoggedSet> {
        return loggedSetDao.getAll()
    }
    
    override fun getAllLoggedSetsFlow(): Flow<List<LoggedSet>> {
        return loggedSetDao.getAllFlow()
    }
    
    override suspend fun getLoggedSetsByLoggedWorkoutId(loggedWorkoutId: String): List<LoggedSet> {
        return loggedSetDao.getByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getLoggedSetsByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<List<LoggedSet>> {
        return loggedSetDao.getByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getLoggedSetsByExerciseSnapshotId(exerciseSnapshotId: String): List<LoggedSet> {
        return loggedSetDao.getByExerciseSnapshotId(exerciseSnapshotId)
    }
    
    override fun getLoggedSetsByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<List<LoggedSet>> {
        return loggedSetDao.getByExerciseSnapshotIdFlow(exerciseSnapshotId)
    }
    
    override suspend fun getLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): List<LoggedSet> {
        return loggedSetDao.getByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<List<LoggedSet>> {
        return loggedSetDao.getByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun getLoggedSetsBySetNumber(setNumber: Int): List<LoggedSet> {
        return loggedSetDao.getBySetNumber(setNumber)
    }
    
    override fun getLoggedSetsBySetNumberFlow(setNumber: Int): Flow<List<LoggedSet>> {
        return loggedSetDao.getBySetNumberFlow(setNumber)
    }
    
    override suspend fun getLoggedSetsByWeightNotNull(): List<LoggedSet> {
        return loggedSetDao.getByWeightNotNull()
    }
    
    override fun getLoggedSetsByWeightNotNullFlow(): Flow<List<LoggedSet>> {
        return loggedSetDao.getByWeightNotNullFlow()
    }
    
    override suspend fun getLoggedSetsByRepsNotNull(): List<LoggedSet> {
        return loggedSetDao.getByRepsNotNull()
    }
    
    override fun getLoggedSetsByRepsNotNullFlow(): Flow<List<LoggedSet>> {
        return loggedSetDao.getByRepsNotNullFlow()
    }
    
    override suspend fun getLoggedSetsByRirNotNull(): List<LoggedSet> {
        return loggedSetDao.getByRirNotNull()
    }
    
    override fun getLoggedSetsByRirNotNullFlow(): Flow<List<LoggedSet>> {
        return loggedSetDao.getByRirNotNullFlow()
    }
    
    override suspend fun updateLoggedSet(loggedSet: LoggedSet) {
        loggedSetDao.update(loggedSet)
    }
    
    override suspend fun updateAllLoggedSets(loggedSets: List<LoggedSet>) {
        loggedSetDao.updateAll(loggedSets)
    }
    
    override suspend fun deleteLoggedSet(loggedSet: LoggedSet) {
        loggedSetDao.delete(loggedSet)
    }
    
    override suspend fun deleteAllLoggedSets(loggedSets: List<LoggedSet>) {
        loggedSetDao.deleteAll(loggedSets)
    }
    
    override suspend fun deleteLoggedSetById(id: String) {
        loggedSetDao.deleteById(id)
    }
    
    override suspend fun deleteLoggedSetsByLoggedWorkoutId(loggedWorkoutId: String) {
        loggedSetDao.deleteByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override suspend fun deleteLoggedSetsByExerciseSnapshotId(exerciseSnapshotId: String) {
        loggedSetDao.deleteByExerciseSnapshotId(exerciseSnapshotId)
    }
    
    override suspend fun deleteLoggedSetsByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String) {
        loggedSetDao.deleteByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun deleteAllLoggedSets() {
        loggedSetDao.deleteAll()
    }
    
    override suspend fun getLoggedSetsCount(): Int {
        return loggedSetDao.getCount()
    }
    
    override fun getLoggedSetsCountFlow(): Flow<Int> {
        return loggedSetDao.getCountFlow()
    }
    
    override suspend fun getLoggedSetsCountByLoggedWorkoutId(loggedWorkoutId: String): Int {
        return loggedSetDao.getCountByLoggedWorkoutId(loggedWorkoutId)
    }
    
    override fun getLoggedSetsCountByLoggedWorkoutIdFlow(loggedWorkoutId: String): Flow<Int> {
        return loggedSetDao.getCountByLoggedWorkoutIdFlow(loggedWorkoutId)
    }
    
    override suspend fun getLoggedSetsCountByExerciseSnapshotId(exerciseSnapshotId: String): Int {
        return loggedSetDao.getCountByExerciseSnapshotId(exerciseSnapshotId)
    }
    
    override fun getLoggedSetsCountByExerciseSnapshotIdFlow(exerciseSnapshotId: String): Flow<Int> {
        return loggedSetDao.getCountByExerciseSnapshotIdFlow(exerciseSnapshotId)
    }
    
    override suspend fun getLoggedSetsCountByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId: String, exerciseSnapshotId: String): Int {
        return loggedSetDao.getCountByLoggedWorkoutIdAndExerciseSnapshotId(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getLoggedSetsCountByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int> {
        return loggedSetDao.getCountByLoggedWorkoutIdAndExerciseSnapshotIdFlow(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun getMaxSetNumber(loggedWorkoutId: String, exerciseSnapshotId: String): Int? {
        return loggedSetDao.getMaxSetNumber(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getMaxSetNumberFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Int?> {
        return loggedSetDao.getMaxSetNumberFlow(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun getAverageWeight(loggedWorkoutId: String, exerciseSnapshotId: String): Double? {
        return loggedSetDao.getAverageWeight(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getAverageWeightFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?> {
        return loggedSetDao.getAverageWeightFlow(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun getAverageReps(loggedWorkoutId: String, exerciseSnapshotId: String): Double? {
        return loggedSetDao.getAverageReps(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getAverageRepsFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?> {
        return loggedSetDao.getAverageRepsFlow(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override suspend fun getAverageRir(loggedWorkoutId: String, exerciseSnapshotId: String): Double? {
        return loggedSetDao.getAverageRir(loggedWorkoutId, exerciseSnapshotId)
    }
    
    override fun getAverageRirFlow(loggedWorkoutId: String, exerciseSnapshotId: String): Flow<Double?> {
        return loggedSetDao.getAverageRirFlow(loggedWorkoutId, exerciseSnapshotId)
    }
} 