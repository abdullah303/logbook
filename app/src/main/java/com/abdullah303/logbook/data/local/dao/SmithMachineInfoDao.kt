package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SmithMachineInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(smithMachineInfo: SmithMachineInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(smithMachineInfo: List<SmithMachineInfo>)
    
    @Query("SELECT * FROM smith_machine_info WHERE equipment_id = :equipmentId")
    suspend fun getById(equipmentId: String): SmithMachineInfo?
    
    @Query("SELECT * FROM smith_machine_info ORDER BY equipment_id ASC")
    fun getAll(): Flow<List<SmithMachineInfo>>
    
    @Query("SELECT * FROM smith_machine_info WHERE bar_weight >= :minWeight ORDER BY bar_weight ASC")
    fun getByMinWeight(minWeight: java.math.BigDecimal): Flow<List<SmithMachineInfo>>
    
    @Query("SELECT * FROM smith_machine_info WHERE bar_weight <= :maxWeight ORDER BY bar_weight DESC")
    fun getByMaxWeight(maxWeight: java.math.BigDecimal): Flow<List<SmithMachineInfo>>
    
    @Update
    suspend fun update(smithMachineInfo: SmithMachineInfo)
    
    @Delete
    suspend fun delete(smithMachineInfo: SmithMachineInfo)
    
    @Query("DELETE FROM smith_machine_info WHERE equipment_id = :equipmentId")
    suspend fun deleteById(equipmentId: String)
    
    @Query("DELETE FROM smith_machine_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM smith_machine_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM smith_machine_info WHERE bar_weight = :weight")
    suspend fun getCountByWeight(weight: java.math.BigDecimal): Int
} 