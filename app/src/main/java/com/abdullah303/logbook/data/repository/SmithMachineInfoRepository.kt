package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface SmithMachineInfoRepository {
    
    suspend fun insertSmithMachineInfo(smithMachineInfo: SmithMachineInfo)
    
    suspend fun insertAllSmithMachineInfo(smithMachineInfo: List<SmithMachineInfo>)
    
    suspend fun getSmithMachineInfoById(equipmentId: String): SmithMachineInfo?
    
    suspend fun getAllSmithMachineInfo(): List<SmithMachineInfo>
    
    fun getAllSmithMachineInfoFlow(): Flow<List<SmithMachineInfo>>
    
    fun getSmithMachineInfoByMinWeight(minWeight: BigDecimal): Flow<List<SmithMachineInfo>>
    
    fun getSmithMachineInfoByMaxWeight(maxWeight: BigDecimal): Flow<List<SmithMachineInfo>>
    
    suspend fun updateSmithMachineInfo(smithMachineInfo: SmithMachineInfo)
    
    suspend fun deleteSmithMachineInfo(smithMachineInfo: SmithMachineInfo)
    
    suspend fun deleteSmithMachineInfoById(equipmentId: String)
    
    suspend fun deleteAllSmithMachineInfo()
    
    suspend fun getSmithMachineInfoCount(): Int
    
    suspend fun getSmithMachineInfoCountByWeight(weight: BigDecimal): Int
} 