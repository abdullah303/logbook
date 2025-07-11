package com.abdullah303.logbook.ui.create_split

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.model.Muscles
import com.abdullah303.logbook.data.model.Side
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
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * data class to hold exercise with equipment information
 */
data class ExerciseWithEquipment(
    val exercise: Exercise,
    val equipmentName: String
)

/**
 * data class to represent a workout exercise with its parameters
 */
data class WorkoutExercise(
    val id: String, // unique identifier for this workout exercise
    val exercise: Exercise,
    val equipmentName: String,
    val sets: Int = 3,
    val repMin: Int = 8,
    val repMax: Int = 12,
    val rir: Float = 2.0f, // rating of perceived exertion in reserve
    val isUnilateral: Boolean = false,
    val sides: List<Side> = listOf(Side.LEFT, Side.RIGHT),
    val supersetGroupId: String? = null // null means not in a superset
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
    
    // exercises per day - map of day index to list of workout exercises
    private val _dayExercises = MutableStateFlow<Map<Int, List<WorkoutExercise>>>(emptyMap())
    val dayExercises: StateFlow<Map<Int, List<WorkoutExercise>>> = _dayExercises.asStateFlow()
    
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
            
            // remove exercises for this day and shift indexes
            val currentExercises = _dayExercises.value.toMutableMap()
            currentExercises.remove(index)
            
            // shift remaining day indexes
            val updatedExercises = mutableMapOf<Int, List<WorkoutExercise>>()
            currentExercises.forEach { (dayIndex, exercises) ->
                if (dayIndex > index) {
                    updatedExercises[dayIndex - 1] = exercises
                } else {
                    updatedExercises[dayIndex] = exercises
                }
            }
            _dayExercises.value = updatedExercises
            
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
     * handles exercise selection - adds the exercise to the current day
     */
    fun selectExercise(exerciseWithEquipment: ExerciseWithEquipment) {
        val currentDayIndex = _selectedDayIndex.value
        val currentExercises = _dayExercises.value.toMutableMap()
        val exercisesForDay = currentExercises[currentDayIndex]?.toMutableList() ?: mutableListOf()
        
        // create a new workout exercise with unique id
        val workoutExercise = WorkoutExercise(
            id = "${exerciseWithEquipment.exercise.id}_${System.currentTimeMillis()}",
            exercise = exerciseWithEquipment.exercise,
            equipmentName = exerciseWithEquipment.equipmentName
        )
        
        exercisesForDay.add(workoutExercise)
        currentExercises[currentDayIndex] = exercisesForDay
        _dayExercises.value = currentExercises
    }
    
    /**
     * updates parameters for a workout exercise
     */
    fun updateWorkoutExercise(
        exerciseId: String,
        sets: Int? = null,
        repRange: Pair<Int, Int>? = null,
        rir: Float? = null,
        isUnilateral: Boolean? = null,
        sideOrder: List<Side>? = null
    ) {
        val currentExercises = _dayExercises.value.toMutableMap()
        // find the exercise across all days
        currentExercises.forEach { (dayIndex, exercises) ->
            val exerciseIndex = exercises.indexOfFirst { it.id == exerciseId }
            if (exerciseIndex != -1) {
                val updatedExercises = exercises.toMutableList()
                val currentExercise = updatedExercises[exerciseIndex]

                updatedExercises[exerciseIndex] = currentExercise.copy(
                    sets = sets ?: currentExercise.sets,
                    repMin = repRange?.first ?: currentExercise.repMin,
                    repMax = repRange?.second ?: currentExercise.repMax,
                    rir = rir ?: currentExercise.rir,
                    isUnilateral = isUnilateral ?: currentExercise.isUnilateral,
                    sides = sideOrder ?: currentExercise.sides
                )

                currentExercises[dayIndex] = updatedExercises
                _dayExercises.value = currentExercises
                return
            }
        }
    }

    /**
     * toggles unilateral state for a workout exercise
     */
    fun toggleUnilateral(exerciseId: String) {
        // flip unilateral flag, keep side order as is or default if turning on
        val currentExercises = _dayExercises.value.toMutableMap()
        currentExercises.forEach { (dayIndex, exercises) ->
            val idx = exercises.indexOfFirst { it.id == exerciseId }
            if (idx != -1) {
                val updated = exercises.toMutableList()
                val ex = updated[idx]
                val newIsUnilateral = !ex.isUnilateral
                updated[idx] = ex.copy(
                    isUnilateral = newIsUnilateral,
                    sides = if (newIsUnilateral) ex.sides else ex.sides // keep order; unused when off
                )
                currentExercises[dayIndex] = updated
                _dayExercises.value = currentExercises
                return
            }
        }
    }

    /**
     * updates the internal side order for a unilateral exercise
     */
    fun updateSideOrder(exerciseId: String, newOrder: List<Side>) {
        if (newOrder.size != 2) return
        val currentExercises = _dayExercises.value.toMutableMap()
        currentExercises.forEach { (dayIndex, exercises) ->
            val idx = exercises.indexOfFirst { it.id == exerciseId }
            if (idx != -1) {
                val updated = exercises.toMutableList()
                val ex = updated[idx]
                if (ex.isUnilateral) {
                    updated[idx] = ex.copy(sides = newOrder)
                    currentExercises[dayIndex] = updated
                    _dayExercises.value = currentExercises
                }
                return
            }
        }
    }
    
    /**
     * removes a workout exercise from a day
     */
    fun removeWorkoutExercise(exerciseId: String) {
        val currentExercises = _dayExercises.value.toMutableMap()
        
        // find and remove the exercise across all days
        currentExercises.forEach { (dayIndex, exercises) ->
            val exerciseIndex = exercises.indexOfFirst { it.id == exerciseId }
            if (exerciseIndex != -1) {
                val updatedExercises = exercises.toMutableList()
                updatedExercises.removeAt(exerciseIndex)
                currentExercises[dayIndex] = updatedExercises
                _dayExercises.value = currentExercises
                return
            }
        }
    }
    
    /**
     * reorders workout exercises within a specific day
     */
    fun reorderWorkoutExercises(dayIndex: Int, fromIndex: Int, toIndex: Int) {
        val currentExercises = _dayExercises.value.toMutableMap()
        val exercisesForDay = currentExercises[dayIndex]?.toMutableList() ?: return
        
        if (fromIndex >= 0 && fromIndex < exercisesForDay.size && 
            toIndex >= 0 && toIndex < exercisesForDay.size && 
            fromIndex != toIndex) {
            
            // perform the reorder operation
            val item = exercisesForDay.removeAt(fromIndex)
            exercisesForDay.add(toIndex, item)
            
            currentExercises[dayIndex] = exercisesForDay
            _dayExercises.value = currentExercises
        }
    }
    
    /**
     * refreshes the exercises list
     */
    fun refreshExercises() {
        loadExercises()
    }
    
    /**
     * adds a specific exercise by ID to the current day
     */
    fun addExerciseById(exerciseId: String) {
        viewModelScope.launch {
            try {
                // get the exercise from the database
                val exercise = exerciseRepository.getExerciseById(exerciseId)
                if (exercise != null) {
                    // get equipment name for the exercise
                    val equipment = equipmentRepository.getEquipmentById(exercise.equipment_id)
                    val equipmentName = equipment?.name ?: "Unknown Equipment"
                    
                    // create exercise with equipment
                    val exerciseWithEquipment = ExerciseWithEquipment(
                        exercise = exercise,
                        equipmentName = equipmentName
                    )
                    
                    // check if this exercise is already in the current day
                    val currentDayIndex = _selectedDayIndex.value
                    val currentExercises = _dayExercises.value[currentDayIndex] ?: emptyList()
                    val isAlreadyAdded = currentExercises.any { it.exercise.id == exercise.id }
                    
                    // only add if not already present
                    if (!isAlreadyAdded) {
                        selectExercise(exerciseWithEquipment)
                    }
                }
                
            } catch (e: Exception) {
                // handle error - could add error state if needed
                e.printStackTrace()
            }
        }
    }
    
    /**
     * adds the most recently created exercise to the current day
     * this method is called when returning from the create exercise screen
     */
    fun addMostRecentExercise() {
        viewModelScope.launch {
            try {
                // refresh exercises list to get the most recent one
                refreshExercises()
                
                // get the most recent exercise from the database
                val exercises = exerciseRepository.getAllExercisesFlow().first()
                if (exercises.isNotEmpty()) {
                    val mostRecentExercise = exercises.maxByOrNull { it.id } // assuming id is timestamp-based
                    mostRecentExercise?.let { exercise ->
                        // get equipment name for the exercise
                        val equipment = equipmentRepository.getEquipmentById(exercise.equipment_id)
                        val equipmentName = equipment?.name ?: "Unknown Equipment"
                        
                        // create exercise with equipment
                        val exerciseWithEquipment = ExerciseWithEquipment(
                            exercise = exercise,
                            equipmentName = equipmentName
                        )
                        
                        // check if this exercise is already in the current day
                        val currentDayIndex = _selectedDayIndex.value
                        val currentExercises = _dayExercises.value[currentDayIndex] ?: emptyList()
                        val isAlreadyAdded = currentExercises.any { it.exercise.id == exercise.id }
                        
                        // only add if not already present
                        if (!isAlreadyAdded) {
                            selectExercise(exerciseWithEquipment)
                        }
                    }
                }
                
            } catch (e: Exception) {
                // handle error - could add error state if needed
                e.printStackTrace()
            }
        }
    }

    /**
     * creates a new superset group with the given exercise
     */
    fun createSuperset(exerciseId: String) {
        val currentExercises = _dayExercises.value.toMutableMap()
        currentExercises.forEach { (dayIndex, exercises) ->
            val exerciseIndex = exercises.indexOfFirst { it.id == exerciseId }
            if (exerciseIndex != -1) {
                val updatedExercises = exercises.toMutableList()
                val currentExercise = updatedExercises[exerciseIndex]
                val supersetGroupId = "superset_${System.currentTimeMillis()}"
                
                updatedExercises[exerciseIndex] = currentExercise.copy(
                    supersetGroupId = supersetGroupId
                )
                
                currentExercises[dayIndex] = updatedExercises
                _dayExercises.value = currentExercises
                return
            }
        }
    }
    
    /**
     * adds an exercise to an existing superset group
     */
    fun addToSuperset(exerciseId: String, targetSupersetGroupId: String) {
        val currentExercises = _dayExercises.value.toMutableMap()
        currentExercises.forEach { (dayIndex, exercises) ->
            val exerciseIndex = exercises.indexOfFirst { it.id == exerciseId }
            if (exerciseIndex != -1) {
                val updatedExercises = exercises.toMutableList()
                val currentExercise = updatedExercises[exerciseIndex]
                
                updatedExercises[exerciseIndex] = currentExercise.copy(
                    supersetGroupId = targetSupersetGroupId
                )
                
                currentExercises[dayIndex] = updatedExercises
                _dayExercises.value = currentExercises
                return
            }
        }
    }
    
    /**
     * removes an exercise from its superset group
     */
    fun removeFromSuperset(exerciseId: String) {
        val currentExercises = _dayExercises.value.toMutableMap()
        currentExercises.forEach { (dayIndex, exercises) ->
            val exerciseIndex = exercises.indexOfFirst { it.id == exerciseId }
            if (exerciseIndex != -1) {
                val updatedExercises = exercises.toMutableList()
                val currentExercise = updatedExercises[exerciseIndex]
                
                updatedExercises[exerciseIndex] = currentExercise.copy(
                    supersetGroupId = null
                )
                
                currentExercises[dayIndex] = updatedExercises
                _dayExercises.value = currentExercises
                return
            }
        }
    }
    
    /**
     * gets all exercises in a superset group for a given day
     */
    fun getExercisesInSuperset(dayIndex: Int, supersetGroupId: String): List<WorkoutExercise> {
        val exercises = _dayExercises.value[dayIndex] ?: emptyList()
        return exercises.filter { it.supersetGroupId == supersetGroupId }
    }
} 