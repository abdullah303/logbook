package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.model.ResistanceMachineType
import kotlinx.coroutines.flow.Flow

interface ResistanceMachineInfoRepository {
    
    suspend fun insertResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo)
    
    suspend fun insertAllResistanceMachineInfo(resistanceMachineInfo: List<ResistanceMachineInfo>)
    
    suspend fun getResistanceMachineInfoById(equipmentId: String): ResistanceMachineInfo?
    
    suspend fun getAllResistanceMachineInfo(): List<ResistanceMachineInfo>
    
    fun getAllResistanceMachineInfoFlow(): Flow<List<ResistanceMachineInfo>>
    
    fun getResistanceMachineInfoByType(type: ResistanceMachineType): Flow<List<ResistanceMachineInfo>>
    
    suspend fun updateResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo)
    
    suspend fun deleteResistanceMachineInfo(resistanceMachineInfo: ResistanceMachineInfo)
    
    suspend fun deleteResistanceMachineInfoById(equipmentId: String)
    
    suspend fun deleteAllResistanceMachineInfo()
    
    suspend fun getResistanceMachineInfoCount(): Int
    
    suspend fun getResistanceMachineInfoCountByType(type: ResistanceMachineType): Int
} 