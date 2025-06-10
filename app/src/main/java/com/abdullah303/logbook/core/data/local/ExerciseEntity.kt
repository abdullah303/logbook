package com.abdullah303.logbook.core.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Embedded
import com.abdullah303.logbook.core.domain.model.Exercise

data class ExerciseWithEquipment(
    @Embedded val exercise: ExerciseEntity,
    val customEquipmentName: String?
)

@Entity(
    tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = EquipmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["equipmentId"],
        onDelete = ForeignKey.SET_NULL
    )],
    indices = [Index(value = ["equipmentId"])]
)
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val equipmentId: String?,
    val primaryMuscle: String,
    val auxiliaryMuscles: String,
    val bodyweightContribution: String,
    val equipmentName: String?
)

// Extension functions to convert between Entity and Domain model
fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        name = name,
        equipment = equipmentName ?: "",
        equipmentId = equipmentId ?: "",
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution
    )
}

fun ExerciseWithEquipment.toDomainModel(): Exercise {
    return Exercise(
        id = exercise.id,
        name = exercise.name,
        equipment = customEquipmentName ?: exercise.equipmentName ?: "",
        equipmentId = exercise.equipmentId ?: "",
        primaryMuscle = exercise.primaryMuscle,
        auxiliaryMuscles = exercise.auxiliaryMuscles,
        bodyweightContribution = exercise.bodyweightContribution
    )
}

fun Exercise.toEntity(): ExerciseEntity {
    val isCustom = equipmentId.isNotBlank()
    return ExerciseEntity(
        id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
        name = name,
        equipmentId = if (isCustom) equipmentId else null,
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution,
        equipmentName = equipment
    )
} 