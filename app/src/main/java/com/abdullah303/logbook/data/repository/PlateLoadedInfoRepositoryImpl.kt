package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.PlateLoadedInfoDao
import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlateLoadedInfoRepositoryImpl @Inject constructor(
    private val plateLoadedInfoDao: PlateLoadedInfoDao
) : PlateLoadedInfoRepository {
    
    override suspend fun insertPlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo) {
        plateLoadedInfoDao.insert(plateLoadedInfo)
    }
    
    override suspend fun insertAllPlateLoadedInfo(plateLoadedInfo: List<PlateLoadedInfo>) {
        plateLoadedInfoDao.insertAll(plateLoadedInfo)
    }
    
    override suspend fun getPlateLoadedInfoById(resistanceMachineId: String): PlateLoadedInfo? {
        return plateLoadedInfoDao.getById(resistanceMachineId)
    }
    
    override suspend fun getAllPlateLoadedInfo(): List<PlateLoadedInfo> {
        return plateLoadedInfoDao.getAll().first()
    }
    
    override fun getAllPlateLoadedInfoFlow(): Flow<List<PlateLoadedInfo>> {
        return plateLoadedInfoDao.getAll()
    }
    
    override fun getPlateLoadedInfoByIdFlow(resistanceMachineId: String): Flow<PlateLoadedInfo?> {
        return plateLoadedInfoDao.getByIdFlow(resistanceMachineId)
    }
    
    override suspend fun updatePlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo) {
        plateLoadedInfoDao.update(plateLoadedInfo)
    }
    
    override suspend fun deletePlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo) {
        plateLoadedInfoDao.delete(plateLoadedInfo)
    }
    
    override suspend fun deletePlateLoadedInfoById(resistanceMachineId: String) {
        plateLoadedInfoDao.deleteById(resistanceMachineId)
    }
    
    override suspend fun deleteAllPlateLoadedInfo() {
        plateLoadedInfoDao.deleteAll()
    }
    
    override suspend fun getPlateLoadedInfoCount(): Int {
        return plateLoadedInfoDao.getCount()
    }
    
    override suspend fun exists(resistanceMachineId: String): Int {
        return plateLoadedInfoDao.exists(resistanceMachineId)
    }
} 