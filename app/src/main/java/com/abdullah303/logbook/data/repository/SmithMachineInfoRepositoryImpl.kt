package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.SmithMachineInfoDao
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import java.math.BigDecimal

@Singleton
class SmithMachineInfoRepositoryImpl @Inject constructor(
    private val smithMachineInfoDao: SmithMachineInfoDao
) : SmithMachineInfoRepository {
    
    override suspend fun insertSmithMachineInfo(smithMachineInfo: SmithMachineInfo) {
        smithMachineInfoDao.insert(smithMachineInfo)
    }
    
    override suspend fun insertAllSmithMachineInfo(smithMachineInfo: List<SmithMachineInfo>) {
        smithMachineInfoDao.insertAll(smithMachineInfo)
    }
    
    override suspend fun getSmithMachineInfoById(equipmentId: String): SmithMachineInfo? {
        return smithMachineInfoDao.getById(equipmentId)
    }
    
    override suspend fun getAllSmithMachineInfo(): List<SmithMachineInfo> {
        return smithMachineInfoDao.getAll().first()
    }
    
    override fun getAllSmithMachineInfoFlow(): Flow<List<SmithMachineInfo>> {
        return smithMachineInfoDao.getAll()
    }
    
    override fun getSmithMachineInfoByMinWeight(minWeight: BigDecimal): Flow<List<SmithMachineInfo>> {
        return smithMachineInfoDao.getByMinWeight(minWeight)
    }
    
    override fun getSmithMachineInfoByMaxWeight(maxWeight: BigDecimal): Flow<List<SmithMachineInfo>> {
        return smithMachineInfoDao.getByMaxWeight(maxWeight)
    }
    
    override suspend fun updateSmithMachineInfo(smithMachineInfo: SmithMachineInfo) {
        smithMachineInfoDao.update(smithMachineInfo)
    }
    
    override suspend fun deleteSmithMachineInfo(smithMachineInfo: SmithMachineInfo) {
        smithMachineInfoDao.delete(smithMachineInfo)
    }
    
    override suspend fun deleteSmithMachineInfoById(equipmentId: String) {
        smithMachineInfoDao.deleteById(equipmentId)
    }
    
    override suspend fun deleteAllSmithMachineInfo() {
        smithMachineInfoDao.deleteAll()
    }
    
    override suspend fun getSmithMachineInfoCount(): Int {
        return smithMachineInfoDao.getCount()
    }
    
    override suspend fun getSmithMachineInfoCountByWeight(weight: BigDecimal): Int {
        return smithMachineInfoDao.getCountByWeight(weight)
    }
} 