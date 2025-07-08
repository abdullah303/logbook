package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.DumbbellRange
import kotlinx.coroutines.flow.Flow

interface DumbbellRangeRepository {
    
    suspend fun insertDumbbellRange(dumbbellRange: DumbbellRange)
    
    suspend fun insertAllDumbbellRanges(dumbbellRanges: List<DumbbellRange>)
    
    suspend fun getDumbbellRangeById(id: String): DumbbellRange?
    
    fun getDumbbellRangeByIdFlow(id: String): Flow<DumbbellRange?>
    
    suspend fun getDumbbellRangesBySettingsId(settingsId: String): List<DumbbellRange>
    
    fun getDumbbellRangesBySettingsIdFlow(settingsId: String): Flow<List<DumbbellRange>>
    
    suspend fun getAllDumbbellRanges(): List<DumbbellRange>
    
    fun getAllDumbbellRangesFlow(): Flow<List<DumbbellRange>>
    
    suspend fun updateDumbbellRange(dumbbellRange: DumbbellRange)
    
    suspend fun updateAllDumbbellRanges(dumbbellRanges: List<DumbbellRange>)
    
    suspend fun deleteDumbbellRange(dumbbellRange: DumbbellRange)
    
    suspend fun deleteDumbbellRangeById(id: String)
    
    suspend fun deleteDumbbellRangesBySettingsId(settingsId: String)
    
    suspend fun deleteAllDumbbellRanges()
    
    suspend fun getDumbbellRangeCount(): Int
    
    fun getDumbbellRangeCountFlow(): Flow<Int>
    
    suspend fun getDumbbellRangeCountBySettingsId(settingsId: String): Int
    
    fun getDumbbellRangeCountBySettingsIdFlow(settingsId: String): Flow<Int>
} 