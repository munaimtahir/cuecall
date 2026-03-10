package com.cuecall.app.ui.screens.reception

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
import com.cuecall.app.domain.model.Service
import com.cuecall.app.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceptionScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: ReceptionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.printStatus) {
        when (val status = uiState.printStatus) {
            is PrintStatus.Success -> {
                snackbarHostState.showSnackbar("Token printed successfully")
                viewModel.clearPrintStatus()
            }
            is PrintStatus.Failure -> {
                snackbarHostState.showSnackbar("Print failed: ${status.reason}")
                viewModel.clearPrintStatus()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reception") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            // Service selector
            SectionHeader("Select Service")
            ServiceSelector(
                services = uiState.services,
                selected = uiState.selectedService,
                onSelect = viewModel::selectService
            )

            Spacer(Modifier.weight(1f))

            // Last generated token display
            uiState.lastGeneratedToken?.let { token ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Token Generated", style = MaterialTheme.typography.titleMedium)
                        Text(
                            token.displayNumber,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            uiState.services.find { it.id == token.serviceId }?.name ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Primary action
            PrimaryButton(
                text = "Generate Token",
                onClick = viewModel::generateToken,
                enabled = uiState.selectedService != null && !uiState.isLoading
            )

            // Reprint
            if (uiState.lastGeneratedToken != null) {
                SecondaryButton(
                    text = "Reprint Last Token",
                    onClick = viewModel::reprintLastToken,
                    enabled = uiState.printStatus !is PrintStatus.Printing
                )
            }

            // Print status indicator
            if (uiState.printStatus is PrintStatus.Printing) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("Printing…", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun ServiceSelector(
    services: List<Service>,
    selected: Service?,
    onSelect: (Service) -> Unit
) {
    if (services.isEmpty()) {
        EmptyState("No services configured.\nPlease add services in Settings.")
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(services) { service ->
            val isSelected = service.id == selected?.id
            Card(
                onClick = { onSelect(service) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                ),
                border = if (isSelected)
                    ButtonDefaults.outlinedButtonBorder
                else null
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(service.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Prefix: ${service.tokenPrefix}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (isSelected) {
                        Icon(Icons.Default.CheckCircle, "Selected", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
