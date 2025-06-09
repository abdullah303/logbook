package com.abdullah303.logbook.features.create_equipment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.outlined.Cable
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.tooling.preview.Preview
import com.abdullah303.logbook.navigation.Screen
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.semantics.semantics
import com.abdullah303.logbook.core.ui.components.FilterOption
import com.abdullah303.logbook.core.ui.components.FilterableListScreen
import com.abdullah303.logbook.features.create_equipment.presentation.EquipmentListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdullah303.logbook.core.domain.model.EquipmentType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EquipmentListScreen(
    navController: NavController,
    equipmentType: String,
    viewModel: EquipmentListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val equipmentTypes = listOf("Cable Stack", "Resistance Machine", "Smith Machine")
    val filterOptions = listOf(
        FilterOption("Cable Stack", Icons.Outlined.Cable),
        FilterOption("Resistance Machine", Icons.Outlined.DeviceHub),
        FilterOption("Smith Machine", Icons.Outlined.SmartToy)
    )
    val checkedIcons = listOf(
        Icons.Filled.Cable,
        Icons.Filled.DeviceHub,
        Icons.Filled.SmartToy
    )
    val initialSelectedIndex = equipmentTypes.indexOf(equipmentType).coerceAtLeast(0)
    var selectedIndex by remember { mutableStateOf(initialSelectedIndex) }
    var searchQuery by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val filteredEquipment = remember(selectedIndex, searchQuery, uiState.equipment) {
        viewModel.filterEquipment(equipmentTypes[selectedIndex], searchQuery)
    }

    FilterableListScreen(
        title = "${equipmentTypes[selectedIndex]} Equipment",
        filterOptions = filterOptions,
        items = filteredEquipment,
        selectedFilterIndex = selectedIndex,
        onFilterSelected = { selectedIndex = it },
        itemContent = { equipment ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("selectedEquipment", equipment.id)
                    navController.navigateUp()
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(equipment.name)
                }
            }
        },
        fabContent = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CreateEquipment.createRoute(equipmentTypes[selectedIndex])) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Equipment")
            }
        },
        modifier = modifier,
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        isLoading = uiState.isLoading,
        error = uiState.error
    )
} 