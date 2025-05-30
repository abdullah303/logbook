package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdullah303.logbook.R
import com.abdullah303.logbook.core.utils.WorkoutMuscleHeatmapImage
import com.abdullah303.logbook.core.utils.countTotalSets
import com.abdullah303.logbook.features.splits.data.ExerciseTemplate
import com.abdullah303.logbook.features.splits.data.Split
import com.abdullah303.logbook.features.splits.data.Workout

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun WorkoutCard(
    workout: Workout,
    exerciseTemplates: List<ExerciseTemplate>,
    split: Split,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    // individual letters for editorial layout
    val letters = workout.name.uppercase().toList()

    Card(
        shape = RoundedCornerShape(16.dp),
        border = if (isFocused) BorderStroke(1.dp, Color(0xFFE0E0E0)) else null,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        if (isFocused) {
            // ——— Focused: full workout UI ———
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(end = 88.dp)
                    ) {
                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${workout.countTotalSets()} sets",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    WorkoutMuscleHeatmapImage(
                        workout = workout,
                        heatmapColor = "FF5722",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.TopEnd)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )

                Timeline(
                    exercises = workout.exercises,
                    exerciseTemplates = exerciseTemplates,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        } else {
            // ——— Minimized: true full-height editorial letters ———
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),  // breathing room
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    letters.forEach { letter ->
                        Text(
                            text = letter.toString(),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = FontFamily(
                                    androidx.compose.ui.text.font.Font(
                                        resId = R.font.roboto_flex_variable,
                                        weight = FontWeight.Black,
                                        variationSettings = FontVariation.Settings(
                                            FontVariation.weight(900),
                                            FontVariation.width(150f)
                                        )
                                    )
                                ),
                                fontSize = 40.sp,
                                lineHeight = 40.sp,       // single-line height
                                letterSpacing = (-2).sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
            }
        }
    }
}
