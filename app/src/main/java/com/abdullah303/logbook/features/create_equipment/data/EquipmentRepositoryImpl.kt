package com.abdullah303.logbook.features.create_equipment.data

import com.abdullah303.logbook.core.domain.model.Equipment
import com.abdullah303.logbook.core.domain.model.EquipmentType
import com.abdullah303.logbook.core.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipmentRepositoryImpl @Inject constructor() : EquipmentRepository {
    private val _currentEquipment = MutableStateFlow(Equipment(type = EquipmentType.BARBELL))
    private val _equipmentList = MutableStateFlow<List<Equipment>>(emptyList())

    override fun getCurrentEquipment(): Flow<Equipment> = _currentEquipment

    override fun getAllEquipment(): Flow<List<Equipment>> = _equipmentList

    override suspend fun getEquipmentById(id: String): Equipment? {
        return _equipmentList.value.find { it.id == id }
    }

    override suspend fun updateEquipment(equipment: Equipment) {
        _currentEquipment.value = equipment
    }

    override suspend fun clearEquipment() {
        _currentEquipment.value = Equipment(type = EquipmentType.BARBELL)
    }

    override suspend fun saveEquipment(equipment: Equipment) {
        val updatedList = _equipmentList.value.toMutableList()
        val existingIndex = updatedList.indexOfFirst { it.id == equipment.id }
        
        if (existingIndex != -1) {
            updatedList[existingIndex] = equipment
        } else {
            updatedList.add(equipment)
        }
        
        _equipmentList.value = updatedList
        clearEquipment()
    }

    override suspend fun deleteEquipment(equipment: Equipment) {
        _equipmentList.value = _equipmentList.value.filter { it.id != equipment.id }
    }

    override suspend fun deleteEquipmentById(id: String) {
        _equipmentList.value = _equipmentList.value.filter { it.id != id }
    }

    override fun searchEquipment(searchQuery: String): Flow<List<Equipment>> {
        return _equipmentList.map { equipmentList ->
            equipmentList.filter { equipment ->
                equipment.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    override fun getEquipmentByType(type: String): Flow<List<Equipment>> {
        return _equipmentList.map { equipmentList ->
            equipmentList.filter { equipment ->
                equipment.type.name == type
            }
        }
    }
} 