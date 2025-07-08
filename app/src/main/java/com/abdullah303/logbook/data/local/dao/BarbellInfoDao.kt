package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbellInfoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barbellInfo: BarbellInfo)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(barbellInfo: List<BarbellInfo>)
    
    @Query("SELECT * FROM barbell_info WHERE equipment_id = :equipmentId")
    suspend fun getById(equipmentId: String): BarbellInfo?
    
    @Query("SELECT * FROM barbell_info ORDER BY equipment_id ASC")
    fun getAll(): Flow<List<BarbellInfo>>
    
    @Query("SELECT * FROM barbell_info WHERE bar_weight >= :minWeight ORDER BY bar_weight ASC")
    fun getByMinWeight(minWeight: java.math.BigDecimal): Flow<List<BarbellInfo>>
    
    @Query("SELECT * FROM barbell_info WHERE bar_weight <= :maxWeight ORDER BY bar_weight DESC")
    fun getByMaxWeight(maxWeight: java.math.BigDecimal): Flow<List<BarbellInfo>>
    
    @Update
    suspend fun update(barbellInfo: BarbellInfo)
    
    @Delete
    suspend fun delete(barbellInfo: BarbellInfo)
    
    @Query("DELETE FROM barbell_info WHERE equipment_id = :equipmentId")
    suspend fun deleteById(equipmentId: String)
    
    @Query("DELETE FROM barbell_info")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM barbell_info")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM barbell_info WHERE bar_weight = :weight")
    suspend fun getCountByWeight(weight: java.math.BigDecimal): Int
} 