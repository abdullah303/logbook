package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.BarbellInfoDao
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import java.math.BigDecimal

@Singleton
class BarbellInfoRepositoryImpl @Inject constructor(
    private val barbellInfoDao: BarbellInfoDao
) : BarbellInfoRepository {
    
    override suspend fun insertBarbellInfo(barbellInfo: BarbellInfo) {
        barbellInfoDao.insert(barbellInfo)
    }
    
    override suspend fun insertAllBarbellInfo(barbellInfo: List<BarbellInfo>) {
        barbellInfoDao.insertAll(barbellInfo)
    }
    
    override suspend fun getBarbellInfoById(equipmentId: String): BarbellInfo? {
        return barbellInfoDao.getById(equipmentId)
    }
    
    override suspend fun getAllBarbellInfo(): List<BarbellInfo> {
        return barbellInfoDao.getAll().first()
    }
    
    override fun getAllBarbellInfoFlow(): Flow<List<BarbellInfo>> {
        return barbellInfoDao.getAll()
    }
    
    override fun getBarbellInfoByMinWeight(minWeight: BigDecimal): Flow<List<BarbellInfo>> {
        return barbellInfoDao.getByMinWeight(minWeight)
    }
    
    override fun getBarbellInfoByMaxWeight(maxWeight: BigDecimal): Flow<List<BarbellInfo>> {
        return barbellInfoDao.getByMaxWeight(maxWeight)
    }
    
    override suspend fun updateBarbellInfo(barbellInfo: BarbellInfo) {
        barbellInfoDao.update(barbellInfo)
    }
    
    override suspend fun deleteBarbellInfo(barbellInfo: BarbellInfo) {
        barbellInfoDao.delete(barbellInfo)
    }
    
    override suspend fun deleteBarbellInfoById(equipmentId: String) {
        barbellInfoDao.deleteById(equipmentId)
    }
    
    override suspend fun deleteAllBarbellInfo() {
        barbellInfoDao.deleteAll()
    }
    
    override suspend fun getBarbellInfoCount(): Int {
        return barbellInfoDao.getCount()
    }
    
    override suspend fun getBarbellInfoCountByWeight(weight: BigDecimal): Int {
        return barbellInfoDao.getCountByWeight(weight)
    }
} 