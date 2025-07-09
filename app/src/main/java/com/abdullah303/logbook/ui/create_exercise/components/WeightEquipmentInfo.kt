package com.abdullah303.logbook.ui.create_exercise.components

import com.abdullah303.logbook.data.local.entity.BarbellInfo
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import com.abdullah303.logbook.data.model.ResistanceMachineType
import java.math.BigDecimal

// generic interface for equipment with weight information
interface WeightEquipmentInfo {
    val equipment_id: String
    val bar_weight: BigDecimal
}

// interface for cable stack equipment with weight range information
interface CableStackEquipmentInfo {
    val equipment_id: String
    val min_weight: BigDecimal
    val max_weight: BigDecimal
    val increment: BigDecimal
}

// base interface for resistance machine equipment
interface ResistanceMachineEquipmentInfo {
    val equipment_id: String
    val type: ResistanceMachineType
}

// interface for pin loaded resistance machine equipment
interface PinLoadedEquipmentInfo : ResistanceMachineEquipmentInfo {
    val min_weight: BigDecimal
    val max_weight: BigDecimal
    val increment: BigDecimal
}

// interface for plate loaded resistance machine equipment
interface PlateLoadedEquipmentInfo : ResistanceMachineEquipmentInfo {
    val num_pegs: Int
    val base_machine_weight: BigDecimal
}

// extension functions to make existing entities compatible with the generic interface
fun BarbellInfo.toWeightEquipmentInfo(): WeightEquipmentInfo = object : WeightEquipmentInfo {
    override val equipment_id: String = this@toWeightEquipmentInfo.equipment_id
    override val bar_weight: BigDecimal = this@toWeightEquipmentInfo.bar_weight
}

fun SmithMachineInfo.toWeightEquipmentInfo(): WeightEquipmentInfo = object : WeightEquipmentInfo {
    override val equipment_id: String = this@toWeightEquipmentInfo.equipment_id
    override val bar_weight: BigDecimal = this@toWeightEquipmentInfo.bar_weight
}

fun CableStackInfo.toCableStackEquipmentInfo(): CableStackEquipmentInfo = object : CableStackEquipmentInfo {
    override val equipment_id: String = this@toCableStackEquipmentInfo.equipment_id
    override val min_weight: BigDecimal = this@toCableStackEquipmentInfo.min_weight
    override val max_weight: BigDecimal = this@toCableStackEquipmentInfo.max_weight
    override val increment: BigDecimal = this@toCableStackEquipmentInfo.increment
}

fun ResistanceMachineInfo.toResistanceMachineEquipmentInfo(): ResistanceMachineEquipmentInfo = object : ResistanceMachineEquipmentInfo {
    override val equipment_id: String = this@toResistanceMachineEquipmentInfo.equipment_id
    override val type: ResistanceMachineType = this@toResistanceMachineEquipmentInfo.type
}

fun PinLoadedInfo.toPinLoadedEquipmentInfo(resistanceMachineInfo: ResistanceMachineInfo): PinLoadedEquipmentInfo = object : PinLoadedEquipmentInfo {
    override val equipment_id: String = resistanceMachineInfo.equipment_id
    override val type: ResistanceMachineType = resistanceMachineInfo.type
    override val min_weight: BigDecimal = this@toPinLoadedEquipmentInfo.min_weight
    override val max_weight: BigDecimal = this@toPinLoadedEquipmentInfo.max_weight
    override val increment: BigDecimal = this@toPinLoadedEquipmentInfo.increment
}

fun PlateLoadedInfo.toPlateLoadedEquipmentInfo(resistanceMachineInfo: ResistanceMachineInfo): PlateLoadedEquipmentInfo = object : PlateLoadedEquipmentInfo {
    override val equipment_id: String = resistanceMachineInfo.equipment_id
    override val type: ResistanceMachineType = resistanceMachineInfo.type
    override val num_pegs: Int = this@toPlateLoadedEquipmentInfo.num_pegs
    override val base_machine_weight: BigDecimal = this@toPlateLoadedEquipmentInfo.base_machine_weight
}

// generic data class to combine equipment and weight info
data class WeightEquipmentConfiguration(
    val equipment: Equipment,
    val weightInfo: WeightEquipmentInfo
)

// data class to combine equipment and cable stack info
data class CableStackEquipmentConfiguration(
    val equipment: Equipment,
    val cableStackInfo: CableStackEquipmentInfo
)

// data class to combine equipment and resistance machine info
data class ResistanceMachineEquipmentConfiguration(
    val equipment: Equipment,
    val resistanceMachineInfo: ResistanceMachineEquipmentInfo
)

// sealed interface for all equipment configurations
sealed interface EquipmentConfiguration {
    val equipment: Equipment
    
    data class WeightEquipment(
        override val equipment: Equipment,
        val weightInfo: WeightEquipmentInfo
    ) : EquipmentConfiguration
    
    data class CableStackEquipment(
        override val equipment: Equipment,
        val cableStackInfo: CableStackEquipmentInfo
    ) : EquipmentConfiguration
    
    data class ResistanceMachineEquipment(
        override val equipment: Equipment,
        val resistanceMachineInfo: ResistanceMachineEquipmentInfo
    ) : EquipmentConfiguration
    
    data class SimpleEquipment(
        override val equipment: Equipment
    ) : EquipmentConfiguration
}

// helper functions to get display information for each equipment type
fun EquipmentConfiguration.getDisplayInfo(): List<String> {
    return when (this) {
        is EquipmentConfiguration.WeightEquipment -> {
            val weightUnit = equipment.weight_unit.name.lowercase()
            listOf("Bar Weight: ${weightInfo.bar_weight} $weightUnit")
        }
        is EquipmentConfiguration.CableStackEquipment -> {
            val weightUnit = equipment.weight_unit.name.lowercase()
            listOf(
                "Weight Range: ${cableStackInfo.min_weight} - ${cableStackInfo.max_weight} $weightUnit",
                "Increment: ${cableStackInfo.increment} $weightUnit"
            )
        }
        is EquipmentConfiguration.ResistanceMachineEquipment -> {
            val typeInfo = "Type: ${resistanceMachineInfo.type.name.lowercase().replace('_', ' ')}"
            val additionalInfo = when (resistanceMachineInfo) {
                is PinLoadedEquipmentInfo -> {
                    val weightUnit = equipment.weight_unit.name.lowercase()
                    listOf(
                        "Weight Range: ${resistanceMachineInfo.min_weight} - ${resistanceMachineInfo.max_weight} $weightUnit",
                        "Increment: ${resistanceMachineInfo.increment} $weightUnit"
                    )
                }
                is PlateLoadedEquipmentInfo -> {
                    val weightUnit = equipment.weight_unit.name.lowercase()
                    listOf(
                        "Base Weight: ${resistanceMachineInfo.base_machine_weight} $weightUnit",
                        "Number of Pegs: ${resistanceMachineInfo.num_pegs}"
                    )
                }
                else -> emptyList()
            }
            listOf(typeInfo) + additionalInfo
        }
        is EquipmentConfiguration.SimpleEquipment -> {
            emptyList()
        }
    }
}

// convenience functions to create unified configurations
fun Equipment.withBarbellInfo(barbellInfo: BarbellInfo): EquipmentConfiguration.WeightEquipment {
    return EquipmentConfiguration.WeightEquipment(
        equipment = this,
        weightInfo = barbellInfo.toWeightEquipmentInfo()
    )
}

fun Equipment.withSmithMachineInfo(smithMachineInfo: SmithMachineInfo): EquipmentConfiguration.WeightEquipment {
    return EquipmentConfiguration.WeightEquipment(
        equipment = this,
        weightInfo = smithMachineInfo.toWeightEquipmentInfo()
    )
}

fun Equipment.withCableStackInfo(cableStackInfo: CableStackInfo): EquipmentConfiguration.CableStackEquipment {
    return EquipmentConfiguration.CableStackEquipment(
        equipment = this,
        cableStackInfo = cableStackInfo.toCableStackEquipmentInfo()
    )
}

fun Equipment.withPinLoadedInfo(pinLoadedInfo: PinLoadedInfo, resistanceMachineInfo: ResistanceMachineInfo): EquipmentConfiguration.ResistanceMachineEquipment {
    return EquipmentConfiguration.ResistanceMachineEquipment(
        equipment = this,
        resistanceMachineInfo = pinLoadedInfo.toPinLoadedEquipmentInfo(resistanceMachineInfo)
    )
}

fun Equipment.withPlateLoadedInfo(plateLoadedInfo: PlateLoadedInfo, resistanceMachineInfo: ResistanceMachineInfo): EquipmentConfiguration.ResistanceMachineEquipment {
    return EquipmentConfiguration.ResistanceMachineEquipment(
        equipment = this,
        resistanceMachineInfo = plateLoadedInfo.toPlateLoadedEquipmentInfo(resistanceMachineInfo)
    )
}

fun Equipment.asSimpleEquipment(): EquipmentConfiguration.SimpleEquipment {
    return EquipmentConfiguration.SimpleEquipment(equipment = this)
}



 