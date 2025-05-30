package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.splits.data.ExerciseTemplate
import com.abdullah303.logbook.features.splits.data.Split
import com.abdullah303.logbook.features.splits.data.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCarousel(
    workouts: List<Workout>,
    exerciseTemplates: List<ExerciseTemplate>,
    split: Split,
    modifier: Modifier = Modifier
) {
    val carouselState = rememberCarouselState { workouts.size }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = modifier
            .width(412.dp)
            .height(320.dp),
        preferredItemWidth = 280.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { index ->
        val drawInfo = carouselItemDrawInfo
        val isFocused = drawInfo.size == drawInfo.maxSize

        WorkoutCard(
            workout = workouts[index],
            exerciseTemplates = exerciseTemplates,
            split = split,
            isFocused = isFocused,
            modifier = Modifier.fillMaxHeight()
        )
    }
}