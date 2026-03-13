package com.cuecall.app.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToClinicSetup: () -> Unit,
    onNavigateToDeviceAssignment: () -> Unit,
    onNavigateToServices: () -> Unit,
    onNavigateToCounters: () -> Unit,
    onNavigateToPrinter: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsNavigationItem(
                icon = Icons.Default.LocalHospital,
                title = "Clinic Setup",
                subtitle = "Configure clinic identity and required setup",
                onClick = onNavigateToClinicSetup
            )
            SettingsNavigationItem(
                icon = Icons.Default.Devices,
                title = "Device Assignment",
                subtitle = "Set this device mode and counter assignment",
                onClick = onNavigateToDeviceAssignment
            )
            SettingsNavigationItem(
                icon = Icons.Default.MedicalServices,
                title = "Services",
                subtitle = "Manage queue services and prefixes",
                onClick = onNavigateToServices
            )
            SettingsNavigationItem(
                icon = Icons.Default.Window,
                title = "Counters",
                subtitle = "Manage counter windows/rooms",
                onClick = onNavigateToCounters
            )
            SettingsNavigationItem(
                icon = Icons.Default.Print,
                title = "Printer Settings",
                subtitle = "Pair and configure Bluetooth printer",
                onClick = onNavigateToPrinter
            )
            SettingsNavigationItem(
                icon = Icons.Default.History,
                title = "Daily History",
                subtitle = "View today's token log",
                onClick = onNavigateToHistory
            )
        }
    }
}

@Composable
private fun SettingsNavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
