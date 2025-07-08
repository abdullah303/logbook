package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    
    suspend fun insertUser(user: User)
    
    suspend fun insertAllUsers(users: List<User>)
    
    suspend fun getUserById(id: String): User?
    
    fun getUserByIdFlow(id: String): Flow<User?>
    
    suspend fun getAllUsers(): List<User>
    
    fun getAllUsersFlow(): Flow<List<User>>
    
    suspend fun getUserByUsername(username: String): User?
    
    fun getUserByUsernameFlow(username: String): Flow<User?>
    
    suspend fun updateUser(user: User)
    
    suspend fun deleteUser(user: User)
    
    suspend fun deleteUserById(id: String)
    
    suspend fun deleteAllUsers()
    
    suspend fun getUserCount(): Int
    
    fun getUserCountFlow(): Flow<Int>
} 