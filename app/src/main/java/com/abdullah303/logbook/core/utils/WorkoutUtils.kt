package com.abdullah303.logbook.core.utils

import com.abdullah303.logbook.features.home.data.Workout

/**
 * Counts the total number of sets in a workout by summing up the sets from each exercise.
 */
fun Workout.countTotalSets(): Int {
    return exercises.sumOf { it.sets }
} 