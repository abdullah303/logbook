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
} 