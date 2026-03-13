package com.cuecall.app.ui.screens.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuecall.app.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: PrinterSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = androidx.compose.ui.platform.LocalContext.current
    val requiresBluetoothConnectPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val hasBluetoothConnectPermission = !requiresBluetoothConnectPermission ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.refreshPairedDevices(granted || !requiresBluetoothConnectPermission)
    }

    LaunchedEffect(hasBluetoothConnectPermission) {
        viewModel.refreshPairedDevices(hasBluetoothConnectPermission)
        if (requiresBluetoothConnectPermission && !hasBluetoothConnectPermission) {
            permissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearError() }
    }
    LaunchedEffect(uiState.message) {
        uiState.message?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessage() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Printer Settings") },
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
            if (requiresBluetoothConnectPermission && !uiState.connectPermissionGranted) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Bluetooth permission required", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "CueCall needs Bluetooth permission to read bonded printers and connect to the selected printer.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT) }
                        ) {
                            Text("Grant Permission")
                        }
                    }
                }
            }

            if (!uiState.bluetoothEnabled) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("Bluetooth is disabled", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Enable Bluetooth in Android settings to view paired printers.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Connection status
            ConnectionStatusCard(
                isConnected = uiState.isConnected,
                printerName = uiState.connectedPrinterName ?: uiState.pairedPrinterName,
                onTestConnect = viewModel::testConnect,
                hasPairedPrinter = uiState.pairedPrinterAddress != null
            )

            // Paper width
            SectionHeader("Paper Width")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(58, 80).forEach { width ->
                    FilterChip(
                        selected = uiState.paperWidth == width,
                        onClick = { viewModel.setPaperWidth(width) },
                        label = { Text("${width}mm") }
                    )
                }
            }

            // Paired devices from system Bluetooth
            SectionHeader("Bluetooth Paired Devices")
            if (uiState.availablePairedDevices.isEmpty()) {
                Text(
                    when {
                        requiresBluetoothConnectPermission && !uiState.connectPermissionGranted ->
                            "Grant Bluetooth permission to load bonded printers."
                        !uiState.bluetoothAvailable ->
                            "Bluetooth is not available on this device."
                        !uiState.bluetoothEnabled ->
                            "Enable Bluetooth, then return to refresh the bonded printer list."
                        else ->
                            "No Bluetooth devices paired on this device.\nPair a printer in Android Bluetooth settings first."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.availablePairedDevices) { device ->
                        val isSelected = device.address == uiState.pairedPrinterAddress
                        Card(
                            onClick = { viewModel.selectPrinter(device) },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(device.name, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        device.address,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConnectionStatusCard(
    isConnected: Boolean,
    printerName: String?,
    onTestConnect: () -> Unit,
    hasPairedPrinter: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isConnected)
                MaterialTheme.colorScheme.secondaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    if (isConnected) "Connected" else "Not Connected",
                    style = MaterialTheme.typography.titleMedium
                )
                if (printerName != null) {
                    Text(
                        printerName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (hasPairedPrinter && !isConnected) {
                Button(onClick = onTestConnect) { Text("Connect") }
            }
        }
    }
}
