package com.cuecall.app.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuecall.app.domain.model.DeviceMode
import com.cuecall.app.ui.components.LoadingScreen
import com.cuecall.app.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAssignmentScreen(
    onNavigateBack: () -> Unit,
    viewModel: DeviceAssignmentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var counterMenuExpanded by remember { mutableStateOf(false) }
    val selectedCounterName = uiState.counters.firstOrNull { it.id == uiState.assignedCounterId }?.name
        ?: "Select Counter"

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Device Assignment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingScreen()
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Device ID", style = MaterialTheme.typography.titleMedium)
                    Text(
                        uiState.deviceId,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            SectionHeader("Mode")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    DeviceMode.RECEPTION to "Reception",
                    DeviceMode.COUNTER to "Counter",
                    DeviceMode.DISPLAY to "Display",
                    DeviceMode.UNASSIGNED to "Unassigned"
                ).forEach { (mode, label) ->
                    FilterChip(
                        selected = uiState.deviceMode == mode,
                        onClick = { viewModel.setDeviceMode(mode) },
                        label = { Text(label) }
                    )
                }
            }

            if (uiState.deviceMode == DeviceMode.COUNTER) {
                SectionHeader("Counter")
                ExposedDropdownMenuBox(
                    expanded = counterMenuExpanded,
                    onExpandedChange = { counterMenuExpanded = !counterMenuExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCounterName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Assigned Counter") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(counterMenuExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = counterMenuExpanded,
                        onDismissRequest = { counterMenuExpanded = false }
                    ) {
                        uiState.counters.forEach { counter ->
                            DropdownMenuItem(
                                text = { Text(counter.name) },
                                onClick = {
                                    counterMenuExpanded = false
                                    viewModel.setAssignedCounter(counter.id)
                                }
                            )
                        }
                    }
                }

                Text(
                    text = uiState.currentServiceName?.let { "Linked service: $it" }
                        ?: "Selected counter has no linked service.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = viewModel::saveAssignment,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Device Assignment")
            }
        }
    }
}
