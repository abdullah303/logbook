package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.SplitsDao
import com.abdullah303.logbook.data.local.entity.Splits
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplitsRepositoryImpl @Inject constructor(
    private val splitsDao: SplitsDao
) : SplitsRepository {
    
    override suspend fun insertSplits(splits: Splits) {
        splitsDao.insert(splits)
    }
    
    override suspend fun insertAllSplits(splitsList: List<Splits>) {
        splitsDao.insertAll(splitsList)
    }
    
    override suspend fun getSplitsById(id: String): Splits? {
        return splitsDao.getById(id)
    }
    
    override fun getSplitsByIdFlow(id: String): Flow<Splits?> {
        return splitsDao.getByIdFlow(id)
    }
    
    override suspend fun getAllSplits(): List<Splits> {
        return splitsDao.getAll()
    }
    
    override fun getAllSplitsFlow(): Flow<List<Splits>> {
        return splitsDao.getAllFlow()
    }
    
    override suspend fun getSplitsByUserId(userId: String): List<Splits> {
        return splitsDao.getByUserId(userId)
    }
    
    override fun getSplitsByUserIdFlow(userId: String): Flow<List<Splits>> {
        return splitsDao.getByUserIdFlow(userId)
    }
    
    override fun searchSplitsByName(userId: String, searchQuery: String): Flow<List<Splits>> {
        return splitsDao.searchByName(userId, searchQuery)
    }
    
    override suspend fun updateSplits(splits: Splits) {
        splitsDao.update(splits)
    }
    
    override suspend fun deleteSplits(splits: Splits) {
        splitsDao.delete(splits)
    }
    
    override suspend fun deleteSplitsById(id: String) {
        splitsDao.deleteById(id)
    }
    
    override suspend fun deleteSplitsByUserId(userId: String) {
        splitsDao.deleteByUserId(userId)
    }
    
    override suspend fun deleteAllSplits() {
        splitsDao.deleteAll()
    }
    
    override suspend fun getSplitsCount(): Int {
        return splitsDao.getCount()
    }
    
    override suspend fun getSplitsCountByUserId(userId: String): Int {
        return splitsDao.getCountByUserId(userId)
    }
    
    override fun getSplitsCountByUserIdFlow(userId: String): Flow<Int> {
        return splitsDao.getCountByUserIdFlow(userId)
    }
} 