package com.abdullah303.logbook.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abdullah303.logbook.ui.components.FABMenu
import com.abdullah303.logbook.ui.components.FABMenuItem
import com.abdullah303.logbook.ui.home.components.NavigationBar
import com.abdullah303.logbook.ui.theme.LogbookTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    // fab menu items for split section
    val fabMenuItems = listOf(
        FABMenuItem(
            icon = Icons.Filled.Add,
            text = "Create Split",
            onClick = {
                // handle create split action
            }
        ),
        FABMenuItem(
            icon = Icons.Filled.FitnessCenter,
            text = "Standalone Workout",
            onClick = {
                // handle standalone workout action
            }
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // fab menu for split section - only show when splits tab is selected
            if (selectedItem == 0) {
                FABMenu(
                    items = fabMenuItems,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
