package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(workouts: List<Workout>)
    
    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getById(id: String): Workout?
    
    @Query("SELECT * FROM workouts WHERE id = :id")
    fun getByIdFlow(id: String): Flow<Workout?>
    
    @Query("SELECT * FROM workouts ORDER BY position ASC")
    suspend fun getAll(): List<Workout>
    
    @Query("SELECT * FROM workouts ORDER BY position ASC")
    fun getAllFlow(): Flow<List<Workout>>
    
    @Query("SELECT * FROM workouts WHERE split_id = :splitId ORDER BY position ASC")
    suspend fun getBySplitId(splitId: String): List<Workout>
    
    @Query("SELECT * FROM workouts WHERE split_id = :splitId ORDER BY position ASC")
    fun getBySplitIdFlow(splitId: String): Flow<List<Workout>>
    
    @Query("SELECT * FROM workouts WHERE name LIKE '%' || :searchQuery || '%' ORDER BY position ASC")
    suspend fun searchByName(searchQuery: String): List<Workout>
    
    @Query("SELECT * FROM workouts WHERE name LIKE '%' || :searchQuery || '%' ORDER BY position ASC")
    fun searchByNameFlow(searchQuery: String): Flow<List<Workout>>
    
    @Query("SELECT * FROM workouts WHERE split_id = :splitId AND name LIKE '%' || :searchQuery || '%' ORDER BY position ASC")
    suspend fun searchByNameInSplit(splitId: String, searchQuery: String): List<Workout>
    
    @Query("SELECT * FROM workouts WHERE split_id = :splitId AND name LIKE '%' || :searchQuery || '%' ORDER BY position ASC")
    fun searchByNameInSplitFlow(splitId: String, searchQuery: String): Flow<List<Workout>>
    
    @Update
    suspend fun update(workout: Workout)
    
    @Update
    suspend fun updateAll(workouts: List<Workout>)
    
    @Delete
    suspend fun delete(workout: Workout)
    
    @Delete
    suspend fun deleteAll(workouts: List<Workout>)
    
    @Query("DELETE FROM workouts WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM workouts WHERE split_id = :splitId")
    suspend fun deleteBySplitId(splitId: String)
    
    @Query("DELETE FROM workouts")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM workouts")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM workouts WHERE split_id = :splitId")
    suspend fun getCountBySplitId(splitId: String): Int
    
    @Query("SELECT COUNT(*) FROM workouts WHERE split_id = :splitId")
    fun getCountBySplitIdFlow(splitId: String): Flow<Int>
    
    @Query("SELECT MAX(position) FROM workouts WHERE split_id = :splitId")
    suspend fun getMaxPositionBySplitId(splitId: String): Int?
    
    @Query("SELECT MAX(position) FROM workouts WHERE split_id = :splitId")
    fun getMaxPositionBySplitIdFlow(splitId: String): Flow<Int?>
} 