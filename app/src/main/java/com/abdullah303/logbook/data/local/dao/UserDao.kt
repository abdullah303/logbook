package com.abdullah303.logbook.data.local.dao

import androidx.room.*
import com.abdullah303.logbook.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)
    
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: String): User?
    
    @Query("SELECT * FROM users WHERE id = :id")
    fun getByIdFlow(id: String): Flow<User?>
    
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>
    
    @Query("SELECT * FROM users")
    fun getAllFlow(): Flow<List<User>>
    
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getByUsername(username: String): User?
    
    @Query("SELECT * FROM users WHERE username = :username")
    fun getByUsernameFlow(username: String): Flow<User?>
    
    @Update
    suspend fun update(user: User)
    
    @Delete
    suspend fun delete(user: User)
    
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM users")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM users")
    fun getCountFlow(): Flow<Int>
} 