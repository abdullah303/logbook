package com.abdullah303.logbook.features.create_exercise.ui.muscle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abdullah303.logbook.R
import com.abdullah303.logbook.core.utils.Muscle

data class MuscleUiModel(
    val name: String,
    val drawableRes: Int
)

data class MuscleGroup(
    val title: String,
    val muscles: List<MuscleUiModel>
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

    val muscleGroups = remember {
        listOf(
            MuscleGroup(
                title = "PUSH",
                muscles = listOf(
                    MuscleUiModel(Muscle.CHEST.displayName, R.drawable.muscles_chest_focused),
                    MuscleUiModel(Muscle.FRONT_DELTS.displayName, R.drawable.muscles_front_delts_focused),
                    MuscleUiModel(Muscle.SIDE_DELTS.displayName, R.drawable.muscles_side_delts_focused),
                    MuscleUiModel(Muscle.TRICEPS.displayName, R.drawable.muscles_triceps_focused)
                )
            ),
            MuscleGroup(
                title = "PULL",
                muscles = listOf(
                    MuscleUiModel(Muscle.UPPER_BACK.displayName, R.drawable.muscles_upper_back_focused),
                    MuscleUiModel(Muscle.LATS.displayName, R.drawable.muscles_lats_focused),
                    MuscleUiModel(Muscle.REAR_DELTS.displayName, R.drawable.muscles_rear_delts_focused),
                    MuscleUiModel(Muscle.LOWER_BACK.displayName, R.drawable.muscles_lower_back_focused),
                    MuscleUiModel(Muscle.BICEPS.displayName, R.drawable.muscles_biceps_focused),
                    MuscleUiModel(Muscle.FOREARMS.displayName, R.drawable.muscles_forearms_focused)
                )
            ),
            MuscleGroup(
                title = "LOWER",
                muscles = listOf(
                    MuscleUiModel(Muscle.QUADS.displayName, R.drawable.muscles_quads_focused),
                    MuscleUiModel(Muscle.HAMS.displayName, R.drawable.muscles_hamstrings_focused),
                    MuscleUiModel(Muscle.CALVES.displayName, R.drawable.muscles_calves_focused),
                    MuscleUiModel(Muscle.GLUTES.displayName, R.drawable.muscles_glutes_focused),
                    MuscleUiModel(Muscle.ABDUCTORS.displayName, R.drawable.muscles_abductors_focused),
                    MuscleUiModel(Muscle.ADDUCTORS.displayName, R.drawable.muscles_adductors_focused)
                )
            ),
            MuscleGroup(
                title = "CORE",
                muscles = listOf(
                    MuscleUiModel(Muscle.ABS.displayName, R.drawable.muscles_abs_focused),
                    MuscleUiModel(Muscle.OBLIQUES.displayName, R.drawable.muscles_obliques_focused)
                )
            ),
            MuscleGroup(
                title = "MISC",
                muscles = listOf(
                    MuscleUiModel(Muscle.NECK.displayName, R.drawable.muscles_neck_focused),
                    MuscleUiModel(Muscle.GRIP.displayName, R.drawable.muscles_grip_focused)
                )
            )
        )
    }

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
            muscleGroups.forEach { group ->
                // Group header
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                MaterialTheme.shapes.small
                            )
                    ) {
                        Text(
                            text = group.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }
                }
                
                // Muscle cards
                items(group.muscles) { muscle ->
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MuscleCard(
    muscle: MuscleUiModel,
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