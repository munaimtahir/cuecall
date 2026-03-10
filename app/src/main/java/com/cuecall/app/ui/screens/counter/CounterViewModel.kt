package com.cuecall.app.ui.screens.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.repository.TokenRepository
import com.cuecall.app.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class CounterUiState(
    val isLoading: Boolean = true,
    val calledToken: Token? = null,
    val waitingTokens: List<Token> = emptyList(),
    val serviceId: String = "",
    val serviceName: String = "",
    val counterName: String = "",
    val queueDayId: String = "",
    val error: String? = null
)

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val queueDayRepository: QueueDayRepository,
    private val settingsRepository: SettingsRepository,
    private val callNextTokenUseCase: CallNextTokenUseCase,
    private val recallTokenUseCase: RecallTokenUseCase,
    private val skipTokenUseCase: SkipTokenUseCase,
    private val completeTokenUseCase: CompleteTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    init {
        loadQueueData()
    }

    private fun loadQueueData() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val clinicId = settings.clinicId
            val serviceId = settings.assignedServiceId ?: ""
            val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

            _uiState.update {
                it.copy(serviceId = serviceId, queueDayId = queueDay.id, isLoading = false)
            }

            // Both flows subscribed in the same coroutine scope; launched independently
            launch {
                tokenRepository.observeWaitingTokens(queueDay.id, serviceId.ifBlank { null })
                    .collect { tokens -> _uiState.update { it.copy(waitingTokens = tokens) } }
            }
            launch {
                tokenRepository.observeCalledToken(queueDay.id, serviceId)
                    .collect { token -> _uiState.update { it.copy(calledToken = token) } }
            }
        }
    }

    fun callNext() {
        viewModelScope.launch {
            val state = _uiState.value
            callNextTokenUseCase(state.serviceId)
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun recallCurrent() {
        val state = _uiState.value
        val token = state.calledToken ?: return
        viewModelScope.launch {
            recallTokenUseCase(token.id, state.queueDayId)
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun skipCurrent() {
        val state = _uiState.value
        val token = state.calledToken ?: return
        viewModelScope.launch {
            skipTokenUseCase(token.id, state.queueDayId)
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun completeCurrent() {
        val state = _uiState.value
        val token = state.calledToken ?: return
        viewModelScope.launch {
            completeTokenUseCase(token.id, state.queueDayId)
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
