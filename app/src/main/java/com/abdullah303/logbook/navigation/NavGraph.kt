package com.abdullah303.logbook.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullah303.logbook.features.create_exercise.ui.CreateExerciseScreen
import com.abdullah303.logbook.features.create_split.ui.CreateSplitScreen
import com.abdullah303.logbook.features.exercise_list.ui.ExerciseListScreen
import com.abdullah303.logbook.features.splits.ui.SplitsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splits.route
    ) {
        composable(Screen.Splits.route) {
            SplitsScreen(navController = navController)
        }
        
        composable(Screen.CreateSplit.route) {
            CreateSplitScreen(navController = navController)
        }
        
        composable(Screen.ExerciseList.route) {
            ExerciseListScreen(navController = navController)
        }
        
        composable(Screen.CreateExercise.route) {
            CreateExerciseScreen(navController = navController)
        }
        
        composable(Screen.Graphs.route) {
            // TODO: Add Graphs screen
        }
        
        composable(Screen.Settings.route) {
            // TODO: Add Settings screen
        }
    }
} 