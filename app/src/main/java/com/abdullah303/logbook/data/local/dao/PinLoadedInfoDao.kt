package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PinLoadedInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pinLoadedInfo: PinLoadedInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pinLoadedInfo: List<PinLoadedInfo>)
    
    @Query("SELECT * FROM pin_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun getById(resistanceMachineId: String): PinLoadedInfo?
    
    @Query("SELECT * FROM pin_loaded_info ORDER BY resistance_machine_id ASC")
    fun getAll(): Flow<List<PinLoadedInfo>>
    
    @Query("SELECT * FROM pin_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    fun getByResistanceMachineId(resistanceMachineId: String): Flow<PinLoadedInfo?>
    
    @Update
    suspend fun update(pinLoadedInfo: PinLoadedInfo)
    
    @Delete
    suspend fun delete(pinLoadedInfo: PinLoadedInfo)
    
    @Query("DELETE FROM pin_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun deleteById(resistanceMachineId: String)
    
    @Query("DELETE FROM pin_loaded_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM pin_loaded_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM pin_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun getCountByResistanceMachineId(resistanceMachineId: String): Int
} 