package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(equipment: Equipment)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipment: List<Equipment>)
    
    @Query("SELECT * FROM equipment WHERE id = :id")
    suspend fun getById(id: String): Equipment?
    
    @Query("SELECT * FROM equipment ORDER BY name ASC")
    fun getAll(): Flow<List<Equipment>>
    
    @Query("SELECT * FROM equipment WHERE equipmentType = :equipmentType ORDER BY name ASC")
    fun getByType(equipmentType: EquipmentType): Flow<List<Equipment>>
    
    @Query("SELECT * FROM equipment WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchByName(searchQuery: String): Flow<List<Equipment>>
    
    @Update
    suspend fun update(equipment: Equipment)
    
    @Delete
    suspend fun delete(equipment: Equipment)
    
    @Query("DELETE FROM equipment WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM equipment")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM equipment")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM equipment WHERE equipmentType = :equipmentType")
    suspend fun getCountByType(equipmentType: EquipmentType): Int
} 