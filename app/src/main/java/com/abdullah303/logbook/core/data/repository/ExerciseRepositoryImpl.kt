package com.abdullah303.logbook.core.data.repository

import com.abdullah303.logbook.core.domain.model.Exercise
import com.abdullah303.logbook.core.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepositoryImpl @Inject constructor() : ExerciseRepository {
    
    private val _currentExercise = MutableStateFlow(Exercise())
    
    override fun getCurrentExercise(): Flow<Exercise> = _currentExercise.asStateFlow()
    
    override suspend fun updateExercise(exercise: Exercise) {
        _currentExercise.value = exercise
    }
    
    override suspend fun clearExercise() {
        _currentExercise.value = Exercise()
    }
} 