package com.abdullah303.logbook.core.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Embedded
import com.abdullah303.logbook.core.domain.model.Exercise

data class ExerciseWithEquipment(
    @Embedded val exercise: ExerciseEntity,
    val equipmentName: String
)

@Entity(
    tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = EquipmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["equipmentId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["equipmentId"])]
)
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val equipmentId: String,
    val primaryMuscle: String,
    val auxiliaryMuscles: String,
    val bodyweightContribution: String
)

// Extension functions to convert between Entity and Domain model
fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        name = name,
        equipment = equipmentId,
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution
    )
}

fun ExerciseWithEquipment.toDomainModel(): Exercise {
    return exercise.toDomainModel().copy(equipment = this.equipmentName)
}

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
        name = name,
        equipmentId = equipment,
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution
    )
} 