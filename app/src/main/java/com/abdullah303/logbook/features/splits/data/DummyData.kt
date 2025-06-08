package com.abdullah303.logbook.features.splits.data

import com.abdullah303.logbook.core.domain.model.EquipmentType
import java.time.LocalDateTime
import java.util.UUID

data class Split(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isActive: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val currentIteration: Int = 1,
    val workouts: List<Workout> = emptyList()
)

data class Workout(
    val id: String = UUID.randomUUID().toString(),
    val splitId: String,
    val name: String,
    val order: Int,
    val iteration: Int = 1,
    val isCompleted: Boolean = false,
    val exercises: List<ExerciseInstance> = emptyList()
)

data class ExerciseTemplate(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val primaryMuscle: String,
    val secondaryMuscles: List<String> = emptyList(),
    val equipmentType: EquipmentType,
    val isUnilateral: Boolean = false,
    val defaultBodyWeightPct: Float? = null,
    val mediaUri: String? = null,
    val setupInstructions: String? = null
)

data class ExerciseInstance(
    val id: String = UUID.randomUUID().toString(),
    val workoutId: String,
    val exerciseTemplateId: String,
    val targetRepRange: IntRange,
    val targetRIR: Int,
    val sets: Int,
    val order: Int
)

object DummyData {
    val exerciseTemplates = listOf(
        // Push Exercises
        ExerciseTemplate(
            name = "Bench Press",
            primaryMuscle = "Chest",
            secondaryMuscles = listOf("Triceps", "Front Deltoids"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Incline Bench Press",
            primaryMuscle = "Upper Chest",
            secondaryMuscles = listOf("Triceps", "Front Deltoids"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Overhead Press",
            primaryMuscle = "Shoulders",
            secondaryMuscles = listOf("Triceps", "Upper Chest"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Lateral Raises",
            primaryMuscle = "Lateral Deltoids",
            secondaryMuscles = listOf("Front Deltoids", "Traps"),
            equipmentType = EquipmentType.DUMBBELL
        ),
        ExerciseTemplate(
            name = "Tricep Pushdown",
            primaryMuscle = "Triceps",
            secondaryMuscles = listOf("Chest", "Shoulders"),
            equipmentType = EquipmentType.CABLE_STACK
        ),
        
        // Pull Exercises
        ExerciseTemplate(
            name = "Pull Up",
            primaryMuscle = "Back",
            secondaryMuscles = listOf("Biceps", "Rear Deltoids"),
            equipmentType = EquipmentType.BODYWEIGHT
        ),
        ExerciseTemplate(
            name = "Barbell Row",
            primaryMuscle = "Back",
            secondaryMuscles = listOf("Biceps", "Rear Deltoids"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Face Pull",
            primaryMuscle = "Rear Deltoids",
            secondaryMuscles = listOf("Traps", "Rhomboids"),
            equipmentType = EquipmentType.CABLE_STACK
        ),
        ExerciseTemplate(
            name = "Bicep Curl",
            primaryMuscle = "Biceps",
            secondaryMuscles = listOf("Forearms"),
            equipmentType = EquipmentType.DUMBBELL
        ),
        
        // Legs Exercises
        ExerciseTemplate(
            name = "Squat",
            primaryMuscle = "Quadriceps",
            secondaryMuscles = listOf("Glutes", "Hamstrings"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Romanian Deadlift",
            primaryMuscle = "Hamstrings",
            secondaryMuscles = listOf("Glutes", "Lower Back"),
            equipmentType = EquipmentType.BARBELL
        ),
        ExerciseTemplate(
            name = "Leg Press",
            primaryMuscle = "Quadriceps",
            secondaryMuscles = listOf("Glutes", "Hamstrings"),
            equipmentType = EquipmentType.RESISTANCE_MACHINE
        ),
        ExerciseTemplate(
            name = "Calf Raises",
            primaryMuscle = "Calves",
            secondaryMuscles = emptyList(),
            equipmentType = EquipmentType.RESISTANCE_MACHINE
        ),
        ExerciseTemplate(
            name = "Bulgarian Split Squat",
            primaryMuscle = "Quadriceps",
            secondaryMuscles = listOf("Glutes", "Hamstrings"),
            equipmentType = EquipmentType.DUMBBELL,
            isUnilateral = true
        )
    )

    val activeSplit = Split(
        name = "Full Body",
        isActive = true,
        workouts = listOf(
            Workout(
                splitId = "full_body",
                name = "Push",
                order = 1,
                exercises = listOf(
                    ExerciseInstance(
                        workoutId = "push_workout",
                        exerciseTemplateId = exerciseTemplates[0].id, // Bench Press
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 1
                    ),
                    ExerciseInstance(
                        workoutId = "push_workout",
                        exerciseTemplateId = exerciseTemplates[2].id, // Overhead Press
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 2
                    ),
                    ExerciseInstance(
                        workoutId = "push_workout",
                        exerciseTemplateId = exerciseTemplates[3].id, // Lateral Raises
                        targetRepRange = 12..15,
                        targetRIR = 2,
                        sets = 3,
                        order = 3
                    ),
                    ExerciseInstance(
                        workoutId = "push_workout",
                        exerciseTemplateId = exerciseTemplates[4].id, // Tricep Pushdown
                        targetRepRange = 12..15,
                        targetRIR = 2,
                        sets = 3,
                        order = 4
                    )
                )
            ),
            Workout(
                splitId = "full_body",
                name = "Pull",
                order = 2,
                exercises = listOf(
                    ExerciseInstance(
                        workoutId = "pull_workout",
                        exerciseTemplateId = exerciseTemplates[5].id, // Pull Up
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 1
                    ),
                    ExerciseInstance(
                        workoutId = "pull_workout",
                        exerciseTemplateId = exerciseTemplates[6].id, // Barbell Row
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 2
                    ),
                    ExerciseInstance(
                        workoutId = "pull_workout",
                        exerciseTemplateId = exerciseTemplates[7].id, // Face Pull
                        targetRepRange = 12..15,
                        targetRIR = 2,
                        sets = 3,
                        order = 3
                    ),
                    ExerciseInstance(
                        workoutId = "pull_workout",
                        exerciseTemplateId = exerciseTemplates[8].id, // Bicep Curl
                        targetRepRange = 12..15,
                        targetRIR = 2,
                        sets = 3,
                        order = 4
                    )
                )
            ),
            Workout(
                splitId = "full_body",
                name = "Legs",
                order = 3,
                exercises = listOf(
                    ExerciseInstance(
                        workoutId = "legs_workout",
                        exerciseTemplateId = exerciseTemplates[9].id, // Squat
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 1
                    ),
                    ExerciseInstance(
                        workoutId = "legs_workout",
                        exerciseTemplateId = exerciseTemplates[10].id, // Romanian Deadlift
                        targetRepRange = 8..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 2
                    ),
                    ExerciseInstance(
                        workoutId = "legs_workout",
                        exerciseTemplateId = exerciseTemplates[11].id, // Leg Press
                        targetRepRange = 10..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 3
                    ),
                    ExerciseInstance(
                        workoutId = "legs_workout",
                        exerciseTemplateId = exerciseTemplates[12].id, // Calf Raises
                        targetRepRange = 15..20,
                        targetRIR = 2,
                        sets = 3,
                        order = 4
                    ),
                    ExerciseInstance(
                        workoutId = "legs_workout",
                        exerciseTemplateId = exerciseTemplates[13].id, // Bulgarian Split Squat
                        targetRepRange = 10..12,
                        targetRIR = 2,
                        sets = 3,
                        order = 5
                    )
                )
            )
        )
    )

    val availableSplits = listOf(
        activeSplit,
        Split(
            name = "Push/Pull/Legs",
            workouts = listOf(
                Workout(
                    splitId = "ppl",
                    name = "Push",
                    order = 1,
                    exercises = listOf(
                        ExerciseInstance(
                            workoutId = "ppl_push",
                            exerciseTemplateId = exerciseTemplates[0].id, // Bench Press
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 1
                        ),
                        ExerciseInstance(
                            workoutId = "ppl_push",
                            exerciseTemplateId = exerciseTemplates[1].id, // Incline Bench
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 3,
                            order = 2
                        )
                    )
                ),
                Workout(
                    splitId = "ppl",
                    name = "Pull",
                    order = 2,
                    exercises = listOf(
                        ExerciseInstance(
                            workoutId = "ppl_pull",
                            exerciseTemplateId = exerciseTemplates[5].id, // Pull Up
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 1
                        ),
                        ExerciseInstance(
                            workoutId = "ppl_pull",
                            exerciseTemplateId = exerciseTemplates[6].id, // Barbell Row
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 3,
                            order = 2
                        )
                    )
                ),
                Workout(
                    splitId = "ppl",
                    name = "Legs",
                    order = 3,
                    exercises = listOf(
                        ExerciseInstance(
                            workoutId = "ppl_legs",
                            exerciseTemplateId = exerciseTemplates[9].id, // Squat
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 1
                        ),
                        ExerciseInstance(
                            workoutId = "ppl_legs",
                            exerciseTemplateId = exerciseTemplates[10].id, // Romanian Deadlift
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 3,
                            order = 2
                        )
                    )
                )
            )
        ),
        Split(
            name = "Upper/Lower",
            workouts = listOf(
                Workout(
                    splitId = "upper_lower",
                    name = "Upper",
                    order = 1,
                    exercises = listOf(
                        ExerciseInstance(
                            workoutId = "upper",
                            exerciseTemplateId = exerciseTemplates[0].id, // Bench Press
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 1
                        ),
                        ExerciseInstance(
                            workoutId = "upper",
                            exerciseTemplateId = exerciseTemplates[5].id, // Pull Up
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 2
                        )
                    )
                ),
                Workout(
                    splitId = "upper_lower",
                    name = "Lower",
                    order = 2,
                    exercises = listOf(
                        ExerciseInstance(
                            workoutId = "lower",
                            exerciseTemplateId = exerciseTemplates[9].id, // Squat
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 4,
                            order = 1
                        ),
                        ExerciseInstance(
                            workoutId = "lower",
                            exerciseTemplateId = exerciseTemplates[10].id, // Romanian Deadlift
                            targetRepRange = 8..12,
                            targetRIR = 2,
                            sets = 3,
                            order = 2
                        )
                    )
                )
            )
        )
    )
} 