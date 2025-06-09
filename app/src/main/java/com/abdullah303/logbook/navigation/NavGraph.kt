package com.abdullah303.logbook.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullah303.logbook.features.create_exercise.ui.CreateExerciseScreen
import com.abdullah303.logbook.features.create_split.ui.CreateSplitScreen
import com.abdullah303.logbook.features.exercise_list.ui.ExerciseListScreen
import com.abdullah303.logbook.features.create_exercise.ui.muscle.MuscleSelectionScreen
import com.abdullah303.logbook.features.create_exercise.ui.bodyweight.BodyweightSelectionScreen
import com.abdullah303.logbook.features.splits.ui.SplitsScreen
import com.abdullah303.logbook.features.create_exercise.ui.equipment.EquipmentSelectionScreen
import com.abdullah303.logbook.features.create_equipment.ui.CreateEquipmentScreen
import com.abdullah303.logbook.features.create_equipment.ui.EquipmentListScreen
import com.abdullah303.logbook.core.ui.components.WeightSelectionScreen
import com.abdullah303.logbook.core.ui.components.MinWeightSelectionScreen
import com.abdullah303.logbook.core.ui.components.MaxWeightSelectionScreen
import com.abdullah303.logbook.core.ui.components.StepWeightSelectionScreen

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
        
        composable(Screen.EquipmentSelection.route) {
            EquipmentSelectionScreen(
                navController = navController
            )
        }
        
        composable(Screen.PrimaryMuscleSelection.route) {
            MuscleSelectionScreen(
                navController = navController,
                isMultiSelect = false,
                onMuscleSelected = { selectedMuscles ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedPrimaryMuscle", selectedMuscles.firstOrNull() ?: "")
                }
            )
        }
        
        composable(Screen.AuxillaryMuscleSelection.route) {
            MuscleSelectionScreen(
                navController = navController,
                isMultiSelect = true,
                onMuscleSelected = { selectedMuscles ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedAuxillaryMuscles", selectedMuscles.joinToString(", "))
                }
            )
        }
        
        composable(Screen.BodyweightSelection.route) {
            BodyweightSelectionScreen(
                navController = navController,
                onPercentageSelected = { selectedPercentage ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedBodyweightPercentage", selectedPercentage)
                }
            )
        }
        
        composable(Screen.Graphs.route) {
            // TODO: Add Graphs screen
        }
        
        composable(Screen.Settings.route) {
            // TODO: Add Settings screen
        }

        composable(
            route = Screen.EquipmentList.route,
            arguments = listOf(
                androidx.navigation.navArgument("equipmentType") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val equipmentType = backStackEntry.arguments?.getString("equipmentType") ?: ""
            EquipmentListScreen(
                navController = navController,
                equipmentType = equipmentType
            )
        }

        composable(
            route = Screen.CreateEquipment.route,
            arguments = listOf(
                androidx.navigation.navArgument("equipmentType") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val equipmentType = backStackEntry.arguments?.getString("equipmentType") ?: ""
            CreateEquipmentScreen(
                navController = navController,
                equipmentType = equipmentType
            )
        }

        composable(
            route = Screen.WeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("min") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("max") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("interval") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("unit") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val min = backStackEntry.arguments?.getString("min") ?: "0"
            val max = backStackEntry.arguments?.getString("max") ?: "100"
            val interval = backStackEntry.arguments?.getString("interval") ?: "5"
            val unit = backStackEntry.arguments?.getString("unit") ?: "kg"
            
            WeightSelectionScreen(
                navController = navController,
                min = min,
                max = max,
                interval = interval,
                unit = unit,
                onWeightSelected = { selectedWeight ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", selectedWeight)
                }
            )
        }

        composable(
            route = Screen.MinWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("max") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("step") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("unit") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("currentValue") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val max = backStackEntry.arguments?.getString("max") ?: "100"
            val step = backStackEntry.arguments?.getString("step") ?: "5"
            val unit = backStackEntry.arguments?.getString("unit") ?: "kg"
            val currentValue = backStackEntry.arguments?.getString("currentValue") ?: "0"
            
            MinWeightSelectionScreen(
                navController = navController,
                max = max,
                step = step,
                unit = unit,
                currentValue = currentValue,
                onWeightSelected = { selectedWeight ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", selectedWeight)
                }
            )
        }

        composable(
            route = Screen.MaxWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("min") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("step") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("unit") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("currentValue") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val min = backStackEntry.arguments?.getString("min") ?: "0"
            val step = backStackEntry.arguments?.getString("step") ?: "5"
            val unit = backStackEntry.arguments?.getString("unit") ?: "kg"
            val currentValue = backStackEntry.arguments?.getString("currentValue") ?: "0"
            
            MaxWeightSelectionScreen(
                navController = navController,
                min = min,
                step = step,
                unit = unit,
                currentValue = currentValue,
                onWeightSelected = { selectedWeight ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", selectedWeight)
                }
            )
        }

        composable(
            route = Screen.StepWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("unit") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("currentValue") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val unit = backStackEntry.arguments?.getString("unit") ?: "kg"
            val currentValue = backStackEntry.arguments?.getString("currentValue") ?: "0"
            
            StepWeightSelectionScreen(
                navController = navController,
                unit = unit,
                currentValue = currentValue,
                onWeightSelected = { selectedWeight ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", selectedWeight)
                }
            )
        }
    }
} 