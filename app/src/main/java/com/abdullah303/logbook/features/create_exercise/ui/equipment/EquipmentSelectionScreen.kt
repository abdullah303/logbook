package com.abdullah303.logbook.features.create_exercise.ui.equipment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.navigation.Screen
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val equipmentOptions = listOf(
        "Cable Stack",
        "Dumbbells",
        "Resistance Machine",
        "Smith Machine",
        "Barbell",
        "Bodyweight",
        "Resistance Band",
        "Plate"
    )

    val selectedEquipmentFromList by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("selectedEquipment", "")
        ?.collectAsStateWithLifecycle() ?: mutableStateOf("")

    LaunchedEffect(selectedEquipmentFromList) {
        if (selectedEquipmentFromList.isNotEmpty()) {
            // Pass the result to the previous screen (CreateExerciseScreen)
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedEquipment", selectedEquipmentFromList)
            // Reset the value in the current screen's SavedStateHandle to avoid re-triggering
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("selectedEquipment", "")
            // Navigate back to CreateExerciseScreen
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Equipment") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(equipmentOptions) { equipment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                    ),
                    onClick = {
                        if (equipment == "Cable Stack" || equipment == "Resistance Machine" || equipment == "Smith Machine") {
                            navController.navigate(
                                Screen.EquipmentList.createRoute(equipment)
                            )
                        } else {
                            navController.previousBackStackEntry?.savedStateHandle?.set("selectedEquipment", equipment)
                            navController.navigateUp()
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = equipment,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
} 