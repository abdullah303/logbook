package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.CableStackInfoDao
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CableStackInfoRepositoryImpl @Inject constructor(
    private val cableStackInfoDao: CableStackInfoDao
) : CableStackInfoRepository {
    
    override suspend fun insertCableStackInfo(cableStackInfo: CableStackInfo) {
        cableStackInfoDao.insert(cableStackInfo)
    }
    
    override suspend fun insertAllCableStackInfo(cableStackInfos: List<CableStackInfo>) {
        cableStackInfoDao.insertAll(cableStackInfos)
    }
    
    override suspend fun getCableStackInfoById(equipmentId: String): CableStackInfo? {
        return cableStackInfoDao.getById(equipmentId)
    }
    
    override fun getCableStackInfoByIdFlow(equipmentId: String): Flow<CableStackInfo?> {
        return cableStackInfoDao.getByIdFlow(equipmentId)
    }
    
    override suspend fun getAllCableStackInfo(): List<CableStackInfo> {
        return cableStackInfoDao.getAll()
    }
    
    override fun getAllCableStackInfoFlow(): Flow<List<CableStackInfo>> {
        return cableStackInfoDao.getAllFlow()
    }
    
    override suspend fun getCableStackInfoByWeightRange(weight: BigDecimal): List<CableStackInfo> {
        return cableStackInfoDao.getByWeightRange(weight)
    }
    
    override fun getCableStackInfoByWeightRangeFlow(weight: BigDecimal): Flow<List<CableStackInfo>> {
        return cableStackInfoDao.getByWeightRangeFlow(weight)
    }
    
    override suspend fun updateCableStackInfo(cableStackInfo: CableStackInfo) {
        cableStackInfoDao.update(cableStackInfo)
    }
    
    override suspend fun updateAllCableStackInfo(cableStackInfos: List<CableStackInfo>) {
        cableStackInfoDao.updateAll(cableStackInfos)
    }
    
    override suspend fun deleteCableStackInfo(cableStackInfo: CableStackInfo) {
        cableStackInfoDao.delete(cableStackInfo)
    }
    
    override suspend fun deleteCableStackInfoById(equipmentId: String) {
        cableStackInfoDao.deleteById(equipmentId)
    }
    
    override suspend fun deleteAllCableStackInfo() {
        cableStackInfoDao.deleteAll()
    }
    
    override suspend fun getCableStackInfoCount(): Int {
        return cableStackInfoDao.getCount()
    }
    
    override fun getCableStackInfoCountFlow(): Flow<Int> {
        return cableStackInfoDao.getCountFlow()
    }
    
    override suspend fun exists(equipmentId: String): Boolean {
        return cableStackInfoDao.exists(equipmentId)
    }
    
    override fun existsFlow(equipmentId: String): Flow<Boolean> {
        return cableStackInfoDao.existsFlow(equipmentId)
    }
} 