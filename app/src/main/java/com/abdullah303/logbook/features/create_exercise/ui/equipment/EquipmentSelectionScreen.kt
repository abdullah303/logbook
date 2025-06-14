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
import com.abdullah303.logbook.features.create_exercise.presentation.CreateExerciseViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateExerciseViewModel = hiltViewModel()
) {
    val equipmentOptions = remember {
        listOf(
            "Cable Stack",
            "Dumbbells",
            "Resistance Machine",
            "Smith Machine",
            "Barbell",
            "Bodyweight",
            "Resistance Band",
            "Plate"
        )
    }
    val scope = rememberCoroutineScope()

    val selectedEquipmentId by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("selectedEquipmentId", "")
        ?.collectAsStateWithLifecycle() ?: mutableStateOf("")

    val selectedEquipmentName by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("selectedEquipmentName", "")
        ?.collectAsStateWithLifecycle() ?: mutableStateOf("")

    LaunchedEffect(selectedEquipmentId, selectedEquipmentName) {
        if (selectedEquipmentId.isNotEmpty() && selectedEquipmentName.isNotEmpty()) {
            // Pass the result to the previous screen (CreateExerciseScreen)
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedEquipmentId", selectedEquipmentId)
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedEquipmentName", selectedEquipmentName)

            // Reset the values in the current screen's SavedStateHandle to avoid re-triggering
            navController.currentBackStackEntry?.savedStateHandle?.set("selectedEquipmentId", "")
            navController.currentBackStackEntry?.savedStateHandle?.set("selectedEquipmentName", "")
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
                            scope.launch {
                                viewModel.findAndSetGenericEquipment(equipment)
                                navController.navigateUp()
                            }
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