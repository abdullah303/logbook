package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.DumbbellRange
import kotlinx.coroutines.flow.Flow

@Dao
interface DumbbellRangeDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dumbbellRange: DumbbellRange)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dumbbellRanges: List<DumbbellRange>)
    
    @Query("SELECT * FROM dumbbell_ranges WHERE id = :id")
    suspend fun getById(id: String): DumbbellRange?
    
    @Query("SELECT * FROM dumbbell_ranges WHERE id = :id")
    fun getByIdFlow(id: String): Flow<DumbbellRange?>
    
    @Query("SELECT * FROM dumbbell_ranges WHERE settingsId = :settingsId")
    suspend fun getBySettingsId(settingsId: String): List<DumbbellRange>
    
    @Query("SELECT * FROM dumbbell_ranges WHERE settingsId = :settingsId")
    fun getBySettingsIdFlow(settingsId: String): Flow<List<DumbbellRange>>
    
    @Query("SELECT * FROM dumbbell_ranges")
    suspend fun getAll(): List<DumbbellRange>
    
    @Query("SELECT * FROM dumbbell_ranges")
    fun getAllFlow(): Flow<List<DumbbellRange>>
    
    @Update
    suspend fun update(dumbbellRange: DumbbellRange)
    
    @Update
    suspend fun updateAll(dumbbellRanges: List<DumbbellRange>)
    
    @Delete
    suspend fun delete(dumbbellRange: DumbbellRange)
    
    @Query("DELETE FROM dumbbell_ranges WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM dumbbell_ranges WHERE settingsId = :settingsId")
    suspend fun deleteBySettingsId(settingsId: String)
    
    @Query("DELETE FROM dumbbell_ranges")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM dumbbell_ranges")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM dumbbell_ranges")
    fun getCountFlow(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM dumbbell_ranges WHERE settingsId = :settingsId")
    suspend fun getCountBySettingsId(settingsId: String): Int
    
    @Query("SELECT COUNT(*) FROM dumbbell_ranges WHERE settingsId = :settingsId")
    fun getCountBySettingsIdFlow(settingsId: String): Flow<Int>
} 