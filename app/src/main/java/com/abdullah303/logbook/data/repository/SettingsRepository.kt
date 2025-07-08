package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    
    suspend fun insertSettings(settings: Settings)
    
    suspend fun insertAllSettings(settingsList: List<Settings>)
    
    suspend fun getSettingsById(id: String): Settings?
    
    fun getSettingsByIdFlow(id: String): Flow<Settings?>
    
    suspend fun getSettingsByUserId(userId: String): Settings?
    
    fun getSettingsByUserIdFlow(userId: String): Flow<Settings?>
    
    suspend fun getAllSettings(): List<Settings>
    
    fun getAllSettingsFlow(): Flow<List<Settings>>
    
    suspend fun updateSettings(settings: Settings)
    
    suspend fun deleteSettings(settings: Settings)
    
    suspend fun deleteSettingsById(id: String)
    
    suspend fun deleteSettingsByUserId(userId: String)
    
    suspend fun deleteAllSettings()
    
    suspend fun getSettingsCount(): Int
    
    fun getSettingsCountFlow(): Flow<Int>
    
    suspend fun getSettingsCountByUserId(userId: String): Int
    
    fun getSettingsCountByUserIdFlow(userId: String): Flow<Int>
} 