package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
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
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceManagementUiState())
    val uiState: StateFlow<ServiceManagementUiState> = _uiState.asStateFlow()

    private var clinicId = ""

    init {
        viewModelScope.launch {
            clinicId = settingsRepository.getSettings().clinicId
            serviceRepository.observeActiveServices(clinicId)
                .collect { services ->
                    _uiState.update { it.copy(services = services, isLoading = false) }
                }
        }
    }

    fun addService(name: String, prefix: String, code: String) {
        viewModelScope.launch {
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
