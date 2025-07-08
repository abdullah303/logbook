package com.abdullah303.logbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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