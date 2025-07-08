package com.abdullah303.logbook.ui.create_split

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * viewmodel for managing create split screen state and business logic
 */
@HiltViewModel
class CreateSplitViewModel @Inject constructor() : ViewModel() {
    
    private val _splitTitle = MutableStateFlow("New Split")
    val splitTitle: StateFlow<String> = _splitTitle.asStateFlow()

    private val _days = MutableStateFlow(listOf("Day 1"))
    val days: StateFlow<List<String>> = _days.asStateFlow()

    private val _selectedDayIndex = MutableStateFlow(0)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()
    
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
} 