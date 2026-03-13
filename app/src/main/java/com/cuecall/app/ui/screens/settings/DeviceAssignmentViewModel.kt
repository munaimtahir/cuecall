package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Counter
import com.cuecall.app.domain.model.DeviceMode
import com.cuecall.app.domain.repository.CounterRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.usecase.SetupValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class DeviceAssignmentUiState(
    val isLoading: Boolean = true,
    val deviceMode: DeviceMode = DeviceMode.UNASSIGNED,
    val assignedCounterId: String? = null,
    val counters: List<Counter> = emptyList(),
    val deviceId: String = "",
    val currentServiceName: String? = null,
    val error: String? = null,
    val message: String? = null
)

@HiltViewModel
class DeviceAssignmentViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val counterRepository: CounterRepository,
    private val serviceRepository: ServiceRepository,
    private val setupValidator: SetupValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceAssignmentUiState())
    val uiState: StateFlow<DeviceAssignmentUiState> = _uiState.asStateFlow()

    private var clinicId: String = ""

    init {
        viewModelScope.launch {
            val setup = runCatching { setupValidator.requireClinicSetup() }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message ?: "No clinic configured") }
                }
                .getOrNull()
                ?: return@launch

            clinicId = setup.clinic.id
            val settings = setup.settings
            val counters = counterRepository.getAllCounters(clinicId).filter { it.isActive }
            val assignedCounter = settings.assignedCounterId?.let { assignedId ->
                counters.firstOrNull { it.id == assignedId }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    deviceMode = settings.deviceMode,
                    assignedCounterId = settings.assignedCounterId,
                    counters = counters,
                    deviceId = settings.deviceId.ifBlank { UUID.randomUUID().toString() }
                )
            }
            ensureDeviceId()
            refreshDerivedServiceName(counters, settings.assignedCounterId)
        }
    }

    fun setDeviceMode(mode: DeviceMode) {
        _uiState.update {
            it.copy(
                deviceMode = mode,
                assignedCounterId = if (mode == DeviceMode.COUNTER) it.assignedCounterId else null,
                currentServiceName = if (mode == DeviceMode.COUNTER) it.currentServiceName else null
            )
        }
    }

    fun setAssignedCounter(counterId: String?) {
        _uiState.update { it.copy(assignedCounterId = counterId) }
        refreshDerivedServiceName(_uiState.value.counters, counterId)
    }

    fun saveAssignment() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.deviceMode == DeviceMode.COUNTER && state.assignedCounterId == null) {
                _uiState.update { it.copy(error = "Select a counter for Counter mode") }
                return@launch
            }

            val selectedCounter = state.assignedCounterId?.let { counterId ->
                state.counters.firstOrNull { it.id == counterId }
            }
            if (state.deviceMode == DeviceMode.COUNTER && selectedCounter?.serviceId == null) {
                _uiState.update { it.copy(error = "Selected counter has no linked service") }
                return@launch
            }
            val settings = settingsRepository.getSettings()
            val nextSettings = settings.copy(
                clinicId = clinicId.ifBlank { settings.clinicId },
                deviceId = state.deviceId,
                deviceMode = state.deviceMode,
                assignedCounterId = if (state.deviceMode == DeviceMode.COUNTER) state.assignedCounterId else null,
                assignedServiceId = if (state.deviceMode == DeviceMode.COUNTER) selectedCounter?.serviceId else null
            )
            settingsRepository.saveSettings(nextSettings)
            _uiState.update {
                it.copy(
                    message = when (state.deviceMode) {
                        DeviceMode.COUNTER -> "Device assigned to counter"
                        DeviceMode.RECEPTION -> "Device assigned to reception"
                        DeviceMode.DISPLAY -> "Device assigned to display"
                        DeviceMode.UNASSIGNED -> "Device assignment cleared"
                    }
                )
            }
        }
    }

    private fun ensureDeviceId() {
        viewModelScope.launch {
            val state = _uiState.value
            val settings = settingsRepository.getSettings()
            if (settings.deviceId.isBlank()) {
                settingsRepository.saveSettings(settings.copy(deviceId = state.deviceId))
            }
        }
    }

    private fun refreshDerivedServiceName(counters: List<Counter>, counterId: String?) {
        viewModelScope.launch {
            val selectedCounter = counterId?.let { selectedId ->
                counters.firstOrNull { it.id == selectedId }
            }
            val serviceName = selectedCounter?.serviceId
                ?.let { serviceId -> serviceRepository.getService(serviceId)?.name }
            _uiState.update { it.copy(currentServiceName = serviceName) }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
    fun clearMessage() = _uiState.update { it.copy(message = null) }
}
