package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.LoggedSplitsDao
import com.abdullah303.logbook.data.local.entity.LoggedSplits
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggedSplitsRepositoryImpl @Inject constructor(
    private val loggedSplitsDao: LoggedSplitsDao
) : LoggedSplitsRepository {
    
    override suspend fun insertLoggedSplit(loggedSplit: LoggedSplits) {
        loggedSplitsDao.insert(loggedSplit)
    }
    
    override suspend fun insertAllLoggedSplits(loggedSplitsList: List<LoggedSplits>) {
        loggedSplitsDao.insertAll(loggedSplitsList)
    }
    
    override suspend fun getLoggedSplitById(id: String): LoggedSplits? {
        return loggedSplitsDao.getById(id)
    }
    
    override fun getLoggedSplitByIdFlow(id: String): Flow<LoggedSplits?> {
        return loggedSplitsDao.getByIdFlow(id)
    }
    
    override suspend fun getAllLoggedSplits(): List<LoggedSplits> {
        return loggedSplitsDao.getAll()
    }
    
    override fun getAllLoggedSplitsFlow(): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getAllFlow()
    }
    
    override suspend fun getLoggedSplitsBySplitId(splitId: String): List<LoggedSplits> {
        return loggedSplitsDao.getBySplitId(splitId)
    }
    
    override fun getLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getBySplitIdFlow(splitId)
    }
    
    override suspend fun getLoggedSplitBySplitIdAndRunNumber(splitId: String, runNumber: Int): LoggedSplits? {
        return loggedSplitsDao.getBySplitIdAndRunNumber(splitId, runNumber)
    }
    
    override fun getLoggedSplitBySplitIdAndRunNumberFlow(splitId: String, runNumber: Int): Flow<LoggedSplits?> {
        return loggedSplitsDao.getBySplitIdAndRunNumberFlow(splitId, runNumber)
    }
    
    override suspend fun getInProgressLoggedSplits(): List<LoggedSplits> {
        return loggedSplitsDao.getInProgress()
    }
    
    override fun getInProgressLoggedSplitsFlow(): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getInProgressFlow()
    }
    
    override suspend fun getInProgressLoggedSplitsBySplitId(splitId: String): List<LoggedSplits> {
        return loggedSplitsDao.getInProgressBySplitId(splitId)
    }
    
    override fun getInProgressLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getInProgressBySplitIdFlow(splitId)
    }
    
    override suspend fun getCompletedLoggedSplits(): List<LoggedSplits> {
        return loggedSplitsDao.getCompleted()
    }
    
    override fun getCompletedLoggedSplitsFlow(): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getCompletedFlow()
    }
    
    override suspend fun getCompletedLoggedSplitsBySplitId(splitId: String): List<LoggedSplits> {
        return loggedSplitsDao.getCompletedBySplitId(splitId)
    }
    
    override fun getCompletedLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>> {
        return loggedSplitsDao.getCompletedBySplitIdFlow(splitId)
    }
    
    override suspend fun updateLoggedSplit(loggedSplit: LoggedSplits) {
        loggedSplitsDao.update(loggedSplit)
    }
    
    override suspend fun updateAllLoggedSplits(loggedSplitsList: List<LoggedSplits>) {
        loggedSplitsDao.updateAll(loggedSplitsList)
    }
    
    override suspend fun deleteLoggedSplit(loggedSplit: LoggedSplits) {
        loggedSplitsDao.delete(loggedSplit)
    }
    
    override suspend fun deleteAllLoggedSplits(loggedSplitsList: List<LoggedSplits>) {
        loggedSplitsDao.deleteAll(loggedSplitsList)
    }
    
    override suspend fun deleteLoggedSplitById(id: String) {
        loggedSplitsDao.deleteById(id)
    }
    
    override suspend fun deleteLoggedSplitsBySplitId(splitId: String) {
        loggedSplitsDao.deleteBySplitId(splitId)
    }
    
    override suspend fun deleteAllLoggedSplits() {
        loggedSplitsDao.deleteAll()
    }
    
    override suspend fun getLoggedSplitsCount(): Int {
        return loggedSplitsDao.getCount()
    }
    
    override suspend fun getLoggedSplitsCountBySplitId(splitId: String): Int {
        return loggedSplitsDao.getCountBySplitId(splitId)
    }
    
    override fun getLoggedSplitsCountBySplitIdFlow(splitId: String): Flow<Int> {
        return loggedSplitsDao.getCountBySplitIdFlow(splitId)
    }
    
    override suspend fun getCompletedLoggedSplitsCountBySplitId(splitId: String): Int {
        return loggedSplitsDao.getCompletedCountBySplitId(splitId)
    }
    
    override fun getCompletedLoggedSplitsCountBySplitIdFlow(splitId: String): Flow<Int> {
        return loggedSplitsDao.getCompletedCountBySplitIdFlow(splitId)
    }
    
    override suspend fun getMaxRunNumberBySplitId(splitId: String): Int? {
        return loggedSplitsDao.getMaxRunNumberBySplitId(splitId)
    }
    
    override fun getMaxRunNumberBySplitIdFlow(splitId: String): Flow<Int?> {
        return loggedSplitsDao.getMaxRunNumberBySplitIdFlow(splitId)
    }
} 