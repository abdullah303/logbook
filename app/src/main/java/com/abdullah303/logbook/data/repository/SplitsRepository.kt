package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.Splits
import kotlinx.coroutines.flow.Flow

interface SplitsRepository {
    
    suspend fun insertSplits(splits: Splits)
    
    suspend fun insertAllSplits(splitsList: List<Splits>)
    
    suspend fun getSplitsById(id: String): Splits?
    
    fun getSplitsByIdFlow(id: String): Flow<Splits?>
    
    suspend fun getAllSplits(): List<Splits>
    
    fun getAllSplitsFlow(): Flow<List<Splits>>
    
    suspend fun getSplitsByUserId(userId: String): List<Splits>
    
    fun getSplitsByUserIdFlow(userId: String): Flow<List<Splits>>
    
    fun searchSplitsByName(userId: String, searchQuery: String): Flow<List<Splits>>
    
    suspend fun updateSplits(splits: Splits)
    
    suspend fun deleteSplits(splits: Splits)
    
    suspend fun deleteSplitsById(id: String)
    
    suspend fun deleteSplitsByUserId(userId: String)
    
    suspend fun deleteAllSplits()
    
    suspend fun getSplitsCount(): Int
    
    suspend fun getSplitsCountByUserId(userId: String): Int
    
    fun getSplitsCountByUserIdFlow(userId: String): Flow<Int>
} 