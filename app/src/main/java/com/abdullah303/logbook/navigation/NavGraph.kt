package com.abdullah303.logbook.navigation

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
        startDestination = Screen.Splits.route // landing page
    ) {
        // basic, no-arg routes
        composable(Screen.Splits.route) { SplitsScreen(navController) }
        composable(Screen.CreateSplit.route) { CreateSplitScreen(navController) }
        composable(Screen.ExerciseList.route) { ExerciseListScreen(navController) }
        composable(Screen.CreateExercise.route) { CreateExerciseScreen(navController) }
        composable(Screen.EquipmentSelection.route) { EquipmentSelectionScreen(navController) }

        // muscle pickers
        composable(Screen.PrimaryMuscleSelection.route) {
            MuscleSelectionScreen(
                navController = navController,
                isMultiSelect = false,
                onMuscleSelected = { selected ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedPrimaryMuscle", selected.firstOrNull() ?: "")
                }
            )
        }

        composable(Screen.AuxiliaryMuscleSelection.route) {
            MuscleSelectionScreen(
                navController = navController,
                isMultiSelect = true,
                onMuscleSelected = { selected ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedAuxiliaryMuscles", selected.joinToString(", "))
                }
            )
        }

        // percentage of bodyweight
        composable(Screen.BodyweightSelection.route) {
            BodyweightSelectionScreen(
                navController = navController,
                onPercentageSelected = { pct ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedBodyweightPercentage", pct)
                }
            )
        }

        // placeholders
        composable(Screen.Graphs.route) { /* TODO graphs */ }
        composable(Screen.Settings.route) { /* TODO settings */ }

        // equipment selection - single param
        composable(
            route = Screen.EquipmentList.route,
            arguments = listOf(androidx.navigation.navArgument("equipmentType") {
                type = androidx.navigation.NavType.StringType
            })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("equipmentType").orEmpty()
            EquipmentListScreen(navController, type)
        }

        composable(
            route = Screen.CreateEquipment.route,
            arguments = listOf(androidx.navigation.navArgument("equipmentType") {
                type = androidx.navigation.NavType.StringType
            })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("equipmentType").orEmpty()
            CreateEquipmentScreen(navController, type)
        }

        // weight pickers – multiple params, defaults as fallbacks
        composable(
            route = Screen.WeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("min") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("max") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("interval") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("unit") { type = androidx.navigation.NavType.StringType }
            )
        ) { entry ->
            WeightSelectionScreen(
                navController = navController,
                min = entry.arguments?.getString("min") ?: "0",
                max = entry.arguments?.getString("max") ?: "100",
                interval = entry.arguments?.getString("interval") ?: "5",
                unit = entry.arguments?.getString("unit") ?: "kg",
                onWeightSelected = { w ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", w)
                }
            )
        }

        composable(
            route = Screen.MinWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("max") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("step") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("unit") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("currentValue") { type = androidx.navigation.NavType.StringType }
            )
        ) { entry ->
            MinWeightSelectionScreen(
                navController = navController,
                max = entry.arguments?.getString("max") ?: "100",
                step = entry.arguments?.getString("step") ?: "5",
                unit = entry.arguments?.getString("unit") ?: "kg",
                currentValue = entry.arguments?.getString("currentValue") ?: "0",
                onWeightSelected = { w ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", w)
                }
            )
        }

        composable(
            route = Screen.MaxWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("min") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("step") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("unit") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("currentValue") { type = androidx.navigation.NavType.StringType }
            )
        ) { entry ->
            MaxWeightSelectionScreen(
                navController = navController,
                min = entry.arguments?.getString("min") ?: "0",
                step = entry.arguments?.getString("step") ?: "5",
                unit = entry.arguments?.getString("unit") ?: "kg",
                currentValue = entry.arguments?.getString("currentValue") ?: "0",
                onWeightSelected = { w ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", w)
                }
            )
        }

        composable(
            route = Screen.StepWeightSelection.route,
            arguments = listOf(
                androidx.navigation.navArgument("unit") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("currentValue") { type = androidx.navigation.NavType.StringType }
            )
        ) { entry ->
            StepWeightSelectionScreen(
                navController = navController,
                unit = entry.arguments?.getString("unit") ?: "kg",
                currentValue = entry.arguments?.getString("currentValue") ?: "0",
                onWeightSelected = { w ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedWeight", w)
                }
            )
        }
    }
}
