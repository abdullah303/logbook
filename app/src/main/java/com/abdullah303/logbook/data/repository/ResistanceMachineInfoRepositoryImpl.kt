package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.ResistanceMachineInfoDao
import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.model.ResistanceMachineType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResistanceMachineInfoRepositoryImpl @Inject constructor(
    private val resistanceMachineInfoDao: ResistanceMachineInfoDao
) : ResistanceMachineInfoRepository {
    
    override suspend fun insertResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo) {
        resistanceMachineInfoDao.insert(resistanceMachineInfo)
    }
    
    override suspend fun insertAllResistanceMachineInfo(resistanceMachineInfo: List<ResistanceMachineInfo>) {
        resistanceMachineInfoDao.insertAll(resistanceMachineInfo)
    }
    
    override suspend fun getResistanceMachineInfoById(equipmentId: String): ResistanceMachineInfo? {
        return resistanceMachineInfoDao.getById(equipmentId)
    }
    
    override suspend fun getAllResistanceMachineInfo(): List<ResistanceMachineInfo> {
        return resistanceMachineInfoDao.getAll().first()
    }
    
    override fun getAllResistanceMachineInfoFlow(): Flow<List<ResistanceMachineInfo>> {
        return resistanceMachineInfoDao.getAll()
    }
    
    override fun getResistanceMachineInfoByType(type: ResistanceMachineType): Flow<List<ResistanceMachineInfo>> {
        return resistanceMachineInfoDao.getByType(type)
    }
    
    override suspend fun updateResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo) {
        resistanceMachineInfoDao.update(resistanceMachineInfo)
    }
    
    override suspend fun deleteResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo) {
        resistanceMachineInfoDao.delete(resistanceMachineInfo)
    }
    
    override suspend fun deleteResistanceMachineInfoById(equipmentId: String) {
        resistanceMachineInfoDao.deleteById(equipmentId)
    }
    
    override suspend fun deleteAllResistanceMachineInfo() {
        resistanceMachineInfoDao.deleteAll()
    }
    
    override suspend fun getResistanceMachineInfoCount(): Int {
        return resistanceMachineInfoDao.getCount()
    }
    
    override suspend fun getResistanceMachineInfoCountByType(type: ResistanceMachineType): Int {
        return resistanceMachineInfoDao.getCountByType(type)
    }
} 