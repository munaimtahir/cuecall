package com.cuecall.app.ui.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.model.TokenStatus
import com.cuecall.app.ui.components.LoadingScreen
import com.cuecall.app.ui.components.SectionHeader
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyHistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: DailyHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily History — ${uiState.businessDate}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingScreen()
            return@Scaffold
        }
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Summary row
            SummaryRow(
                issued = uiState.totalIssued,
                completed = uiState.totalCompleted,
                skipped = uiState.totalSkipped,
                waiting = uiState.totalWaiting
            )
            Divider()
            if (uiState.tokens.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tokens issued today", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(uiState.tokens, key = { it.id }) { token ->
                        TokenHistoryRow(
                            token = token,
                            serviceName = uiState.serviceNameMap[token.serviceId] ?: "Unknown"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(issued: Int, completed: Int, skipped: Int, waiting: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryChip("Issued", issued, MaterialTheme.colorScheme.primary, Modifier.weight(1f))
        SummaryChip("Done", completed, Color(0xFF2E7D32), Modifier.weight(1f))
        SummaryChip("Skipped", skipped, MaterialTheme.colorScheme.error, Modifier.weight(1f))
        SummaryChip("Waiting", waiting, MaterialTheme.colorScheme.secondary, Modifier.weight(1f))
    }
}

@Composable
private fun SummaryChip(label: String, count: Int, color: Color, modifier: Modifier) {
    Surface(
        color = color.copy(alpha = 0.12f),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(count.toString(), style = MaterialTheme.typography.headlineMedium, color = color)
            Text(label, style = MaterialTheme.typography.bodySmall, color = color)
        }
    }
}

@Composable
private fun TokenHistoryRow(token: Token, serviceName: String) {
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val statusColor = when (token.status) {
        TokenStatus.COMPLETED -> Color(0xFF2E7D32)
        TokenStatus.SKIPPED -> MaterialTheme.colorScheme.error
        TokenStatus.WAITING -> MaterialTheme.colorScheme.secondary
        TokenStatus.CALLED, TokenStatus.RECALLED -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(token.displayNumber, style = MaterialTheme.typography.titleMedium)
                Text(serviceName, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    token.status.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = statusColor
                )
                Text(
                    timeFormat.format(Date(token.issuedAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
