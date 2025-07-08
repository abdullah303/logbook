package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(settingsList: List<Settings>)
    
    @Query("SELECT * FROM settings WHERE id = :id")
    suspend fun getById(id: String): Settings?
    
    @Query("SELECT * FROM settings WHERE id = :id")
    fun getByIdFlow(id: String): Flow<Settings?>
    
    @Query("SELECT * FROM settings WHERE userId = :userId")
    suspend fun getByUserId(userId: String): Settings?
    
    @Query("SELECT * FROM settings WHERE userId = :userId")
    fun getByUserIdFlow(userId: String): Flow<Settings?>
    
    @Query("SELECT * FROM settings")
    suspend fun getAll(): List<Settings>
    
    @Query("SELECT * FROM settings")
    fun getAllFlow(): Flow<List<Settings>>
    
    @Update
    suspend fun update(settings: Settings)
    
    @Delete
    suspend fun delete(settings: Settings)
    
    @Query("DELETE FROM settings WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM settings WHERE userId = :userId")
    suspend fun deleteByUserId(userId: String)
    
    @Query("DELETE FROM settings")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM settings")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM settings")
    fun getCountFlow(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM settings WHERE userId = :userId")
    suspend fun getCountByUserId(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM settings WHERE userId = :userId")
    fun getCountByUserIdFlow(userId: String): Flow<Int>
} 