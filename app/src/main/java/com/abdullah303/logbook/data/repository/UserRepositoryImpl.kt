package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.UserDao
import com.abdullah303.logbook.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    
    override suspend fun insertUser(user: User) {
        userDao.insert(user)
    }
    
    override suspend fun insertAllUsers(users: List<User>) {
        userDao.insertAll(users)
    }
    
    override suspend fun getUserById(id: String): User? {
        return userDao.getById(id)
    }
    
    override fun getUserByIdFlow(id: String): Flow<User?> {
        return userDao.getByIdFlow(id)
    }
    
    override suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }
    
    override fun getAllUsersFlow(): Flow<List<User>> {
        return userDao.getAllFlow()
    }
    
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getByUsername(username)
    }
    
    override fun getUserByUsernameFlow(username: String): Flow<User?> {
        return userDao.getByUsernameFlow(username)
    }
    
    override suspend fun updateUser(user: User) {
        userDao.update(user)
    }
    
    override suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }
    
    override suspend fun deleteUserById(id: String) {
        userDao.deleteById(id)
    }
    
    override suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
    
    override suspend fun getUserCount(): Int {
        return userDao.getCount()
    }
    
    override fun getUserCountFlow(): Flow<Int> {
        return userDao.getCountFlow()
    }
} 