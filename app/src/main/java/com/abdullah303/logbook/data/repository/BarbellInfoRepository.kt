package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.BarbellInfo
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface BarbellInfoRepository {
    
    suspend fun insertBarbellInfo(barbellInfo: BarbellInfo)
    
    suspend fun insertAllBarbellInfo(barbellInfo: List<BarbellInfo>)
    
    suspend fun getBarbellInfoById(equipmentId: String): BarbellInfo?
    
    suspend fun getAllBarbellInfo(): List<BarbellInfo>
    
    fun getAllBarbellInfoFlow(): Flow<List<BarbellInfo>>
    
    fun getBarbellInfoByMinWeight(minWeight: BigDecimal): Flow<List<BarbellInfo>>
    
    fun getBarbellInfoByMaxWeight(maxWeight: BigDecimal): Flow<List<BarbellInfo>>
    
    suspend fun updateBarbellInfo(barbellInfo: BarbellInfo)
    
    suspend fun deleteBarbellInfo(barbellInfo: BarbellInfo)
    
    suspend fun deleteBarbellInfoById(equipmentId: String)
    
    suspend fun deleteAllBarbellInfo()
    
    suspend fun getBarbellInfoCount(): Int
    
    suspend fun getBarbellInfoCountByWeight(weight: BigDecimal): Int
} 