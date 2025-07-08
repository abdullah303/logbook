package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.DumbbellRangeDao
import com.abdullah303.logbook.data.local.entity.DumbbellRange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DumbbellRangeRepositoryImpl @Inject constructor(
    private val dumbbellRangeDao: DumbbellRangeDao
) : DumbbellRangeRepository {
    
    override suspend fun insertDumbbellRange(dumbbellRange: DumbbellRange) {
        dumbbellRangeDao.insert(dumbbellRange)
    }
    
    override suspend fun insertAllDumbbellRanges(dumbbellRanges: List<DumbbellRange>) {
        dumbbellRangeDao.insertAll(dumbbellRanges)
    }
    
    override suspend fun getDumbbellRangeById(id: String): DumbbellRange? {
        return dumbbellRangeDao.getById(id)
    }
    
    override fun getDumbbellRangeByIdFlow(id: String): Flow<DumbbellRange?> {
        return dumbbellRangeDao.getByIdFlow(id)
    }
    
    override suspend fun getDumbbellRangesBySettingsId(settingsId: String): List<DumbbellRange> {
        return dumbbellRangeDao.getBySettingsId(settingsId)
    }
    
    override fun getDumbbellRangesBySettingsIdFlow(settingsId: String): Flow<List<DumbbellRange>> {
        return dumbbellRangeDao.getBySettingsIdFlow(settingsId)
    }
    
    override suspend fun getAllDumbbellRanges(): List<DumbbellRange> {
        return dumbbellRangeDao.getAll()
    }
    
    override fun getAllDumbbellRangesFlow(): Flow<List<DumbbellRange>> {
        return dumbbellRangeDao.getAllFlow()
    }
    
    override suspend fun updateDumbbellRange(dumbbellRange: DumbbellRange) {
        dumbbellRangeDao.update(dumbbellRange)
    }
    
    override suspend fun updateAllDumbbellRanges(dumbbellRanges: List<DumbbellRange>) {
        dumbbellRangeDao.updateAll(dumbbellRanges)
    }
    
    override suspend fun deleteDumbbellRange(dumbbellRange: DumbbellRange) {
        dumbbellRangeDao.delete(dumbbellRange)
    }
    
    override suspend fun deleteDumbbellRangeById(id: String) {
        dumbbellRangeDao.deleteById(id)
    }
    
    override suspend fun deleteDumbbellRangesBySettingsId(settingsId: String) {
        dumbbellRangeDao.deleteBySettingsId(settingsId)
    }
    
    override suspend fun deleteAllDumbbellRanges() {
        dumbbellRangeDao.deleteAll()
    }
    
    override suspend fun getDumbbellRangeCount(): Int {
        return dumbbellRangeDao.getCount()
    }
    
    override fun getDumbbellRangeCountFlow(): Flow<Int> {
        return dumbbellRangeDao.getCountFlow()
    }
    
    override suspend fun getDumbbellRangeCountBySettingsId(settingsId: String): Int {
        return dumbbellRangeDao.getCountBySettingsId(settingsId)
    }
    
    override fun getDumbbellRangeCountBySettingsIdFlow(settingsId: String): Flow<Int> {
        return dumbbellRangeDao.getCountBySettingsIdFlow(settingsId)
    }
} 