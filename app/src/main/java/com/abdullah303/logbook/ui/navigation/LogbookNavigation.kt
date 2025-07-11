package com.abdullah303.logbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullah303.logbook.ui.create_exercise.CreateExerciseScreen
import com.abdullah303.logbook.ui.create_split.CreateSplitScreen
import com.abdullah303.logbook.ui.home.HomeScreen


@Composable
fun LogbookNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier
    ) {
        // home screen
        composable(Routes.Home.route) {
            HomeScreen(
                onNavigateToCreateSplit = {
                    navController.navigate(Routes.CreateSplit.route)
                },
                onNavigateToStandaloneWorkout = {
                    navController.navigate(Routes.StandaloneWorkout.route)
                }
            )
        }
        
        // create split screen
        composable(Routes.CreateSplit.route) {
            CreateSplitScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCreateExercise = {
                    navController.navigate(Routes.CreateExercise.route)
                },
                onSaveComplete = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) {
                            inclusive = false
                        }
                    }
                },
                navController = navController
            )
        }
        
        // create exercise screen
        composable(Routes.CreateExercise.route) {
            CreateExerciseScreen(
                onNavigateBack = { exerciseId ->
                    // navigate back with the exercise ID
                    navController.previousBackStackEntry?.savedStateHandle?.set("newlyCreatedExerciseId", exerciseId)
                    navController.popBackStack()
                }
            )
        }
        
        // standalone workout screen (placeholder for now)
        composable(Routes.StandaloneWorkout.route) {
            // todo: create standalone workout screen
            CreateSplitScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 