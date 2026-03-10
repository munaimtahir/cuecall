package com.cuecall.app.ui.screens.settings

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.printer.PrinterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrinterSettingsUiState(
    val isLoading: Boolean = true,
    val pairedPrinterAddress: String? = null,
    val pairedPrinterName: String? = null,
    val availablePairedDevices: List<BluetoothDeviceInfo> = emptyList(),
    val isConnected: Boolean = false,
    val connectedPrinterName: String? = null,
    val paperWidth: Int = 58,
    val error: String? = null,
    val message: String? = null
)

data class BluetoothDeviceInfo(val name: String, val address: String)

@HiltViewModel
class PrinterSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val printerManager: PrinterManager,
    private val bluetoothAdapter: BluetoothAdapter?
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrinterSettingsUiState())
    val uiState: StateFlow<PrinterSettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val paired = getSystemPairedDevices()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    pairedPrinterAddress = settings.pairedPrinterAddress,
                    pairedPrinterName = settings.pairedPrinterName,
                    paperWidth = settings.printerPaperWidth,
                    availablePairedDevices = paired,
                    isConnected = printerManager.isConnected(),
                    connectedPrinterName = printerManager.connectedPrinterName()
                )
            }
        }
    }

    private fun getSystemPairedDevices(): List<BluetoothDeviceInfo> {
        return try {
            bluetoothAdapter?.bondedDevices
                ?.map { device -> BluetoothDeviceInfo(device.name ?: "Unknown", device.address) }
                ?: emptyList()
        } catch (e: SecurityException) {
            emptyList()
        }
    }

    fun selectPrinter(device: BluetoothDeviceInfo) {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            settingsRepository.saveSettings(
                settings.copy(
                    pairedPrinterAddress = device.address,
                    pairedPrinterName = device.name
                )
            )
            _uiState.update {
                it.copy(pairedPrinterAddress = device.address, pairedPrinterName = device.name)
            }
        }
    }

    fun testConnect() {
        val address = _uiState.value.pairedPrinterAddress ?: return
        viewModelScope.launch {
            printerManager.connect(address)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isConnected = true,
                            connectedPrinterName = printerManager.connectedPrinterName(),
                            message = "Connected successfully"
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isConnected = false, error = "Connection failed: ${e.message}") }
                }
        }
    }

    fun setPaperWidth(width: Int) {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            settingsRepository.saveSettings(settings.copy(printerPaperWidth = width))
            _uiState.update { it.copy(paperWidth = width) }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
    fun clearMessage() = _uiState.update { it.copy(message = null) }
}
