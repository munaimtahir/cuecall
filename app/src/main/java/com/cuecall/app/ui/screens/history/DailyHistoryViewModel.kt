package com.cuecall.app.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.model.TokenStatus
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class DailyHistoryUiState(
    val isLoading: Boolean = true,
    val businessDate: String = "",
    val tokens: List<Token> = emptyList(),
    val serviceNameMap: Map<String, String> = emptyMap(),
    val totalIssued: Int = 0,
    val totalCompleted: Int = 0,
    val totalSkipped: Int = 0,
    val totalWaiting: Int = 0,
    val error: String? = null
)

@HiltViewModel
class DailyHistoryViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val queueDayRepository: QueueDayRepository,
    private val settingsRepository: SettingsRepository,
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyHistoryUiState())
    val uiState: StateFlow<DailyHistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val clinicId = settings.clinicId
            val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

            val services = serviceRepository.getAllServices(clinicId)
            val serviceNameMap = services.associate { it.id to it.name }

            tokenRepository.observeTokensByQueueDay(queueDay.id)
                .collect { tokens ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            businessDate = businessDate,
                            tokens = tokens.sortedByDescending { t -> t.issuedAt },
                            serviceNameMap = serviceNameMap,
                            totalIssued = tokens.size,
                            totalCompleted = tokens.count { t -> t.status == TokenStatus.COMPLETED },
                            totalSkipped = tokens.count { t -> t.status == TokenStatus.SKIPPED },
                            totalWaiting = tokens.count { t -> t.status == TokenStatus.WAITING }
                        )
                    }
                }
        }
    }
}
