package com.abdullah303.logbook.navigation

sealed class Screen(val route: String) {
    object Splits : Screen(NavRoutes.SPLITS)
    object CreateSplit : Screen(NavRoutes.CREATE_SPLIT)
    object Graphs : Screen("graphs")
    object Settings : Screen("settings")
    object ExerciseList : Screen(NavRoutes.EXERCISE_LIST)
    object CreateExercise : Screen(NavRoutes.CREATE_EXERCISE)
    object PrimaryMuscleSelection : Screen(NavRoutes.PRIMARY_MUSCLE_SELECTION)
    object AuxillaryMuscleSelection : Screen(NavRoutes.AUXILLARY_MUSCLE_SELECTION)
    object BodyweightSelection : Screen(NavRoutes.BODYWEIGHT_SELECTION)
    object EquipmentSelection : Screen("equipment_selection")
    object EquipmentList : Screen("equipment_list/{equipmentType}") {
        fun createRoute(equipmentType: String) = "equipment_list/$equipmentType"
    }
    object CreateEquipment : Screen("create_equipment/{equipmentType}") {
        fun createRoute(equipmentType: String) = "create_equipment/$equipmentType"
    }
    object WeightSelection : Screen("weight_selection/{min}/{max}/{interval}/{unit}") {
        fun createRoute(min: String, max: String, interval: String, unit: String) = 
            "weight_selection/$min/$max/$interval/$unit"
    }
    object MinWeightSelection : Screen("min_weight_selection/{max}/{step}/{unit}/{currentValue}") {
        fun createRoute(max: String, step: String, unit: String, currentValue: String) = 
            "min_weight_selection/$max/$step/$unit/$currentValue"
    }
    object MaxWeightSelection : Screen("max_weight_selection/{min}/{step}/{unit}/{currentValue}") {
        fun createRoute(min: String, step: String, unit: String, currentValue: String) = 
            "max_weight_selection/$min/$step/$unit/$currentValue"
    }
    object StepWeightSelection : Screen("step_weight_selection/{unit}/{currentValue}") {
        fun createRoute(unit: String, currentValue: String) = 
            "step_weight_selection/$unit/$currentValue"
    }
} 