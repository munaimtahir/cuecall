package com.cuecall.app.ui.screens.settings

import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.model.Clinic
import com.cuecall.app.domain.model.Counter
import com.cuecall.app.domain.model.DeviceMode
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.repository.CounterRepository
import com.cuecall.app.domain.repository.ServiceRepository
import com.cuecall.app.domain.repository.SettingsRepository
import com.cuecall.app.domain.usecase.ClinicSetupContext
import com.cuecall.app.domain.usecase.SetupValidator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeviceAssignmentViewModelTest {

    @MockK lateinit var settingsRepository: SettingsRepository
    @MockK lateinit var counterRepository: CounterRepository
    @MockK lateinit var serviceRepository: ServiceRepository
    @MockK lateinit var setupValidator: SetupValidator

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `saving counter mode persists assigned counter and linked service`() = runTest(dispatcher) {
        val settings = AppSettings(clinicId = "clinic1", deviceId = "device-1")
        val counter = Counter(id = "counter1", clinicId = "clinic1", name = "Window 1", serviceId = "svc1")
        val service = Service(id = "svc1", clinicId = "clinic1", name = "General", code = "GEN", tokenPrefix = "G")
        coEvery { setupValidator.requireClinicSetup() } returns ClinicSetupContext(settings, Clinic("clinic1", "Clinic"))
        coEvery { counterRepository.getAllCounters("clinic1") } returns listOf(counter)
        coEvery { settingsRepository.getSettings() } returns settings
        coEvery { serviceRepository.getService("svc1") } returns service
        coEvery { settingsRepository.saveSettings(any()) } returns Unit

        val viewModel = DeviceAssignmentViewModel(
            settingsRepository = settingsRepository,
            counterRepository = counterRepository,
            serviceRepository = serviceRepository,
            setupValidator = setupValidator
        )
        advanceUntilIdle()

        viewModel.setDeviceMode(DeviceMode.COUNTER)
        viewModel.setAssignedCounter("counter1")
        advanceUntilIdle()
        viewModel.saveAssignment()
        advanceUntilIdle()

        coVerify {
            settingsRepository.saveSettings(match {
                it.deviceMode == DeviceMode.COUNTER &&
                    it.assignedCounterId == "counter1" &&
                    it.assignedServiceId == "svc1"
            })
        }
    }

    @Test
    fun `saving counter mode with unlinked counter shows error`() = runTest(dispatcher) {
        val settings = AppSettings(clinicId = "clinic1", deviceId = "device-1")
        val counter = Counter(id = "counter1", clinicId = "clinic1", name = "Window 1", serviceId = null)
        coEvery { setupValidator.requireClinicSetup() } returns ClinicSetupContext(settings, Clinic("clinic1", "Clinic"))
        coEvery { counterRepository.getAllCounters("clinic1") } returns listOf(counter)
        coEvery { settingsRepository.getSettings() } returns settings
        coEvery { settingsRepository.saveSettings(any()) } returns Unit

        val viewModel = DeviceAssignmentViewModel(
            settingsRepository = settingsRepository,
            counterRepository = counterRepository,
            serviceRepository = serviceRepository,
            setupValidator = setupValidator
        )
        advanceUntilIdle()

        viewModel.setDeviceMode(DeviceMode.COUNTER)
        viewModel.setAssignedCounter("counter1")
        advanceUntilIdle()
        viewModel.saveAssignment()
        advanceUntilIdle()

        assertEquals("Selected counter has no linked service", viewModel.uiState.value.error)
        assertTrue(viewModel.uiState.value.assignedCounterId == "counter1")
    }
}
