package com.abdullah303.logbook.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullah303.logbook.core.domain.model.Exercise

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val equipment: String,
    val primaryMuscle: String,
    val auxiliaryMuscles: String,
    val bodyweightContribution: String
)

// Extension functions to convert between Entity and Domain model
fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        name = name,
        equipment = equipment,
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution
    )
}

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
        name = name,
        equipment = equipment,
        primaryMuscle = primaryMuscle,
        auxiliaryMuscles = auxiliaryMuscles,
        bodyweightContribution = bodyweightContribution
    )
} 