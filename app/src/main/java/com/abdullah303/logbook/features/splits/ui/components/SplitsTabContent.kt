package com.abdullah303.logbook.features.splits.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.features.splits.data.DummyData
import com.abdullah303.logbook.features.splits.data.Split
import java.time.LocalDate

/**
 * Content for the Splits tab showing the calendar and active split.
 * Displays a week calendar view and the current split's details.
 *
 * @param paddingValues Padding values from the parent Scaffold
 * @param activeSplit The currently active split to display
 */
@Composable
fun SplitsTabContent(
    paddingValues: PaddingValues,
    activeSplit: Split
) {
    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        WeekCalendarView(
            modifier = Modifier.padding(bottom = 24.dp),
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )

        SplitContainer(
            split = activeSplit,
            exerciseTemplates = DummyData.exerciseTemplates,
            onSettingsClick = { /* TODO: Handle settings click */ }
        )
    }
} 