package com.abdullah303.logbook.ui.create_split

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.Muscles
import com.abdullah303.logbook.data.repository.ExerciseRepository
import com.abdullah303.logbook.data.repository.EquipmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

/**
 * data class to hold exercise with equipment information
 */
data class ExerciseWithEquipment(
    val exercise: Exercise,
    val equipmentName: String
)

/**
 * viewmodel for managing create split screen state and business logic
 */
@HiltViewModel
class CreateSplitViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val equipmentRepository: EquipmentRepository
) : ViewModel() {
    
    private val _splitTitle = MutableStateFlow("New Split")
    val splitTitle: StateFlow<String> = _splitTitle.asStateFlow()

    private val _days = MutableStateFlow(listOf("Day 1"))
    val days: StateFlow<List<String>> = _days.asStateFlow()

    private val _selectedDayIndex = MutableStateFlow(0)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()
    
    // exercise-related state
    private val _allExercises = MutableStateFlow<List<ExerciseWithEquipment>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedMuscles = MutableStateFlow<Set<Muscles>>(emptySet())
    val selectedMuscles: StateFlow<Set<Muscles>> = _selectedMuscles.asStateFlow()
    
    // filtered exercises based on search query and selected muscles
    val filteredExercises: StateFlow<List<ExerciseWithEquipment>> = combine(
        _allExercises,
        _searchQuery,
        _selectedMuscles
    ) { exercises, query, muscles ->
        exercises.filter { exerciseWithEquipment ->
            val exercise = exerciseWithEquipment.exercise
            // filter by search query
            val matchesSearch = query.isBlank() || 
                exercise.name.contains(query, ignoreCase = true)
            
            // filter by selected muscles (if any selected)
            val matchesMuscles = muscles.isEmpty() || 
                exercise.primaryMuscles.any { it in muscles } ||
                exercise.auxiliaryMuscles.any { it in muscles }
            
            matchesSearch && matchesMuscles
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    /**
     * updates the split title
     * @param newTitle the new title value
     */
    fun updateSplitTitle(newTitle: String) {
        _splitTitle.value = newTitle
    }

    fun selectDay(index: Int) {
        _selectedDayIndex.value = index
    }

    fun addDay() {
        val currentDays = _days.value.toMutableList()
        currentDays.add("Day ${currentDays.size + 1}")
        _days.value = currentDays
        _selectedDayIndex.value = currentDays.lastIndex
    }

    fun renameDay(index: Int, newName: String) {
        val currentDays = _days.value.toMutableList()
        if (index >= 0 && index < currentDays.size) {
            currentDays[index] = newName
            _days.value = currentDays
        }
    }

    fun deleteDay(index: Int) {
        val currentDays = _days.value.toMutableList()
        if (index >= 0 && index < currentDays.size) {
            currentDays.removeAt(index)
            _days.value = currentDays
            if (_selectedDayIndex.value >= currentDays.size) {
                _selectedDayIndex.value = currentDays.size - 1
            }
        }
    }
    
    /**
     * saves the split (placeholder for future implementation)
     */
    fun saveSplit() {
        // todo: implement save logic
        // this will likely involve calling a repository to save the split
    }
    
    // exercise-related functions
    init {
        loadExercises()
    }
    
    /**
     * loads all exercises from the database with equipment information
     */
    private fun loadExercises() {
        viewModelScope.launch {
            try {
                exerciseRepository.getAllExercisesFlow()
                    .collect { exercises ->
                        val exercisesWithEquipment = exercises.mapNotNull { exercise ->
                            try {
                                val equipment = equipmentRepository.getEquipmentById(exercise.equipment_id)
                                equipment?.let { eq ->
                                    ExerciseWithEquipment(
                                        exercise = exercise,
                                        equipmentName = eq.name
                                    )
                                }
                            } catch (e: Exception) {
                                // if equipment not found, skip this exercise
                                null
                            }
                        }
                        _allExercises.value = exercisesWithEquipment
                    }
            } catch (e: Exception) {
                // handle error - could add error state if needed
                _allExercises.value = emptyList()
            }
        }
    }
    
    /**
     * updates the search query for filtering exercises
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * updates the selected muscles for filtering exercises
     */
    fun updateSelectedMuscles(muscles: Set<Muscles>) {
        _selectedMuscles.value = muscles
    }
    
    /**
     * handles exercise selection (placeholder for future implementation)
     */
    fun selectExercise(exerciseWithEquipment: ExerciseWithEquipment) {
        // todo: implement exercise selection logic
        // this will likely involve adding the exercise to the current day
    }
    
    /**
     * refreshes the exercises list
     */
    fun refreshExercises() {
        loadExercises()
    }
} 