package com.abdullah303.logbook.navigation

sealed class Screen(val route: String) {
    object Splits : Screen(NavRoutes.SPLITS)
    object CreateSplit : Screen(NavRoutes.CREATE_SPLIT)

    object Graphs : Screen(NavRoutes.GRAPHS)
    object Settings : Screen(NavRoutes.SETTINGS)

    object ExerciseList : Screen(NavRoutes.EXERCISE_LIST)
    object CreateExercise : Screen(NavRoutes.CREATE_EXERCISE)

    object PrimaryMuscleSelection : Screen(NavRoutes.PRIMARY_MUSCLE_SELECTION)
    object AuxiliaryMuscleSelection : Screen(NavRoutes.AUXILIARY_MUSCLE_SELECTION)

    object BodyweightSelection : Screen(NavRoutes.BODYWEIGHT_SELECTION)
    object EquipmentSelection : Screen(NavRoutes.EQUIPMENT_SELECTION)

    object EquipmentList : Screen("${NavRoutes.EQUIPMENT_LIST}/{equipmentType}") {
        fun createRoute(equipmentType: String) =
            "${NavRoutes.EQUIPMENT_LIST}/$equipmentType"
    }

    object CreateEquipment : Screen("${NavRoutes.CREATE_EQUIPMENT}/{equipmentType}") {
        fun createRoute(equipmentType: String) =
            "${NavRoutes.CREATE_EQUIPMENT}/$equipmentType"
    }

    object WeightSelection :
        Screen("${NavRoutes.WEIGHT_SELECTION}/{min}/{max}/{interval}/{unit}") {
        fun createRoute(
            min: String,
            max: String,
            interval: String,
            unit: String
        ) = "${NavRoutes.WEIGHT_SELECTION}/$min/$max/$interval/$unit"
    }

    object MinWeightSelection :
        Screen("${NavRoutes.MIN_WEIGHT_SELECTION}/{max}/{step}/{unit}/{currentValue}") {
        fun createRoute(
            max: String,
            step: String,
            unit: String,
            currentValue: String
        ) = "${NavRoutes.MIN_WEIGHT_SELECTION}/$max/$step/$unit/$currentValue"
    }

    object MaxWeightSelection :
        Screen("${NavRoutes.MAX_WEIGHT_SELECTION}/{min}/{step}/{unit}/{currentValue}") {
        fun createRoute(
            min: String,
            step: String,
            unit: String,
            currentValue: String
        ) = "${NavRoutes.MAX_WEIGHT_SELECTION}/$min/$step/$unit/$currentValue"
    }

    object StepWeightSelection :
        Screen("${NavRoutes.STEP_WEIGHT_SELECTION}/{unit}/{currentValue}") {
        fun createRoute(unit: String, currentValue: String) =
            "${NavRoutes.STEP_WEIGHT_SELECTION}/$unit/$currentValue"
    }
}
