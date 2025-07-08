package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.PinLoadedInfoDao
import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PinLoadedInfoRepositoryImpl @Inject constructor(
    private val pinLoadedInfoDao: PinLoadedInfoDao
) : PinLoadedInfoRepository {
    
    override suspend fun insertPinLoadedInfo(pinLoadedInfo: PinLoadedInfo) {
        pinLoadedInfoDao.insert(pinLoadedInfo)
    }
    
    override suspend fun insertAllPinLoadedInfo(pinLoadedInfo: List<PinLoadedInfo>) {
        pinLoadedInfoDao.insertAll(pinLoadedInfo)
    }
    
    override suspend fun getPinLoadedInfoById(resistanceMachineId: String): PinLoadedInfo? {
        return pinLoadedInfoDao.getById(resistanceMachineId)
    }
    
    override suspend fun getAllPinLoadedInfo(): List<PinLoadedInfo> {
        return pinLoadedInfoDao.getAll().first()
    }
    
    override fun getAllPinLoadedInfoFlow(): Flow<List<PinLoadedInfo>> {
        return pinLoadedInfoDao.getAll()
    }
    
    override fun getPinLoadedInfoByResistanceMachineId(resistanceMachineId: String): Flow<PinLoadedInfo?> {
        return pinLoadedInfoDao.getByResistanceMachineId(resistanceMachineId)
    }
    
    override suspend fun updatePinLoadedInfo(pinLoadedInfo: PinLoadedInfo) {
        pinLoadedInfoDao.update(pinLoadedInfo)
    }
    
    override suspend fun deletePinLoadedInfo(pinLoadedInfo: PinLoadedInfo) {
        pinLoadedInfoDao.delete(pinLoadedInfo)
    }
    
    override suspend fun deletePinLoadedInfoById(resistanceMachineId: String) {
        pinLoadedInfoDao.deleteById(resistanceMachineId)
    }
    
    override suspend fun deleteAllPinLoadedInfo() {
        pinLoadedInfoDao.deleteAll()
    }
    
    override suspend fun getPinLoadedInfoCount(): Int {
        return pinLoadedInfoDao.getCount()
    }
    
    override suspend fun getPinLoadedInfoCountByResistanceMachineId(resistanceMachineId: String): Int {
        return pinLoadedInfoDao.getCountByResistanceMachineId(resistanceMachineId)
    }
} 