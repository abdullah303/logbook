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
    
    /**
     * updates the split title
     * @param newTitle the new title value
     */
    fun updateSplitTitle(newTitle: String) {
        _splitTitle.value = newTitle
    }
    
    /**
     * saves the split (placeholder for future implementation)
     */
    fun saveSplit() {
        // todo: implement save logic
        // this will likely involve calling a repository to save the split
    }
} 