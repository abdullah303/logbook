package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository {
    
    suspend fun insertEquipment(equipment: Equipment)
    
    suspend fun insertAllEquipment(equipment: List<Equipment>)
    
    suspend fun getEquipmentById(id: String): Equipment?
    
    suspend fun getAllEquipment(): List<Equipment>
    
    fun getAllEquipmentFlow(): Flow<List<Equipment>>
    
    fun getEquipmentByType(equipmentType: EquipmentType): Flow<List<Equipment>>
    
    fun searchEquipmentByName(searchQuery: String): Flow<List<Equipment>>
    
    suspend fun updateEquipment(equipment: Equipment)
    
    suspend fun deleteEquipment(equipment: Equipment)
    
    suspend fun deleteEquipmentById(id: String)
    
    suspend fun deleteAllEquipment()
    
    suspend fun getEquipmentCount(): Int
    
    suspend fun getEquipmentCountByType(equipmentType: EquipmentType): Int
} 