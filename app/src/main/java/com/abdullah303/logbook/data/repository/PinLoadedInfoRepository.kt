package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import kotlinx.coroutines.flow.Flow

interface PinLoadedInfoRepository {
    
    suspend fun insertPinLoadedInfo(pinLoadedInfo: PinLoadedInfo)
    
    suspend fun insertAllPinLoadedInfo(pinLoadedInfo: List<PinLoadedInfo>)
    
    suspend fun getPinLoadedInfoById(resistanceMachineId: String): PinLoadedInfo?
    
    suspend fun getAllPinLoadedInfo(): List<PinLoadedInfo>
    
    fun getAllPinLoadedInfoFlow(): Flow<List<PinLoadedInfo>>
    
    fun getPinLoadedInfoByResistanceMachineId(resistanceMachineId: String): Flow<PinLoadedInfo?>
    
    suspend fun updatePinLoadedInfo(pinLoadedInfo: PinLoadedInfo)
    
    suspend fun deletePinLoadedInfo(pinLoadedInfo: PinLoadedInfo)
    
    suspend fun deletePinLoadedInfoById(resistanceMachineId: String)
    
    suspend fun deleteAllPinLoadedInfo()
    
    suspend fun getPinLoadedInfoCount(): Int
    
    suspend fun getPinLoadedInfoCountByResistanceMachineId(resistanceMachineId: String): Int
} 