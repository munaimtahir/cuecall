package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.model.Clinic
import com.cuecall.app.domain.model.QueueDay
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.ClinicRepository
import com.cuecall.app.domain.repository.QueueDayRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SetupValidatorTest {

    @MockK lateinit var clinicRepository: ClinicRepository
    @MockK lateinit var serviceRepository: ServiceRepository
    @MockK lateinit var queueDayRepository: QueueDayRepository
    @MockK lateinit var settingsRepository: SettingsRepository

    private lateinit var validator: SetupValidator

    private val clinic = Clinic(id = "clinic1", name = "Demo Clinic")
    private val settings = AppSettings(clinicId = "clinic1", deviceId = "device1")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        validator = SetupValidator(clinicRepository, serviceRepository, queueDayRepository, settingsRepository)
    }

    @Test
    fun `fails when no clinic exists`() = runTest {
        coEvery { settingsRepository.getSettings() } returns AppSettings(clinicId = "")
        coEvery { clinicRepository.getAnyClinic() } returns null

        val result = runCatching { validator.requireClinicSetup() }

        assertTrue(result.isFailure)
        assertEquals("No clinic configured", result.exceptionOrNull()?.message)
    }

    @Test
    fun `syncs settings clinic id from stored clinic record`() = runTest {
        coEvery { settingsRepository.getSettings() } returns AppSettings(clinicId = "")
        coEvery { clinicRepository.getAnyClinic() } returns clinic
        coEvery { settingsRepository.saveSettings(any()) } returns Unit

        val result = validator.requireClinicSetup()

        assertEquals("clinic1", result.clinic.id)
        coVerify { settingsRepository.saveSettings(match { it.clinicId == "clinic1" }) }
    }

    @Test
    fun `fails token generation when no active service exists`() = runTest {
        coEvery { settingsRepository.getSettings() } returns settings
        coEvery { clinicRepository.getClinic("clinic1") } returns clinic
        coEvery { serviceRepository.getAllServices("clinic1") } returns emptyList()

        val result = runCatching { validator.validateTokenGeneration("svc1") }

        assertTrue(result.isFailure)
        assertEquals("No service configured", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fails token generation when selected service is inactive`() = runTest {
        val inactiveService = Service(
            id = "svc1",
            clinicId = "clinic1",
            name = "General",
            code = "GEN",
            tokenPrefix = "G",
            isActive = false
        )
        coEvery { settingsRepository.getSettings() } returns settings
        coEvery { clinicRepository.getClinic("clinic1") } returns clinic
        coEvery { serviceRepository.getAllServices("clinic1") } returns listOf(inactiveService)
        coEvery { serviceRepository.getService("svc1") } returns inactiveService

        val result = runCatching { validator.validateTokenGeneration("svc1") }

        assertTrue(result.isFailure)
        assertEquals("Selected service inactive", result.exceptionOrNull()?.message)
    }

    @Test
    fun `auto creates queue day for today when setup is valid`() = runTest {
        val service = Service(
            id = "svc1",
            clinicId = "clinic1",
            name = "General",
            code = "GEN",
            tokenPrefix = "G"
        )
        val queueDay = QueueDay(id = "clinic1_2026-03-11", clinicId = "clinic1", businessDate = "2026-03-11")
        coEvery { settingsRepository.getSettings() } returns settings
        coEvery { clinicRepository.getClinic("clinic1") } returns clinic
        coEvery { serviceRepository.getAllServices("clinic1") } returns listOf(service)
        coEvery { serviceRepository.getService("svc1") } returns service
        coEvery { queueDayRepository.getOrCreateQueueDay("clinic1", any()) } returns queueDay

        val result = validator.validateTokenGeneration("svc1")

        assertEquals(queueDay.id, result.queueDay.id)
    }
}
