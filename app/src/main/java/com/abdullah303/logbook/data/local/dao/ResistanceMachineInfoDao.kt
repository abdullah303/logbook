package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.model.ResistanceMachineType
import kotlinx.coroutines.flow.Flow

@Dao
interface ResistanceMachineInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resistanceMachineInfo: ResistanceMachineInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resistanceMachineInfo: List<ResistanceMachineInfo>)
    
    @Query("SELECT * FROM resistance_machine_info WHERE equipment_id = :equipmentId")
    suspend fun getById(equipmentId: String): ResistanceMachineInfo?
    
    @Query("SELECT * FROM resistance_machine_info ORDER BY equipment_id ASC")
    fun getAll(): Flow<List<ResistanceMachineInfo>>
    
    @Query("SELECT * FROM resistance_machine_info WHERE type = :type ORDER BY equipment_id ASC")
    fun getByType(type: ResistanceMachineType): Flow<List<ResistanceMachineInfo>>
    
    @Update
    suspend fun update(resistanceMachineInfo: ResistanceMachineInfo)
    
    @Delete
    suspend fun delete(resistanceMachineInfo: ResistanceMachineInfo)
    
    @Query("DELETE FROM resistance_machine_info WHERE equipment_id = :equipmentId")
    suspend fun deleteById(equipmentId: String)
    
    @Query("DELETE FROM resistance_machine_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM resistance_machine_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM resistance_machine_info WHERE type = :type")
    suspend fun getCountByType(type: ResistanceMachineType): Int
} 