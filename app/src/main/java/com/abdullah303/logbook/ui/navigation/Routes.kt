package com.abdullah303.logbook.ui.navigation

// main navigation destinations
sealed class Routes(val route: String) {
    object Home : Routes("home")
    object CreateSplit : Routes("create_split")
    object CreateExercise : Routes("create_exercise")
    object StandaloneWorkout : Routes("standalone_workout")
} 