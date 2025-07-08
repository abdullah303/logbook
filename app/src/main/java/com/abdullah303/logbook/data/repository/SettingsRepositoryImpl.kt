package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.SettingsDao
import com.abdullah303.logbook.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {
    
    override suspend fun insertSettings(settings: Settings) {
        settingsDao.insert(settings)
    }
    
    override suspend fun insertAllSettings(settingsList: List<Settings>) {
        settingsDao.insertAll(settingsList)
    }
    
    override suspend fun getSettingsById(id: String): Settings? {
        return settingsDao.getById(id)
    }
    
    override fun getSettingsByIdFlow(id: String): Flow<Settings?> {
        return settingsDao.getByIdFlow(id)
    }
    
    override suspend fun getSettingsByUserId(userId: String): Settings? {
        return settingsDao.getByUserId(userId)
    }
    
    override fun getSettingsByUserIdFlow(userId: String): Flow<Settings?> {
        return settingsDao.getByUserIdFlow(userId)
    }
    
    override suspend fun getAllSettings(): List<Settings> {
        return settingsDao.getAll()
    }
    
    override fun getAllSettingsFlow(): Flow<List<Settings>> {
        return settingsDao.getAllFlow()
    }
    
    override suspend fun updateSettings(settings: Settings) {
        settingsDao.update(settings)
    }
    
    override suspend fun deleteSettings(settings: Settings) {
        settingsDao.delete(settings)
    }
    
    override suspend fun deleteSettingsById(id: String) {
        settingsDao.deleteById(id)
    }
    
    override suspend fun deleteSettingsByUserId(userId: String) {
        settingsDao.deleteByUserId(userId)
    }
    
    override suspend fun deleteAllSettings() {
        settingsDao.deleteAll()
    }
    
    override suspend fun getSettingsCount(): Int {
        return settingsDao.getCount()
    }
    
    override fun getSettingsCountFlow(): Flow<Int> {
        return settingsDao.getCountFlow()
    }
    
    override suspend fun getSettingsCountByUserId(userId: String): Int {
        return settingsDao.getCountByUserId(userId)
    }
    
    override fun getSettingsCountByUserIdFlow(userId: String): Flow<Int> {
        return settingsDao.getCountByUserIdFlow(userId)
    }
} 