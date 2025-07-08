package com.abdullah303.logbook.data.repository

import com.abdullah303.logbook.data.local.dao.EquipmentDao
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.EquipmentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipmentRepositoryImpl @Inject constructor(
    private val equipmentDao: EquipmentDao
) : EquipmentRepository {
    
    override suspend fun insertEquipment(equipment: Equipment) {
        equipmentDao.insert(equipment)
    }
    
    override suspend fun insertAllEquipment(equipment: List<Equipment>) {
        equipmentDao.insertAll(equipment)
    }
    
    override suspend fun getEquipmentById(id: String): Equipment? {
        return equipmentDao.getById(id)
    }
    
    override suspend fun getAllEquipment(): List<Equipment> {
        return equipmentDao.getAll().first()
    }
    
    override fun getAllEquipmentFlow(): Flow<List<Equipment>> {
        return equipmentDao.getAll()
    }
    
    override fun getEquipmentByType(equipmentType: EquipmentType): Flow<List<Equipment>> {
        return equipmentDao.getByType(equipmentType)
    }
    
    override fun searchEquipmentByName(searchQuery: String): Flow<List<Equipment>> {
        return equipmentDao.searchByName(searchQuery)
    }
    
    override suspend fun updateEquipment(equipment: Equipment) {
        equipmentDao.update(equipment)
    }
    
    override suspend fun deleteEquipment(equipment: Equipment) {
        equipmentDao.delete(equipment)
    }
    
    override suspend fun deleteEquipmentById(id: String) {
        equipmentDao.deleteById(id)
    }
    
    override suspend fun deleteAllEquipment() {
        equipmentDao.deleteAll()
    }
    
    override suspend fun getEquipmentCount(): Int {
        return equipmentDao.getCount()
    }
    
    override suspend fun getEquipmentCountByType(equipmentType: EquipmentType): Int {
        return equipmentDao.getCountByType(equipmentType)
    }
} 