package com.cuecall.app.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuecall.app.domain.model.Counter
import com.cuecall.app.domain.model.Service
import com.cuecall.app.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterManagementScreen(
    onNavigateBack: () -> Unit,
    viewModel: CounterManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddCounterDialog(
            services = uiState.services,
            onDismiss = { showAddDialog = false },
            onConfirm = { name, serviceId ->
                viewModel.saveCounter(null, name, serviceId)
                showAddDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Counters / Windows") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, "Add Counter")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingScreen()
            return@Scaffold
        }
        if (uiState.counters.isEmpty()) {
            EmptyState("No counters yet.\nTap + to add a window or room label.")
            return@Scaffold
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(uiState.counters, key = { it.id }) { counter ->
                CounterRow(
                    counter = counter,
                    serviceName = uiState.services.firstOrNull { it.id == counter.serviceId }?.name,
                    services = uiState.services,
                    onSave = { name, serviceId -> viewModel.saveCounter(counter.id, name, serviceId) },
                    onDelete = { viewModel.deleteCounter(counter.id) }
                )
            }
        }
    }
}

@Composable
private fun CounterRow(
    counter: Counter,
    serviceName: String?,
    services: List<Service>,
    onSave: (String, String?) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete ${counter.name}?") },
            text = { Text("This removes the counter label. Tokens assigned to it are not affected.") },
            confirmButton = {
                TextButton(onClick = { showDeleteConfirm = false; onDelete() }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }
    if (showEditDialog) {
        AddCounterDialog(
            services = services,
            initialName = counter.name,
            initialServiceId = counter.serviceId,
            title = "Edit Counter",
            confirmLabel = "Save",
            onDismiss = { showEditDialog = false },
            onConfirm = { name, serviceId ->
                showEditDialog = false
                onSave(name, serviceId)
            }
        )
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Default.Window, null, tint = MaterialTheme.colorScheme.primary)
                Column {
                    Text(counter.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        serviceName ?: "No service linked",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Row {
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                IconButton(onClick = { showDeleteConfirm = true }) {
                    Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCounterDialog(
    services: List<Service>,
    initialName: String = "",
    initialServiceId: String? = null,
    title: String = "Add Counter / Window",
    confirmLabel: String = "Add",
    onDismiss: () -> Unit,
    onConfirm: (String, String?) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var selectedServiceId by remember { mutableStateOf(initialServiceId) }
    var expanded by remember { mutableStateOf(false) }
    val selectedServiceName = services.firstOrNull { it.id == selectedServiceId }?.name ?: "Select Service"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Counter Name") },
                    placeholder = { Text("e.g. Window 1, Room 3") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedServiceName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Linked Service") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        services.forEach { service ->
                            DropdownMenuItem(
                                text = { Text(service.name) },
                                onClick = {
                                    selectedServiceId = service.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, selectedServiceId) },
                enabled = name.isNotBlank() && selectedServiceId != null
            ) { Text(confirmLabel) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
