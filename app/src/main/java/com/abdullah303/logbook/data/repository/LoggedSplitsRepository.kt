package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.LoggedSplits
import kotlinx.coroutines.flow.Flow

interface LoggedSplitsRepository {
    
    suspend fun insertLoggedSplit(loggedSplit: LoggedSplits)
    
    suspend fun insertAllLoggedSplits(loggedSplitsList: List<LoggedSplits>)
    
    suspend fun getLoggedSplitById(id: String): LoggedSplits?
    
    fun getLoggedSplitByIdFlow(id: String): Flow<LoggedSplits?>
    
    suspend fun getAllLoggedSplits(): List<LoggedSplits>
    
    fun getAllLoggedSplitsFlow(): Flow<List<LoggedSplits>>
    
    suspend fun getLoggedSplitsBySplitId(splitId: String): List<LoggedSplits>
    
    fun getLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    suspend fun getLoggedSplitBySplitIdAndRunNumber(splitId: String, runNumber: Int): LoggedSplits?
    
    fun getLoggedSplitBySplitIdAndRunNumberFlow(splitId: String, runNumber: Int): Flow<LoggedSplits?>
    
    suspend fun getInProgressLoggedSplits(): List<LoggedSplits>
    
    fun getInProgressLoggedSplitsFlow(): Flow<List<LoggedSplits>>
    
    suspend fun getInProgressLoggedSplitsBySplitId(splitId: String): List<LoggedSplits>
    
    fun getInProgressLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    suspend fun getCompletedLoggedSplits(): List<LoggedSplits>
    
    fun getCompletedLoggedSplitsFlow(): Flow<List<LoggedSplits>>
    
    suspend fun getCompletedLoggedSplitsBySplitId(splitId: String): List<LoggedSplits>
    
    fun getCompletedLoggedSplitsBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    suspend fun updateLoggedSplit(loggedSplit: LoggedSplits)
    
    suspend fun updateAllLoggedSplits(loggedSplitsList: List<LoggedSplits>)
    
    suspend fun deleteLoggedSplit(loggedSplit: LoggedSplits)
    
    suspend fun deleteAllLoggedSplits(loggedSplitsList: List<LoggedSplits>)
    
    suspend fun deleteLoggedSplitById(id: String)
    
    suspend fun deleteLoggedSplitsBySplitId(splitId: String)
    
    suspend fun deleteAllLoggedSplits()
    
    suspend fun getLoggedSplitsCount(): Int
    
    suspend fun getLoggedSplitsCountBySplitId(splitId: String): Int
    
    fun getLoggedSplitsCountBySplitIdFlow(splitId: String): Flow<Int>
    
    suspend fun getCompletedLoggedSplitsCountBySplitId(splitId: String): Int
    
    fun getCompletedLoggedSplitsCountBySplitIdFlow(splitId: String): Flow<Int>
    
    suspend fun getMaxRunNumberBySplitId(splitId: String): Int?
    
    fun getMaxRunNumberBySplitIdFlow(splitId: String): Flow<Int?>
} 