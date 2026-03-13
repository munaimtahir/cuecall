package com.cuecall.app.ui.screens.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.repository.CounterRepository
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.ServiceRepository
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
    private val counterRepository: CounterRepository,
    private val serviceRepository: ServiceRepository,
    private val setupValidator: SetupValidator,
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
            val setup = runCatching { setupValidator.requireClinicSetup() }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "No clinic configured"
                        )
                    }
                }
                .getOrNull()
                ?: return@launch
            val settings = setup.settings
            val clinicId = setup.clinic.id
            val assignedCounter = settings.assignedCounterId?.let { counterRepository.getCounter(it) }
            val serviceId = assignedCounter?.serviceId ?: settings.assignedServiceId.orEmpty()
            val service = serviceId.takeIf { it.isNotBlank() }?.let { serviceRepository.getService(it) }

            if (serviceId.isBlank()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No counter service assigned"
                    )
                }
                return@launch
            }
            if (service == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Selected service does not exist"
                    )
                }
                return@launch
            }
            if (!service.isActive) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Selected service inactive"
                    )
                }
                return@launch
            }

            val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

            _uiState.update {
                it.copy(
                    serviceId = serviceId,
                    serviceName = service.name,
                    counterName = assignedCounter?.name.orEmpty(),
                    queueDayId = queueDay.id,
                    isLoading = false
                )
            }

            // Both flows subscribed in the same coroutine scope; launched independently
            launch {
                tokenRepository.observeWaitingTokens(queueDay.id, serviceId)
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
