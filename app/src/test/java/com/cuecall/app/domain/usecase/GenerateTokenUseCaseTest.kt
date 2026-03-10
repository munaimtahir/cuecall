package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.*
import com.cuecall.app.domain.repository.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GenerateTokenUseCaseTest {

    @MockK lateinit var tokenRepository: TokenRepository
    @MockK lateinit var tokenSequenceRepository: TokenSequenceRepository
    @MockK lateinit var queueDayRepository: QueueDayRepository
    @MockK lateinit var serviceRepository: ServiceRepository
    @MockK lateinit var settingsRepository: SettingsRepository
    @MockK lateinit var callEventRepository: CallEventRepository

    private lateinit var useCase: GenerateTokenUseCase

    private val testSettings = AppSettings(
        clinicId = "clinic1",
        deviceId = "device1",
        dailyResetEnabled = true
    )

    private val testService = Service(
        id = "svc1",
        clinicId = "clinic1",
        name = "General OPD",
        code = "GEN",
        tokenPrefix = "G"
    )

    private val testQueueDay = QueueDay(
        id = "clinic1_2024-01-15",
        clinicId = "clinic1",
        businessDate = "2024-01-15"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GenerateTokenUseCase(
            tokenRepository, tokenSequenceRepository, queueDayRepository,
            serviceRepository, settingsRepository, callEventRepository
        )
    }

    @Test
    fun `generates token with correct display number format`() = runTest {
        coEvery { settingsRepository.getSettings() } returns testSettings
        coEvery { serviceRepository.getService("svc1") } returns testService
        coEvery { queueDayRepository.getOrCreateQueueDay("clinic1", any()) } returns testQueueDay
        coEvery { tokenSequenceRepository.getNextTokenNumber("clinic1", testQueueDay.id, "svc1") } returns 5
        coEvery { tokenRepository.saveToken(any()) } just Runs
        coEvery { callEventRepository.logEvent(any()) } just Runs

        val result = useCase("svc1")

        assertTrue(result.isSuccess)
        val token = result.getOrThrow()
        assertEquals("G-005", token.displayNumber)
        assertEquals(5, token.tokenNumber)
        assertEquals("G", token.tokenPrefix)
        assertEquals(TokenStatus.WAITING, token.status)
    }

    @Test
    fun `generates sequential tokens with incrementing numbers`() = runTest {
        coEvery { settingsRepository.getSettings() } returns testSettings
        coEvery { serviceRepository.getService("svc1") } returns testService
        coEvery { queueDayRepository.getOrCreateQueueDay(any(), any()) } returns testQueueDay
        coEvery { tokenRepository.saveToken(any()) } just Runs
        coEvery { callEventRepository.logEvent(any()) } just Runs

        // Simulate sequence returning 1, 2, 3
        coEvery {
            tokenSequenceRepository.getNextTokenNumber("clinic1", testQueueDay.id, "svc1")
        } returnsMany listOf(1, 2, 3)

        val tokens = (1..3).map { useCase("svc1").getOrThrow() }

        assertEquals("G-001", tokens[0].displayNumber)
        assertEquals("G-002", tokens[1].displayNumber)
        assertEquals("G-003", tokens[2].displayNumber)
    }

    @Test
    fun `fails when clinic not configured`() = runTest {
        coEvery { settingsRepository.getSettings() } returns testSettings.copy(clinicId = "")

        val result = useCase("svc1")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Clinic not configured") == true)
    }

    @Test
    fun `fails when service not found`() = runTest {
        coEvery { settingsRepository.getSettings() } returns testSettings
        coEvery { queueDayRepository.getOrCreateQueueDay(any(), any()) } returns testQueueDay
        coEvery { serviceRepository.getService("unknown") } returns null

        val result = useCase("unknown")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Service not found") == true)
    }

    @Test
    fun `token prefix is blank produces plain padded number`() = runTest {
        val serviceNoPrefix = testService.copy(tokenPrefix = "")
        coEvery { settingsRepository.getSettings() } returns testSettings
        coEvery { serviceRepository.getService("svc_noprefix") } returns serviceNoPrefix
        coEvery { queueDayRepository.getOrCreateQueueDay(any(), any()) } returns testQueueDay
        coEvery { tokenSequenceRepository.getNextTokenNumber(any(), any(), any()) } returns 12
        coEvery { tokenRepository.saveToken(any()) } just Runs
        coEvery { callEventRepository.logEvent(any()) } just Runs

        val result = useCase("svc_noprefix")

        assertTrue(result.isSuccess)
        assertEquals("012", result.getOrThrow().displayNumber)
    }
}
