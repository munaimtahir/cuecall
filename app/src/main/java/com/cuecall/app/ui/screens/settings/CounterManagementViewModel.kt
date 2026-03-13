package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Counter
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.CounterRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.usecase.SetupValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CounterManagementUiState(
    val counters: List<Counter> = emptyList(),
    val services: List<Service> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class CounterManagementViewModel @Inject constructor(
    private val counterRepository: CounterRepository,
    private val serviceRepository: ServiceRepository,
    private val setupValidator: SetupValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(CounterManagementUiState())
    val uiState: StateFlow<CounterManagementUiState> = _uiState.asStateFlow()

    private var clinicId = ""

    init {
        viewModelScope.launch {
            clinicId = runCatching { setupValidator.requireClinicSetup().clinic.id }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message ?: "No clinic configured") }
                }
                .getOrNull()
                ?: return@launch

            launch {
                serviceRepository.observeActiveServices(clinicId)
                    .collect { services ->
                        _uiState.update { it.copy(services = services, isLoading = false) }
                    }
            }
            launch {
                counterRepository.observeActiveCounters(clinicId)
                    .collect { counters ->
                        _uiState.update { it.copy(counters = counters, isLoading = false) }
                    }
            }
        }
    }

    fun saveCounter(counterId: String?, name: String, serviceId: String?) {
        viewModelScope.launch {
            if (clinicId.isBlank()) {
                _uiState.update { it.copy(error = "No clinic configured") }
                return@launch
            }
            val counter = Counter(
                id = counterId ?: UUID.randomUUID().toString(),
                clinicId = clinicId,
                name = name.trim(),
                serviceId = serviceId
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
