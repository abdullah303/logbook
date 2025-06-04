package com.abdullah303.logbook.features.create_exercise.ui.muscle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah303.logbook.R

data class Muscle(
    val name: String,
    val drawableRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuscleSelectionScreen(
    navController: NavController,
    isMultiSelect: Boolean = false,
    selectedMuscles: List<String> = emptyList(),
    onMuscleSelected: (List<String>) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedMusclesList by remember { mutableStateOf(selectedMuscles) }
    
    val muscles = listOf(
        Muscle("Abs", R.drawable.muscles_abs),
        Muscle("Biceps", R.drawable.muscles_biceps),
        Muscle("Triceps", R.drawable.muscles_triceps),
        Muscle("Chest", R.drawable.muscles_chest),
        Muscle("Back", R.drawable.muscles_back),
        Muscle("Shoulders", R.drawable.muscles_shoulders),
        Muscle("Quadriceps", R.drawable.muscles_quadriceps),
        Muscle("Hamstring", R.drawable.muscles_hamstring),
        Muscle("Calves", R.drawable.muscles_calves),
        Muscle("Gluteus", R.drawable.muscles_gluteus),
        Muscle("Forearms", R.drawable.muscles_forearms),
        Muscle("Latissimus", R.drawable.muscles_latissimus),
        Muscle("Core", R.drawable.muscles_core),
        Muscle("Neck", R.drawable.muscles_neck),
        Muscle("Adductors", R.drawable.muscles_adductors),
        Muscle("Abductors", R.drawable.muscles_abductors)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(if (isMultiSelect) "Select Muscles" else "Select Muscle") 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (isMultiSelect && selectedMusclesList.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onMuscleSelected(selectedMusclesList)
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Confirm Selection"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(muscles) { muscle ->
                MuscleCard(
                    muscle = muscle,
                    isSelected = selectedMusclesList.contains(muscle.name),
                    onClick = {
                        if (isMultiSelect) {
                            selectedMusclesList = if (selectedMusclesList.contains(muscle.name)) {
                                selectedMusclesList - muscle.name
                            } else {
                                selectedMusclesList + muscle.name
                            }
                        } else {
                            onMuscleSelected(listOf(muscle.name))
                            navController.navigateUp()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MuscleCard(
    muscle: Muscle,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(
                width = 2.dp,
                brush = androidx.compose.ui.graphics.SolidColor(
                    MaterialTheme.colorScheme.primary
                )
            )
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(muscle.drawableRes),
                contentDescription = muscle.name,
                modifier = Modifier
                    .size(80.dp)
                    .weight(1f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = muscle.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
} 