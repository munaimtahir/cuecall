package com.cuecall.app.ui.screens.display

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuecall.app.domain.model.Token
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DisplayScreen(
    onExitDisplay: () -> Unit,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showExitDialog by remember { mutableStateOf(false) }

    // Intercept back button for display exit guard
    BackHandler { showExitDialog = true }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit Display Mode?") },
            text = { Text("Are you sure you want to exit the display board?") },
            confirmButton = {
                TextButton(onClick = { showExitDialog = false; onExitDisplay() }) {
                    Text("Exit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Stay") }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1628)) // Deep dark blue for TV display
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            DisplayHeader(clinicName = uiState.clinicName, isSynced = uiState.isSynced)

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                // Now Serving section
                NowServingSection(tokens = uiState.calledTokens)

                Divider(color = Color.White.copy(alpha = 0.2f), thickness = 1.dp)

                // Waiting section
                WaitingSection(tokens = uiState.waitingTokens)
            }
        }
    }
}

@Composable
private fun DisplayHeader(clinicName: String, isSynced: Boolean) {
    val timeStr = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    var currentTime by remember { mutableStateOf(timeStr.format(Date())) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = timeStr.format(Date())
            kotlinx.coroutines.delay(60_000)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            clinicName.ifBlank { "CueCall" },
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(currentTime, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            if (!isSynced) {
                Text(
                    "• Syncing…",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Yellow
                )
            }
        }
    }
}

@Composable
private fun NowServingSection(tokens: List<Token>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "NOW SERVING",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White.copy(alpha = 0.7f),
            letterSpacing = 4.sp
        )
        if (tokens.isEmpty()) {
            Text(
                "—",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White.copy(alpha = 0.4f)
            )
        } else {
            // Show primary (most recent call) large, others smaller
            val primary = tokens.first()
            Text(
                primary.displayNumber,
                fontSize = 96.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF4FC3F7), // bright cyan
                lineHeight = 96.sp
            )
        }
    }
}

@Composable
private fun WaitingSection(tokens: List<Token>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "WAITING",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White.copy(alpha = 0.7f),
            letterSpacing = 4.sp
        )
        if (tokens.isEmpty()) {
            Text(
                "No patients waiting",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.5f)
            )
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(tokens) { token ->
                    WaitingTokenBadge(token)
                }
            }
        }
    }
}

@Composable
private fun WaitingTokenBadge(token: Token) {
    Surface(
        color = Color.White.copy(alpha = 0.12f),
        shape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier.size(110.dp, 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                token.displayNumber,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
