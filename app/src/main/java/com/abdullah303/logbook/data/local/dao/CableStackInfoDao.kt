package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CableStackInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cableStackInfo: CableStackInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cableStackInfos: List<CableStackInfo>)
    
    @Query("SELECT * FROM cable_stack_info WHERE equipment_id = :equipmentId")
    suspend fun getById(equipmentId: String): CableStackInfo?
    
    @Query("SELECT * FROM cable_stack_info WHERE equipment_id = :equipmentId")
    fun getByIdFlow(equipmentId: String): Flow<CableStackInfo?>
    
    @Query("SELECT * FROM cable_stack_info")
    suspend fun getAll(): List<CableStackInfo>
    
    @Query("SELECT * FROM cable_stack_info")
    fun getAllFlow(): Flow<List<CableStackInfo>>
    
    @Query("SELECT * FROM cable_stack_info WHERE min_weight <= :weight AND max_weight >= :weight")
    suspend fun getByWeightRange(weight: java.math.BigDecimal): List<CableStackInfo>
    
    @Query("SELECT * FROM cable_stack_info WHERE min_weight <= :weight AND max_weight >= :weight")
    fun getByWeightRangeFlow(weight: java.math.BigDecimal): Flow<List<CableStackInfo>>
    
    @Update
    suspend fun update(cableStackInfo: CableStackInfo)
    
    @Update
    suspend fun updateAll(cableStackInfos: List<CableStackInfo>)
    
    @Delete
    suspend fun delete(cableStackInfo: CableStackInfo)
    
    @Query("DELETE FROM cable_stack_info WHERE equipment_id = :equipmentId")
    suspend fun deleteById(equipmentId: String)
    
    @Query("DELETE FROM cable_stack_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM cable_stack_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM cable_stack_info")
    fun getCountFlow(): Flow<Int>
    
    @Query("SELECT EXISTS(SELECT 1 FROM cable_stack_info WHERE equipment_id = :equipmentId)")
    suspend fun exists(equipmentId: String): Boolean
    
    @Query("SELECT EXISTS(SELECT 1 FROM cable_stack_info WHERE equipment_id = :equipmentId)")
    fun existsFlow(equipmentId: String): Flow<Boolean>
} 