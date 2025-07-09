package com.abdullah303.logbook.ui.create_split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdullah303.logbook.ui.components.BubbleContextMenu
import com.abdullah303.logbook.ui.create_split.components.DayButtonGroup
import com.abdullah303.logbook.ui.create_split.components.DayContainer
import com.abdullah303.logbook.ui.components.InlineEditableText

@Composable
fun CreateSplitScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: CreateSplitViewModel = hiltViewModel()
) {
    val navigationState = CreateSplitScreenNavigation(
        onNavigateBack = onNavigateBack
    )

    val splitTitle by viewModel.splitTitle.collectAsState()
    val days by viewModel.days.collectAsState()
    val selectedDayIndex by viewModel.selectedDayIndex.collectAsState()

    var showMenu by remember { mutableStateOf(false) }
    var menuAnchorOffset by remember { mutableStateOf(Offset.Zero) }
    var contextMenuDayIndex by remember { mutableStateOf<Int?>(null) }
    var renamingDayIndex by remember { mutableStateOf<Int?>(null) }
    val dayButtonPositions = remember { mutableStateMapOf<Int, Offset>() }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InlineEditableText(
                text = splitTitle,
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .alpha(0.9f),
                onTextChange = { viewModel.updateSplitTitle(it) },
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                onDone = {
                    // keyboard will be dismissed automatically
                }
            )

            DayButtonGroup(
                days = days,
                selectedDayIndex = selectedDayIndex,
                onDaySelected = { viewModel.selectDay(it) },
                onAddDay = { viewModel.addDay() },
                onLongPressDay = { index, _ ->
                    contextMenuDayIndex = index
                    menuAnchorOffset = dayButtonPositions[index] ?: Offset.Zero
                    showMenu = true
                },
                onPositionUpdate = { index, offset ->
                    dayButtonPositions[index] = offset
                },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                renamingIndex = renamingDayIndex,
                onRenameDay = { index, newName ->
                    viewModel.renameDay(index, newName)
                },
                onRenameDayFinished = {
                    renamingDayIndex = null
                }
            )

            // day container that takes the rest of the screen
            if (days.isNotEmpty() && selectedDayIndex < days.size) {
                DayContainer(
                    dayName = days[selectedDayIndex]
                )
            }
        }

        BubbleContextMenu(
            showMenu = showMenu,
            onDismiss = { showMenu = false },
            anchorOffset = menuAnchorOffset,
            buttonWidth = 120.dp, // approx width from DayButtonGroup
            onRename = {
                renamingDayIndex = contextMenuDayIndex
            },
            onDelete = {
                contextMenuDayIndex?.let { viewModel.deleteDay(it) }
            },
            canDelete = days.size > 1
        )
    }
} 