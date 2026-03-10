package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Counter
import com.cuecall.app.domain.repository.CounterRepository
import com.cuecall.app.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CounterManagementUiState(
    val counters: List<Counter> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class CounterManagementViewModel @Inject constructor(
    private val counterRepository: CounterRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CounterManagementUiState())
    val uiState: StateFlow<CounterManagementUiState> = _uiState.asStateFlow()

    private var clinicId = ""

    init {
        viewModelScope.launch {
            clinicId = settingsRepository.getSettings().clinicId
            counterRepository.observeActiveCounters(clinicId)
                .collect { counters ->
                    _uiState.update { it.copy(counters = counters, isLoading = false) }
                }
        }
    }

    fun addCounter(name: String) {
        viewModelScope.launch {
            val counter = Counter(
                id = UUID.randomUUID().toString(),
                clinicId = clinicId,
                name = name.trim()
            )
            counterRepository.saveCounter(counter)
        }
    }

    fun deleteCounter(counterId: String) {
        viewModelScope.launch {
            counterRepository.deleteCounter(counterId)
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
