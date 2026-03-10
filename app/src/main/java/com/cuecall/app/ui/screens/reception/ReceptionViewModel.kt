package com.cuecall.app.ui.screens.reception

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.usecase.GenerateTokenUseCase
import com.cuecall.app.printer.PrinterManager
import com.cuecall.app.printer.TokenTicket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReceptionUiState(
    val isLoading: Boolean = false,
    val services: List<Service> = emptyList(),
    val selectedService: Service? = null,
    val lastGeneratedToken: Token? = null,
    val clinicName: String = "",
    val ticketFooterText: String = "Please wait for your turn",
    val error: String? = null,
    val printStatus: PrintStatus = PrintStatus.Idle
)

sealed class PrintStatus {
    object Idle : PrintStatus()
    object Printing : PrintStatus()
    object Success : PrintStatus()
    data class Failure(val reason: String) : PrintStatus()
}

@HiltViewModel
class ReceptionViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val settingsRepository: SettingsRepository,
    private val generateTokenUseCase: GenerateTokenUseCase,
    private val printerManager: PrinterManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReceptionUiState())
    val uiState: StateFlow<ReceptionUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val clinicId = settings.clinicId
            _uiState.update { it.copy(clinicName = clinicId, ticketFooterText = settings.ticketFooterText) }

            serviceRepository.observeActiveServices(clinicId)
                .collect { services ->
                    _uiState.update { state ->
                        state.copy(
                            services = services,
                            selectedService = state.selectedService
                                ?: services.firstOrNull()
                        )
                    }
                }
        }
    }

    fun selectService(service: Service) {
        _uiState.update { it.copy(selectedService = service, error = null) }
    }

    fun generateToken() {
        val serviceId = _uiState.value.selectedService?.id ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, printStatus = PrintStatus.Idle) }
            generateTokenUseCase(serviceId)
                .onSuccess { token ->
                    _uiState.update { it.copy(isLoading = false, lastGeneratedToken = token) }
                    printToken(token)
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to generate token") }
                }
        }
    }

    fun reprintLastToken() {
        val token = _uiState.value.lastGeneratedToken ?: return
        viewModelScope.launch { printToken(token) }
    }

    private suspend fun printToken(token: Token) {
        val state = _uiState.value
        val service = state.services.find { it.id == token.serviceId } ?: return
        _uiState.update { it.copy(printStatus = PrintStatus.Printing) }
        val ticket = TokenTicket(
            clinicName = state.clinicName.ifBlank { "Clinic" },
            serviceName = service.name,
            tokenDisplayNumber = token.displayNumber,
            issuedAt = token.issuedAt,
            footerText = state.ticketFooterText
        )
        printerManager.printTicket(ticket)
            .onSuccess { _uiState.update { it.copy(printStatus = PrintStatus.Success) } }
            .onFailure { e ->
                _uiState.update { it.copy(printStatus = PrintStatus.Failure(e.message ?: "Print failed")) }
            }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
    fun clearPrintStatus() = _uiState.update { it.copy(printStatus = PrintStatus.Idle) }
}
