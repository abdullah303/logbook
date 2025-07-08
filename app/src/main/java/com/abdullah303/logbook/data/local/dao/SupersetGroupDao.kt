package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.SupersetGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface SupersetGroupDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supersetGroup: SupersetGroup)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(supersetGroups: List<SupersetGroup>)
    
    @Query("SELECT * FROM superset_groups WHERE id = :id")
    suspend fun getById(id: String): SupersetGroup?
    
    @Query("SELECT * FROM superset_groups WHERE id = :id")
    fun getByIdFlow(id: String): Flow<SupersetGroup?>
    
    @Query("SELECT * FROM superset_groups ORDER BY id ASC")
    suspend fun getAll(): List<SupersetGroup>
    
    @Query("SELECT * FROM superset_groups ORDER BY id ASC")
    fun getAllFlow(): Flow<List<SupersetGroup>>
    
    @Query("SELECT * FROM superset_groups WHERE workout_id = :workoutId ORDER BY id ASC")
    suspend fun getByWorkoutId(workoutId: String): List<SupersetGroup>
    
    @Query("SELECT * FROM superset_groups WHERE workout_id = :workoutId ORDER BY id ASC")
    fun getByWorkoutIdFlow(workoutId: String): Flow<List<SupersetGroup>>
    
    @Update
    suspend fun update(supersetGroup: SupersetGroup)
    
    @Update
    suspend fun updateAll(supersetGroups: List<SupersetGroup>)
    
    @Delete
    suspend fun delete(supersetGroup: SupersetGroup)
    
    @Delete
    suspend fun deleteAll(supersetGroups: List<SupersetGroup>)
    
    @Query("DELETE FROM superset_groups WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM superset_groups WHERE workout_id = :workoutId")
    suspend fun deleteByWorkoutId(workoutId: String)
    
    @Query("DELETE FROM superset_groups")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM superset_groups")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM superset_groups WHERE workout_id = :workoutId")
    suspend fun getCountByWorkoutId(workoutId: String): Int
    
    @Query("SELECT COUNT(*) FROM superset_groups WHERE workout_id = :workoutId")
    fun getCountByWorkoutIdFlow(workoutId: String): Flow<Int>
} 