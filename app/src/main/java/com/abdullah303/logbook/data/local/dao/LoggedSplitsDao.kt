package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.LoggedSplits
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedSplitsDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loggedSplit: LoggedSplits)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(loggedSplits: List<LoggedSplits>)
    
    @Query("SELECT * FROM logged_splits WHERE id = :id")
    suspend fun getById(id: String): LoggedSplits?
    
    @Query("SELECT * FROM logged_splits WHERE id = :id")
    fun getByIdFlow(id: String): Flow<LoggedSplits?>
    
    @Query("SELECT * FROM logged_splits ORDER BY started_at DESC")
    suspend fun getAll(): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits ORDER BY started_at DESC")
    fun getAllFlow(): Flow<List<LoggedSplits>>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId ORDER BY started_at DESC")
    suspend fun getBySplitId(splitId: String): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId ORDER BY started_at DESC")
    fun getBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND run_number = :runNumber")
    suspend fun getBySplitIdAndRunNumber(splitId: String, runNumber: Int): LoggedSplits?
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND run_number = :runNumber")
    fun getBySplitIdAndRunNumberFlow(splitId: String, runNumber: Int): Flow<LoggedSplits?>
    
    @Query("SELECT * FROM logged_splits WHERE completed_at IS NULL ORDER BY started_at DESC")
    suspend fun getInProgress(): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits WHERE completed_at IS NULL ORDER BY started_at DESC")
    fun getInProgressFlow(): Flow<List<LoggedSplits>>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND completed_at IS NULL ORDER BY started_at DESC")
    suspend fun getInProgressBySplitId(splitId: String): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND completed_at IS NULL ORDER BY started_at DESC")
    fun getInProgressBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    @Query("SELECT * FROM logged_splits WHERE completed_at IS NOT NULL ORDER BY completed_at DESC")
    suspend fun getCompleted(): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits WHERE completed_at IS NOT NULL ORDER BY completed_at DESC")
    fun getCompletedFlow(): Flow<List<LoggedSplits>>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND completed_at IS NOT NULL ORDER BY completed_at DESC")
    suspend fun getCompletedBySplitId(splitId: String): List<LoggedSplits>
    
    @Query("SELECT * FROM logged_splits WHERE split_id = :splitId AND completed_at IS NOT NULL ORDER BY completed_at DESC")
    fun getCompletedBySplitIdFlow(splitId: String): Flow<List<LoggedSplits>>
    
    @Update
    suspend fun update(loggedSplit: LoggedSplits)
    
    @Update
    suspend fun updateAll(loggedSplits: List<LoggedSplits>)
    
    @Delete
    suspend fun delete(loggedSplit: LoggedSplits)
    
    @Delete
    suspend fun deleteAll(loggedSplits: List<LoggedSplits>)
    
    @Query("DELETE FROM logged_splits WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM logged_splits WHERE split_id = :splitId")
    suspend fun deleteBySplitId(splitId: String)
    
    @Query("DELETE FROM logged_splits")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM logged_splits")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM logged_splits WHERE split_id = :splitId")
    suspend fun getCountBySplitId(splitId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_splits WHERE split_id = :splitId")
    fun getCountBySplitIdFlow(splitId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM logged_splits WHERE split_id = :splitId AND completed_at IS NOT NULL")
    suspend fun getCompletedCountBySplitId(splitId: String): Int
    
    @Query("SELECT COUNT(*) FROM logged_splits WHERE split_id = :splitId AND completed_at IS NOT NULL")
    fun getCompletedCountBySplitIdFlow(splitId: String): Flow<Int>
    
    @Query("SELECT MAX(run_number) FROM logged_splits WHERE split_id = :splitId")
    suspend fun getMaxRunNumberBySplitId(splitId: String): Int?
    
    @Query("SELECT MAX(run_number) FROM logged_splits WHERE split_id = :splitId")
    fun getMaxRunNumberBySplitIdFlow(splitId: String): Flow<Int?>
} 