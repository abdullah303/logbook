package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.SupersetGroup
import kotlinx.coroutines.flow.Flow

interface SupersetGroupRepository {
    
    suspend fun insertSupersetGroup(supersetGroup: SupersetGroup)
    
    suspend fun insertAllSupersetGroups(supersetGroupsList: List<SupersetGroup>)
    
    suspend fun getSupersetGroupById(id: String): SupersetGroup?
    
    fun getSupersetGroupByIdFlow(id: String): Flow<SupersetGroup?>
    
    suspend fun getAllSupersetGroups(): List<SupersetGroup>
    
    fun getAllSupersetGroupsFlow(): Flow<List<SupersetGroup>>
    
    suspend fun getSupersetGroupsByWorkoutId(workoutId: String): List<SupersetGroup>
    
    fun getSupersetGroupsByWorkoutIdFlow(workoutId: String): Flow<List<SupersetGroup>>
    
    suspend fun updateSupersetGroup(supersetGroup: SupersetGroup)
    
    suspend fun updateAllSupersetGroups(supersetGroups: List<SupersetGroup>)
    
    suspend fun deleteSupersetGroup(supersetGroup: SupersetGroup)
    
    suspend fun deleteAllSupersetGroups(supersetGroups: List<SupersetGroup>)
    
    suspend fun deleteSupersetGroupById(id: String)
    
    suspend fun deleteSupersetGroupsByWorkoutId(workoutId: String)
    
    suspend fun deleteAllSupersetGroups()
    
    suspend fun getSupersetGroupsCount(): Int
    
    suspend fun getSupersetGroupsCountByWorkoutId(workoutId: String): Int
    
    fun getSupersetGroupsCountByWorkoutIdFlow(workoutId: String): Flow<Int>
} 