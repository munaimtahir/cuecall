package com.cuecall.app.ui.screens.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Token
import com.cuecall.app.domain.model.TokenStatus
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.repository.TokenRepository
import com.cuecall.app.sync.TokenSyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class DisplayUiState(
    val isLoading: Boolean = true,
    val clinicName: String = "Clinic",
    val calledTokens: List<Token> = emptyList(),
    val waitingTokens: List<Token> = emptyList(),
    val filterServiceId: String? = null,
    val isSynced: Boolean = false
)

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val queueDayRepository: QueueDayRepository,
    private val settingsRepository: SettingsRepository,
    private val tokenSyncManager: TokenSyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DisplayUiState())
    val uiState: StateFlow<DisplayUiState> = _uiState.asStateFlow()

    init {
        startDisplayMode()
    }

    private fun startDisplayMode() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val clinicId = settings.clinicId
            val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

            // Start syncing Firestore → Room for this queue day
            tokenSyncManager.startSync(clinicId, queueDay.id, viewModelScope)
            _uiState.update { it.copy(clinicName = clinicId, isSynced = true, isLoading = false) }

            // Observe all tokens for display
            tokenRepository.observeTokensByQueueDay(queueDay.id)
                .collect { tokens ->
                    val called = tokens
                        .filter { it.status == TokenStatus.CALLED || it.status == TokenStatus.RECALLED }
                        .sortedByDescending { it.calledAt }
                        .take(3)
                    val waiting = tokens
                        .filter { it.status == TokenStatus.WAITING }
                        .sortedBy { it.tokenNumber }
                        .take(8)
                    _uiState.update { it.copy(calledTokens = called, waitingTokens = waiting) }
                }
        }
    }
}
