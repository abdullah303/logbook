package com.abdullah303.logbook.features.graphs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.core.ui.components.ContainedLoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphsScreen(paddingValues: PaddingValues) {
    var isLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = "Progress Graphs",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isLoading) {
            ContainedLoadingIndicator()
        } else {
            // TODO: Add graph components here
            Text(
                text = "Coming soon: Progress tracking and analytics",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 