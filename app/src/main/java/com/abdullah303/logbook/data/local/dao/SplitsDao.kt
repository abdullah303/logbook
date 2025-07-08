package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.Splits
import kotlinx.coroutines.flow.Flow

@Dao
interface SplitsDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(splits: Splits)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(splits: List<Splits>)
    
    @Query("SELECT * FROM splits WHERE id = :id")
    suspend fun getById(id: String): Splits?
    
    @Query("SELECT * FROM splits WHERE id = :id")
    fun getByIdFlow(id: String): Flow<Splits?>
    
    @Query("SELECT * FROM splits")
    suspend fun getAll(): List<Splits>
    
    @Query("SELECT * FROM splits")
    fun getAllFlow(): Flow<List<Splits>>
    
    @Query("SELECT * FROM splits WHERE user_id = :userId")
    suspend fun getByUserId(userId: String): List<Splits>
    
    @Query("SELECT * FROM splits WHERE user_id = :userId")
    fun getByUserIdFlow(userId: String): Flow<List<Splits>>
    
    @Query("SELECT * FROM splits WHERE user_id = :userId AND name LIKE '%' || :searchQuery || '%'")
    fun searchByName(userId: String, searchQuery: String): Flow<List<Splits>>
    
    @Update
    suspend fun update(splits: Splits)
    
    @Delete
    suspend fun delete(splits: Splits)
    
    @Query("DELETE FROM splits WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM splits WHERE user_id = :userId")
    suspend fun deleteByUserId(userId: String)
    
    @Query("DELETE FROM splits")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM splits")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM splits WHERE user_id = :userId")
    suspend fun getCountByUserId(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM splits WHERE user_id = :userId")
    fun getCountByUserIdFlow(userId: String): Flow<Int>
} 