package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.SupersetGroupDao
import com.abdullah303.logbook.data.local.entity.SupersetGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupersetGroupRepositoryImpl @Inject constructor(
    private val supersetGroupDao: SupersetGroupDao
) : SupersetGroupRepository {
    
    override suspend fun insertSupersetGroup(supersetGroup: SupersetGroup) {
        supersetGroupDao.insert(supersetGroup)
    }
    
    override suspend fun insertAllSupersetGroups(supersetGroupsList: List<SupersetGroup>) {
        supersetGroupDao.insertAll(supersetGroupsList)
    }
    
    override suspend fun getSupersetGroupById(id: String): SupersetGroup? {
        return supersetGroupDao.getById(id)
    }
    
    override fun getSupersetGroupByIdFlow(id: String): Flow<SupersetGroup?> {
        return supersetGroupDao.getByIdFlow(id)
    }
    
    override suspend fun getAllSupersetGroups(): List<SupersetGroup> {
        return supersetGroupDao.getAll()
    }
    
    override fun getAllSupersetGroupsFlow(): Flow<List<SupersetGroup>> {
        return supersetGroupDao.getAllFlow()
    }
    
    override suspend fun getSupersetGroupsByWorkoutId(workoutId: String): List<SupersetGroup> {
        return supersetGroupDao.getByWorkoutId(workoutId)
    }
    
    override fun getSupersetGroupsByWorkoutIdFlow(workoutId: String): Flow<List<SupersetGroup>> {
        return supersetGroupDao.getByWorkoutIdFlow(workoutId)
    }
    
    override suspend fun updateSupersetGroup(supersetGroup: SupersetGroup) {
        supersetGroupDao.update(supersetGroup)
    }
    
    override suspend fun updateAllSupersetGroups(supersetGroups: List<SupersetGroup>) {
        supersetGroupDao.updateAll(supersetGroups)
    }
    
    override suspend fun deleteSupersetGroup(supersetGroup: SupersetGroup) {
        supersetGroupDao.delete(supersetGroup)
    }
    
    override suspend fun deleteAllSupersetGroups(supersetGroups: List<SupersetGroup>) {
        supersetGroupDao.deleteAll(supersetGroups)
    }
    
    override suspend fun deleteSupersetGroupById(id: String) {
        supersetGroupDao.deleteById(id)
    }
    
    override suspend fun deleteSupersetGroupsByWorkoutId(workoutId: String) {
        supersetGroupDao.deleteByWorkoutId(workoutId)
    }
    
    override suspend fun deleteAllSupersetGroups() {
        supersetGroupDao.deleteAll()
    }
    
    override suspend fun getSupersetGroupsCount(): Int {
        return supersetGroupDao.getCount()
    }
    
    override suspend fun getSupersetGroupsCountByWorkoutId(workoutId: String): Int {
        return supersetGroupDao.getCountByWorkoutId(workoutId)
    }
    
    override fun getSupersetGroupsCountByWorkoutIdFlow(workoutId: String): Flow<Int> {
        return supersetGroupDao.getCountByWorkoutIdFlow(workoutId)
    }
} 