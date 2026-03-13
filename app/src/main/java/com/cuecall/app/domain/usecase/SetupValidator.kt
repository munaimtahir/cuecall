package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.model.Clinic
import com.cuecall.app.domain.model.QueueDay
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.ClinicRepository
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

data class ClinicSetupContext(
    val settings: AppSettings,
    val clinic: Clinic
)

data class TokenGenerationContext(
    val settings: AppSettings,
    val clinic: Clinic,
    val service: Service,
    val queueDay: QueueDay
)

@Singleton
class SetupValidator @Inject constructor(
    private val clinicRepository: ClinicRepository,
    private val serviceRepository: ServiceRepository,
    private val queueDayRepository: QueueDayRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend fun requireClinicSetup(): ClinicSetupContext {
        val settings = settingsRepository.getSettings()
        val clinic = resolveClinic(settings) ?: error("No clinic configured")
        require(clinic.name.isNotBlank()) { "Clinic name is missing" }

        if (settings.clinicId != clinic.id) {
            settingsRepository.saveSettings(settings.copy(clinicId = clinic.id))
        }

        return ClinicSetupContext(
            settings = settings.copy(clinicId = clinic.id),
            clinic = clinic
        )
    }

    suspend fun validateTokenGeneration(
        selectedServiceId: String,
        autoCreateQueueDay: Boolean = true
    ): TokenGenerationContext {
        val setup = requireClinicSetup()
        val services = serviceRepository.getAllServices(setup.clinic.id)
        require(services.isNotEmpty()) { "No service configured" }

        val service = serviceRepository.getService(selectedServiceId)
            ?: error("Selected service does not exist")
        require(service.clinicId == setup.clinic.id) { "Selected service does not belong to the configured clinic" }
        require(service.isActive) { "Selected service inactive" }

        val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val queueDay = if (autoCreateQueueDay) {
            queueDayRepository.getOrCreateQueueDay(setup.clinic.id, businessDate)
        } else {
            queueDayRepository.getQueueDay("${setup.clinic.id}_$businessDate")
                ?: error("No queue day initialized")
        }

        return TokenGenerationContext(
            settings = setup.settings,
            clinic = setup.clinic,
            service = service,
            queueDay = queueDay
        )
    }

    private suspend fun resolveClinic(settings: AppSettings): Clinic? {
        val bySettings = settings.clinicId
            .takeIf { it.isNotBlank() }
            ?.let { clinicId -> clinicRepository.getClinic(clinicId) }
        return bySettings ?: clinicRepository.getAnyClinic()
    }
}
