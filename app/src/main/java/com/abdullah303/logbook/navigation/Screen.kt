package com.abdullah303.logbook.navigation

sealed class Screen(val route: String) {
    object Splits : Screen(NavRoutes.SPLITS)
    object CreateSplit : Screen(NavRoutes.CREATE_SPLIT)
    object Graphs : Screen("graphs")
    object Settings : Screen("settings")
    object ExerciseList : Screen(NavRoutes.EXERCISE_LIST)
    object CreateExercise : Screen(NavRoutes.CREATE_EXERCISE)
} 