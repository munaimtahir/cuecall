package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.*
import com.cuecall.app.domain.repository.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests the queue state machine:
 * Token transitions: WAITING → CALLED → COMPLETED
 *                                     → SKIPPED
 *                              → RECALLED → COMPLETED
 *                              → SKIPPED
 */
class QueueStateMachineTest {

    @MockK lateinit var tokenRepository: TokenRepository
    @MockK lateinit var settingsRepository: SettingsRepository
    @MockK lateinit var callEventRepository: CallEventRepository
    @MockK lateinit var queueDayRepository: QueueDayRepository

    private val testSettings = AppSettings(clinicId = "clinic1", deviceId = "device1")
    private val testQueueDay = QueueDay(
        id = "clinic1_2024-01-15", clinicId = "clinic1", businessDate = "2024-01-15"
    )

    private fun makeToken(status: TokenStatus) = Token(
        id = "token1", clinicId = "clinic1", queueDayId = testQueueDay.id,
        serviceId = "svc1", tokenPrefix = "G", tokenNumber = 1, displayNumber = "G-001",
        status = status
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { settingsRepository.getSettings() } returns testSettings
        coEvery { queueDayRepository.getOrCreateQueueDay(any(), any()) } returns testQueueDay
        coEvery { callEventRepository.logEvent(any()) } just Runs
        coEvery { tokenRepository.updateTokenStatus(any(), any(), any(), any()) } just Runs
    }

    // --- CallNext ---

    @Test
    fun `callNext returns the lowest-number waiting token`() = runTest {
        val tokens = listOf(
            makeToken(TokenStatus.WAITING).copy(id = "t3", tokenNumber = 3, displayNumber = "G-003"),
            makeToken(TokenStatus.WAITING).copy(id = "t1", tokenNumber = 1, displayNumber = "G-001"),
            makeToken(TokenStatus.WAITING).copy(id = "t2", tokenNumber = 2, displayNumber = "G-002"),
        )
        coEvery { tokenRepository.getTokensForDay(testQueueDay.id) } returns tokens

        val useCase = CallNextTokenUseCase(
            tokenRepository, settingsRepository, callEventRepository, queueDayRepository
        )
        val result = useCase("svc1")

        assertTrue(result.isSuccess)
        val calledToken = result.getOrThrow()
        assertNotNull(calledToken)
        assertEquals("t1", calledToken!!.id)
        coVerify {
            tokenRepository.updateTokenStatus("t1", TokenStatus.CALLED, any(), "device1")
        }
    }

    @Test
    fun `callNext returns null when no waiting tokens`() = runTest {
        coEvery { tokenRepository.getTokensForDay(testQueueDay.id) } returns emptyList()

        val useCase = CallNextTokenUseCase(
            tokenRepository, settingsRepository, callEventRepository, queueDayRepository
        )
        val result = useCase("svc1")

        assertTrue(result.isSuccess)
        assertNull(result.getOrThrow())
    }

    // --- RecallToken ---

    @Test
    fun `recall succeeds on CALLED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.CALLED)

        val useCase = RecallTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
        coVerify { tokenRepository.updateTokenStatus("token1", TokenStatus.RECALLED, any(), any()) }
    }

    @Test
    fun `recall succeeds on RECALLED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.RECALLED)

        val useCase = RecallTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `recall fails on WAITING token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.WAITING)

        val useCase = RecallTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("WAITING") == true)
    }

    // --- SkipToken ---

    @Test
    fun `skip succeeds on WAITING token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.WAITING)

        val useCase = SkipTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
        coVerify { tokenRepository.updateTokenStatus("token1", TokenStatus.SKIPPED, any(), any()) }
    }

    @Test
    fun `skip succeeds on CALLED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.CALLED)

        val useCase = SkipTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `skip fails on COMPLETED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.COMPLETED)

        val useCase = SkipTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isFailure)
    }

    // --- CompleteToken ---

    @Test
    fun `complete succeeds on CALLED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.CALLED)

        val useCase = CompleteTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
        coVerify { tokenRepository.updateTokenStatus("token1", TokenStatus.COMPLETED, any(), any()) }
    }

    @Test
    fun `complete succeeds on RECALLED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.RECALLED)

        val useCase = CompleteTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `complete fails on WAITING token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.WAITING)

        val useCase = CompleteTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("WAITING") == true)
    }

    @Test
    fun `complete fails on SKIPPED token`() = runTest {
        coEvery { tokenRepository.getToken("token1") } returns makeToken(TokenStatus.SKIPPED)

        val useCase = CompleteTokenUseCase(tokenRepository, settingsRepository, callEventRepository)
        val result = useCase("token1", testQueueDay.id)

        assertTrue(result.isFailure)
    }
}
