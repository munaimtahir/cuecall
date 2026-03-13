package com.cuecall.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuecall.app.domain.model.Clinic
import com.cuecall.app.domain.repository.ClinicRepository
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

data class ClinicSetupUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val clinicName: String = "",
    val address: String = "",
    val phone: String = "",
    val error: String? = null,
    val message: String? = null
)

@HiltViewModel
class ClinicSetupViewModel @Inject constructor(
    private val clinicRepository: ClinicRepository,
    private val serviceRepository: ServiceRepository,
    private val counterRepository: CounterRepository,
    private val settingsRepository: SettingsRepository,
    private val setupValidator: SetupValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClinicSetupUiState())
    val uiState: StateFlow<ClinicSetupUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val clinic = runCatching { setupValidator.requireClinicSetup().clinic }
                .getOrNull()
                ?: clinicRepository.getAnyClinic()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    clinicName = clinic?.name.orEmpty(),
                    address = clinic?.address.orEmpty(),
                    phone = clinic?.phone.orEmpty()
                )
            }
        }
    }

    fun saveClinic(clinicName: String, address: String, phone: String) {
        viewModelScope.launch {
            val trimmedName = clinicName.trim()
            if (trimmedName.isBlank()) {
                _uiState.update { it.copy(error = "Clinic name is required") }
                return@launch
            }

            _uiState.update { it.copy(isSaving = true, error = null) }
            val settings = settingsRepository.getSettings()
            val existingClinic = clinicRepository.getAnyClinic()
            val clinicId = settings.clinicId.ifBlank { existingClinic?.id ?: UUID.randomUUID().toString() }
            val now = System.currentTimeMillis()
            val clinic = Clinic(
                id = clinicId,
                name = trimmedName,
                address = address.trim(),
                phone = phone.trim(),
                createdAt = existingClinic?.createdAt ?: now,
                updatedAt = now
            )
            clinicRepository.saveClinic(clinic)
            serviceRepository.assignBlankClinicId(clinicId)
            counterRepository.assignBlankClinicId(clinicId)
            settingsRepository.saveSettings(settings.copy(clinicId = clinicId))
            _uiState.update {
                it.copy(
                    isSaving = false,
                    clinicName = clinic.name,
                    address = clinic.address,
                    phone = clinic.phone,
                    message = "Clinic setup saved"
                )
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
    fun clearMessage() = _uiState.update { it.copy(message = null) }
}
