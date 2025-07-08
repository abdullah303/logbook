package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.LoggedWorkoutDao
import com.abdullah303.logbook.data.local.entity.LoggedWorkout
import com.abdullah303.logbook.data.model.LoggedWorkoutStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggedWorkoutRepositoryImpl @Inject constructor(
    private val loggedWorkoutDao: LoggedWorkoutDao
) : LoggedWorkoutRepository {
    
    override suspend fun insertLoggedWorkout(loggedWorkout: LoggedWorkout) {
        loggedWorkoutDao.insert(loggedWorkout)
    }
    
    override suspend fun insertAllLoggedWorkouts(loggedWorkoutsList: List<LoggedWorkout>) {
        loggedWorkoutDao.insertAll(loggedWorkoutsList)
    }
    
    override suspend fun getLoggedWorkoutById(id: String): LoggedWorkout? {
        return loggedWorkoutDao.getById(id)
    }
    
    override fun getLoggedWorkoutByIdFlow(id: String): Flow<LoggedWorkout?> {
        return loggedWorkoutDao.getByIdFlow(id)
    }
    
    override suspend fun getAllLoggedWorkouts(): List<LoggedWorkout> {
        return loggedWorkoutDao.getAll()
    }
    
    override fun getAllLoggedWorkoutsFlow(): Flow<List<LoggedWorkout>> {
        return loggedWorkoutDao.getAllFlow()
    }
    
    override suspend fun getLoggedWorkoutsByLoggedSplitId(loggedSplitId: String): List<LoggedWorkout> {
        return loggedWorkoutDao.getByLoggedSplitId(loggedSplitId)
    }
    
    override fun getLoggedWorkoutsByLoggedSplitIdFlow(loggedSplitId: String): Flow<List<LoggedWorkout>> {
        return loggedWorkoutDao.getByLoggedSplitIdFlow(loggedSplitId)
    }
    
    override suspend fun getLoggedWorkoutsByWorkoutId(workoutId: String): List<LoggedWorkout> {
        return loggedWorkoutDao.getByWorkoutId(workoutId)
    }
    
    override fun getLoggedWorkoutsByWorkoutIdFlow(workoutId: String): Flow<List<LoggedWorkout>> {
        return loggedWorkoutDao.getByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun getLoggedWorkoutsByStatus(status: LoggedWorkoutStatus): List<LoggedWorkout> {
        return loggedWorkoutDao.getByStatus(status)
    }
    
    override fun getLoggedWorkoutsByStatusFlow(status: LoggedWorkoutStatus): Flow<List<LoggedWorkout>> {
        return loggedWorkoutDao.getByStatusFlow(status)
    }
    
    override suspend fun getLoggedWorkoutsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<LoggedWorkout> {
        return loggedWorkoutDao.getByDateRange(startDate, endDate)
    }
    
    override fun getLoggedWorkoutsByDateRangeFlow(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<LoggedWorkout>> {
        return loggedWorkoutDao.getByDateRangeFlow(startDate, endDate)
    }
    
    override suspend fun getLoggedWorkoutByLoggedSplitIdAndWorkoutId(loggedSplitId: String, workoutId: String): LoggedWorkout? {
        return loggedWorkoutDao.getByLoggedSplitIdAndWorkoutId(loggedSplitId, workoutId)
    }
    
    override fun getLoggedWorkoutByLoggedSplitIdAndWorkoutIdFlow(loggedSplitId: String, workoutId: String): Flow<LoggedWorkout?> {
        return loggedWorkoutDao.getByLoggedSplitIdAndWorkoutIdFlow(loggedSplitId, workoutId)
    }
    
    override suspend fun updateLoggedWorkout(loggedWorkout: LoggedWorkout) {
        loggedWorkoutDao.update(loggedWorkout)
    }
    
    override suspend fun updateAllLoggedWorkouts(loggedWorkouts: List<LoggedWorkout>) {
        loggedWorkoutDao.updateAll(loggedWorkouts)
    }
    
    override suspend fun deleteLoggedWorkout(loggedWorkout: LoggedWorkout) {
        loggedWorkoutDao.delete(loggedWorkout)
    }
    
    override suspend fun deleteAllLoggedWorkouts(loggedWorkouts: List<LoggedWorkout>) {
        loggedWorkoutDao.deleteAll(loggedWorkouts)
    }
    
    override suspend fun deleteLoggedWorkoutById(id: String) {
        loggedWorkoutDao.deleteById(id)
    }
    
    override suspend fun deleteLoggedWorkoutsByLoggedSplitId(loggedSplitId: String) {
        loggedWorkoutDao.deleteByLoggedSplitId(loggedSplitId)
    }
    
    override suspend fun deleteLoggedWorkoutsByWorkoutId(workoutId: String) {
        loggedWorkoutDao.deleteByWorkoutId(workoutId)
    }
    
    override suspend fun deleteAllLoggedWorkouts() {
        loggedWorkoutDao.deleteAll()
    }
    
    override suspend fun getLoggedWorkoutsCount(): Int {
        return loggedWorkoutDao.getCount()
    }
    
    override suspend fun getLoggedWorkoutsCountByLoggedSplitId(loggedSplitId: String): Int {
        return loggedWorkoutDao.getCountByLoggedSplitId(loggedSplitId)
    }
    
    override fun getLoggedWorkoutsCountByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int> {
        return loggedWorkoutDao.getCountByLoggedSplitIdFlow(loggedSplitId)
    }
    
    override suspend fun getLoggedWorkoutsCountByWorkoutId(workoutId: String): Int {
        return loggedWorkoutDao.getCountByWorkoutId(workoutId)
    }
    
    override fun getLoggedWorkoutsCountByWorkoutIdFlow(workoutId: String): Flow<Int> {
        return loggedWorkoutDao.getCountByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun getLoggedWorkoutsCountByStatus(status: LoggedWorkoutStatus): Int {
        return loggedWorkoutDao.getCountByStatus(status)
    }
    
    override fun getLoggedWorkoutsCountByStatusFlow(status: LoggedWorkoutStatus): Flow<Int> {
        return loggedWorkoutDao.getCountByStatusFlow(status)
    }
    
    override suspend fun getTotalDurationByLoggedSplitId(loggedSplitId: String): Int? {
        return loggedWorkoutDao.getTotalDurationByLoggedSplitId(loggedSplitId)
    }
    
    override fun getTotalDurationByLoggedSplitIdFlow(loggedSplitId: String): Flow<Int?> {
        return loggedWorkoutDao.getTotalDurationByLoggedSplitIdFlow(loggedSplitId)
    }
    
    override suspend fun getAverageDurationByWorkoutId(workoutId: String): Double? {
        return loggedWorkoutDao.getAverageDurationByWorkoutId(workoutId)
    }
    
    override fun getAverageDurationByWorkoutIdFlow(workoutId: String): Flow<Double?> {
        return loggedWorkoutDao.getAverageDurationByWorkoutIdFlow(workoutId)
    }
} 