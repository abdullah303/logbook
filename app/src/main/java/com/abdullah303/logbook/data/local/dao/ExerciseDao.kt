package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<Exercise>)
    
    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getById(id: String): Exercise?
    
    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getByIdFlow(id: String): Flow<Exercise?>
    
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    suspend fun getAll(): List<Exercise>
    
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllFlow(): Flow<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE equipment_id = :equipmentId ORDER BY name ASC")
    suspend fun getByEquipmentId(equipmentId: String): List<Exercise>
    
    @Query("SELECT * FROM exercises WHERE equipment_id = :equipmentId ORDER BY name ASC")
    fun getByEquipmentIdFlow(equipmentId: String): Flow<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    suspend fun searchByName(searchQuery: String): List<Exercise>
    
    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchByNameFlow(searchQuery: String): Flow<List<Exercise>>
    
    @Update
    suspend fun update(exercise: Exercise)
    
    @Update
    suspend fun updateAll(exercises: List<Exercise>)
    
    @Delete
    suspend fun delete(exercise: Exercise)
    
    @Delete
    suspend fun deleteAll(exercises: List<Exercise>)
    
    @Query("DELETE FROM exercises WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM exercises WHERE equipment_id = :equipmentId")
    suspend fun deleteByEquipmentId(equipmentId: String)
    
    @Query("DELETE FROM exercises")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM exercises WHERE equipment_id = :equipmentId")
    suspend fun getCountByEquipmentId(equipmentId: String): Int
} 