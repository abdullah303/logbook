package com.abdullah303.logbook.core.domain.repository

import com.abdullah303.logbook.core.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository {
    // Current equipment for create/edit flow
    fun getCurrentEquipment(): Flow<Equipment>
    suspend fun updateEquipment(equipment: Equipment)
    suspend fun clearEquipment()
    
    // Database operations for permanent storage
    fun getAllEquipment(): Flow<List<Equipment>>
    suspend fun getEquipmentById(id: String): Equipment?
    suspend fun saveEquipment(equipment: Equipment)
    suspend fun deleteEquipment(equipment: Equipment)
    suspend fun deleteEquipmentById(id: String)
    fun searchEquipment(searchQuery: String): Flow<List<Equipment>>
    fun getEquipmentByType(type: String): Flow<List<Equipment>>
} 