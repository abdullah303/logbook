package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.ui.components.GenericBottomSheet

// data class to combine equipment and cable stack info
data class CableStackConfiguration(
    val equipment: Equipment,
    val cableStackInfo: CableStackInfo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CableStackSelectionBottomSheet(
    cableStackConfigurations: List<CableStackConfiguration>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectCableStack: (CableStackConfiguration) -> Unit = {},
    onNavigateToCreateCableStack: () -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var searchQuery by remember { mutableStateOf("") }
    
    // filter configurations based on search query
    val filteredConfigurations = cableStackConfigurations.filter {
        it.equipment.name.contains(searchQuery, ignoreCase = true)
    }

    GenericBottomSheet(
        title = "Cable Stack Configurations",
        modifier = modifier,
        onDismiss = onDismiss,
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    ) {
        // search bar with add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Search configurations...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                IconButton(
                    onClick = onNavigateToCreateCableStack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Cable Stack Configuration",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // configurations list
        if (filteredConfigurations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) {
                        "No Cable Stack configurations available yet"
                    } else {
                        "No configurations found matching \"$searchQuery\""
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredConfigurations) { configuration ->
                    CableStackConfigurationCard(
                        configuration = configuration,
                        onSelect = { onSelectCableStack(configuration) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CableStackConfigurationCard(
    configuration: CableStackConfiguration,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onSelect,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = configuration.equipment.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            val weightUnit = configuration.equipment.weight_unit.name.lowercase()
            Text(
                text = "Weight Range: ${configuration.cableStackInfo.min_weight} - ${configuration.cableStackInfo.max_weight} $weightUnit",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Text(
                text = "Increment: ${configuration.cableStackInfo.increment} $weightUnit",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 