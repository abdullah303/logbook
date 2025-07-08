package com.abdullah303.logbook.ui.create_split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
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
    }
} 