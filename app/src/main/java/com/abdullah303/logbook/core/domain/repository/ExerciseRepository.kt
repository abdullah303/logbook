package com.abdullah303.logbook.core.domain.repository

import com.abdullah303.logbook.core.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getCurrentExercise(): Flow<Exercise>
    suspend fun updateExercise(exercise: Exercise)
    suspend fun clearExercise()
} 