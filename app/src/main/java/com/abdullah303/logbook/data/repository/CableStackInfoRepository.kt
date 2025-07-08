package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.CableStackInfo
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface CableStackInfoRepository {
    
    suspend fun insertCableStackInfo(cableStackInfo: CableStackInfo)
    
    suspend fun insertAllCableStackInfo(cableStackInfos: List<CableStackInfo>)
    
    suspend fun getCableStackInfoById(equipmentId: String): CableStackInfo?
    
    fun getCableStackInfoByIdFlow(equipmentId: String): Flow<CableStackInfo?>
    
    suspend fun getAllCableStackInfo(): List<CableStackInfo>
    
    fun getAllCableStackInfoFlow(): Flow<List<CableStackInfo>>
    
    suspend fun getCableStackInfoByWeightRange(weight: BigDecimal): List<CableStackInfo>
    
    fun getCableStackInfoByWeightRangeFlow(weight: BigDecimal): Flow<List<CableStackInfo>>
    
    suspend fun updateCableStackInfo(cableStackInfo: CableStackInfo)
    
    suspend fun updateAllCableStackInfo(cableStackInfos: List<CableStackInfo>)
    
    suspend fun deleteCableStackInfo(cableStackInfo: CableStackInfo)
    
    suspend fun deleteCableStackInfoById(equipmentId: String)
    
    suspend fun deleteAllCableStackInfo()
    
    suspend fun getCableStackInfoCount(): Int
    
    fun getCableStackInfoCountFlow(): Flow<Int>
    
    suspend fun exists(equipmentId: String): Boolean
    
    fun existsFlow(equipmentId: String): Flow<Boolean>
} 