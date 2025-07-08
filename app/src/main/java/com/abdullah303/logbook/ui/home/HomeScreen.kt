package com.abdullah303.logbook.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abdullah303.logbook.ui.components.FABMenu
import com.abdullah303.logbook.ui.home.components.NavigationBar
import com.abdullah303.logbook.ui.theme.LogbookTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToCreateSplit: () -> Unit = {},
    onNavigateToStandaloneWorkout: () -> Unit = {}
) {
    val navigationState = HomeScreenNavigation(
        onNavigateToCreateSplit = onNavigateToCreateSplit,
        onNavigateToStandaloneWorkout = onNavigateToStandaloneWorkout
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                selectedItem = navigationState.selectedItem,
                onItemSelected = navigationState.onItemSelected
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // fab menu for split section - only show when splits tab is selected
            if (navigationState.selectedItem == 0) {
                FABMenu(
                    items = navigationState.fabMenuItems,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
