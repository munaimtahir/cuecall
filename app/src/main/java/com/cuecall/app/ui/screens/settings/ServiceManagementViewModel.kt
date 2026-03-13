package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.usecase.SetupValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ServiceManagementUiState(
    val services: List<Service> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class ServiceManagementViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val setupValidator: SetupValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceManagementUiState())
    val uiState: StateFlow<ServiceManagementUiState> = _uiState.asStateFlow()

    private var clinicId = ""

    init {
        viewModelScope.launch {
            clinicId = runCatching { setupValidator.requireClinicSetup().clinic.id }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message ?: "No clinic configured") }
                }
                .getOrNull()
                ?: return@launch
            serviceRepository.observeActiveServices(clinicId)
                .collect { services ->
                    _uiState.update { it.copy(services = services, isLoading = false) }
                }
        }
    }

    fun addService(name: String, prefix: String, code: String) {
        viewModelScope.launch {
            if (clinicId.isBlank()) {
                _uiState.update { it.copy(error = "No clinic configured") }
                return@launch
            }
            val service = Service(
                id = UUID.randomUUID().toString(),
                clinicId = clinicId,
                name = name,
                code = code.uppercase(),
                tokenPrefix = prefix.uppercase().take(3),
                sortOrder = _uiState.value.services.size + 1
            )
            serviceRepository.saveService(service)
        }
    }

    fun toggleActive(service: Service) {
        viewModelScope.launch {
            serviceRepository.setServiceActive(service.id, !service.isActive)
        }
    }

    fun deleteService(serviceId: String) {
        viewModelScope.launch {
            serviceRepository.deleteService(serviceId)
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
