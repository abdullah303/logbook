package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PlateLoadedInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plateLoadedInfo: PlateLoadedInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plateLoadedInfo: List<PlateLoadedInfo>)
    
    @Query("SELECT * FROM plate_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun getById(resistanceMachineId: String): PlateLoadedInfo?
    
    @Query("SELECT * FROM plate_loaded_info ORDER BY resistance_machine_id ASC")
    fun getAll(): Flow<List<PlateLoadedInfo>>
    
    @Query("SELECT * FROM plate_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    fun getByIdFlow(resistanceMachineId: String): Flow<PlateLoadedInfo?>
    
    @Update
    suspend fun update(plateLoadedInfo: PlateLoadedInfo)
    
    @Delete
    suspend fun delete(plateLoadedInfo: PlateLoadedInfo)
    
    @Query("DELETE FROM plate_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun deleteById(resistanceMachineId: String)
    
    @Query("DELETE FROM plate_loaded_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM plate_loaded_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM plate_loaded_info WHERE resistance_machine_id = :resistanceMachineId")
    suspend fun exists(resistanceMachineId: String): Int
} 