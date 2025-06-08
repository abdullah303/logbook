package com.abdullah303.logbook.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment ORDER BY name ASC")
    fun getAllEquipment(): Flow<List<EquipmentEntity>>
    
    @Query("SELECT * FROM equipment WHERE id = :id")
    suspend fun getEquipmentById(id: String): EquipmentEntity?
    
    @Query("SELECT * FROM equipment WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchEquipment(searchQuery: String): Flow<List<EquipmentEntity>>
    
    @Query("SELECT * FROM equipment WHERE type = :type ORDER BY name ASC")
    fun getEquipmentByType(type: String): Flow<List<EquipmentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(equipment: EquipmentEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipmentList(equipment: List<EquipmentEntity>)
    
    @Update
    suspend fun updateEquipment(equipment: EquipmentEntity)
    
    @Delete
    suspend fun deleteEquipment(equipment: EquipmentEntity)
    
    @Query("DELETE FROM equipment WHERE id = :id")
    suspend fun deleteEquipmentById(id: String)
    
    @Query("DELETE FROM equipment")
    suspend fun deleteAllEquipment()
    
    @Query("SELECT COUNT(*) FROM equipment")
    suspend fun getEquipmentCount(): Int
} 