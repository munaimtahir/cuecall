package com.cuecall.app.ui.screens.counter

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
import com.cuecall.app.domain.model.Token
import com.cuecall.app.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: CounterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Counter") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.History, "History")
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
            if (uiState.serviceName.isNotBlank() || uiState.counterName.isNotBlank()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                        if (uiState.counterName.isNotBlank()) {
                            Text("Counter: ${uiState.counterName}", style = MaterialTheme.typography.titleMedium)
                        }
                        if (uiState.serviceName.isNotBlank()) {
                            Text(
                                "Service: ${uiState.serviceName}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            // Currently called token
            CurrentTokenCard(
                token = uiState.calledToken,
                onRecall = viewModel::recallCurrent,
                onSkip = viewModel::skipCurrent,
                onComplete = viewModel::completeCurrent
            )

            // Call next
            PrimaryButton(
                text = if (uiState.waitingTokens.isEmpty()) "No tokens waiting" else "Call Next",
                onClick = viewModel::callNext,
                enabled = uiState.waitingTokens.isNotEmpty()
            )

            // Waiting queue list
            SectionHeader("Waiting (${uiState.waitingTokens.size})")
            if (uiState.waitingTokens.isEmpty()) {
                EmptyState("No patients waiting")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.waitingTokens) { token ->
                        WaitingTokenRow(token)
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentTokenCard(
    token: Token?,
    onRecall: () -> Unit,
    onSkip: () -> Unit,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (token != null)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (token != null) "Now Serving" else "No Active Token",
                style = MaterialTheme.typography.titleMedium
            )
            if (token != null) {
                Text(
                    token.displayNumber,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onRecall,
                        modifier = Modifier.weight(1f)
                    ) { Text("Recall") }
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) { Text("Skip") }
                    Button(
                        onClick = onComplete,
                        modifier = Modifier.weight(1f)
                    ) { Text("Done") }
                }
            }
        }
    }
}

@Composable
private fun WaitingTokenRow(token: Token) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(token.displayNumber, style = MaterialTheme.typography.titleMedium)
            Text(
                "Waiting",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
