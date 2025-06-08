package com.abdullah303.logbook.core.data.repository

import com.abdullah303.logbook.core.data.local.EquipmentDao
import com.abdullah303.logbook.core.data.local.toDomainModel
import com.abdullah303.logbook.core.data.local.toEntity
import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipmentRepositoryImpl @Inject constructor(
    private val equipmentDao: EquipmentDao
) : EquipmentRepository {
    
    // Current equipment for create/edit flow (in-memory state)
    private val _currentEquipment = MutableStateFlow(Equipment(type = EquipmentType.BARBELL))
    
    override fun getCurrentEquipment(): Flow<Equipment> = _currentEquipment.asStateFlow()
    
    override suspend fun updateEquipment(equipment: Equipment) {
        _currentEquipment.value = equipment
    }
    
    override suspend fun clearEquipment() {
        _currentEquipment.value = Equipment(type = EquipmentType.BARBELL)
    }
    
    // Database operations for permanent storage
    override fun getAllEquipment(): Flow<List<Equipment>> {
        return equipmentDao.getAllEquipment().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getEquipmentById(id: String): Equipment? {
        return equipmentDao.getEquipmentById(id)?.toDomainModel()
    }
    
    override suspend fun saveEquipment(equipment: Equipment) {
        equipmentDao.insertEquipment(equipment.toEntity())
    }
    
    override suspend fun deleteEquipment(equipment: Equipment) {
        equipmentDao.deleteEquipment(equipment.toEntity())
    }
    
    override suspend fun deleteEquipmentById(id: String) {
        equipmentDao.deleteEquipmentById(id)
    }
    
    override fun searchEquipment(searchQuery: String): Flow<List<Equipment>> {
        return equipmentDao.searchEquipment(searchQuery).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override fun getEquipmentByType(type: String): Flow<List<Equipment>> {
        return equipmentDao.getEquipmentByType(type).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
} 