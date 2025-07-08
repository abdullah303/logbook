package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import kotlinx.coroutines.flow.Flow

interface PlateLoadedInfoRepository {
    
    suspend fun insertPlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo)
    
    suspend fun insertAllPlateLoadedInfo(plateLoadedInfo: List<PlateLoadedInfo>)
    
    suspend fun getPlateLoadedInfoById(resistanceMachineId: String): PlateLoadedInfo?
    
    suspend fun getAllPlateLoadedInfo(): List<PlateLoadedInfo>
    
    fun getAllPlateLoadedInfoFlow(): Flow<List<PlateLoadedInfo>>
    
    fun getPlateLoadedInfoByIdFlow(resistanceMachineId: String): Flow<PlateLoadedInfo?>
    
    suspend fun updatePlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo)
    
    suspend fun deletePlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo)
    
    suspend fun deletePlateLoadedInfoById(resistanceMachineId: String)
    
    suspend fun deleteAllPlateLoadedInfo()
    
    suspend fun getPlateLoadedInfoCount(): Int
    
    suspend fun exists(resistanceMachineId: String): Int
} 