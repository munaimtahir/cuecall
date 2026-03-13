package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.ActionType
import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.model.CallEvent
import com.cuecall.app.domain.model.Clinic
import com.cuecall.app.domain.model.QueueDay
import com.cuecall.app.domain.model.Service
import com.cuecall.app.domain.model.TokenStatus
import com.cuecall.app.domain.repository.CallEventRepository
import com.cuecall.app.domain.repository.TokenRepository
import com.cuecall.app.domain.repository.TokenSequenceRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GenerateTokenUseCaseTest {

    @MockK lateinit var tokenRepository: TokenRepository
    @MockK lateinit var tokenSequenceRepository: TokenSequenceRepository
    @MockK lateinit var callEventRepository: CallEventRepository
    @MockK lateinit var setupValidator: SetupValidator

    private lateinit var useCase: GenerateTokenUseCase

    private val context = TokenGenerationContext(
        settings = AppSettings(clinicId = "clinic1", deviceId = "device1"),
        clinic = Clinic(id = "clinic1", name = "Demo Clinic"),
        service = Service(
            id = "svc1",
            clinicId = "clinic1",
            name = "General OPD",
            code = "GEN",
            tokenPrefix = "G"
        ),
        queueDay = QueueDay(
            id = "clinic1_2026-03-11",
            clinicId = "clinic1",
            businessDate = "2026-03-11"
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GenerateTokenUseCase(
            tokenRepository = tokenRepository,
            tokenSequenceRepository = tokenSequenceRepository,
            callEventRepository = callEventRepository,
            setupValidator = setupValidator
        )
    }

    @Test
    fun `generates token with correct display number format`() = runTest {
        coEvery { setupValidator.validateTokenGeneration("svc1") } returns context
        coEvery { tokenSequenceRepository.getNextTokenNumber("clinic1", context.queueDay.id, "svc1") } returns 5
        coEvery { tokenRepository.saveToken(any()) } returns Unit
        coEvery { callEventRepository.logEvent(any()) } returns Unit

        val result = useCase("svc1")

        assertTrue(result.isSuccess)
        val token = result.getOrThrow()
        assertEquals("G-005", token.displayNumber)
        assertEquals(5, token.tokenNumber)
        assertEquals(TokenStatus.WAITING, token.status)
        coVerify {
            callEventRepository.logEvent(withArg<CallEvent> {
                assertEquals(ActionType.ISSUED, it.actionType)
                assertEquals("svc1", it.serviceId)
            })
        }
    }

    @Test
    fun `returns validator failure message when setup is incomplete`() = runTest {
        coEvery { setupValidator.validateTokenGeneration("svc1") } throws IllegalStateException("No clinic configured")

        val result = useCase("svc1")

        assertTrue(result.isFailure)
        assertEquals("No clinic configured", result.exceptionOrNull()?.message)
    }

    @Test
    fun `token prefix is blank produces plain padded number`() = runTest {
        coEvery { setupValidator.validateTokenGeneration("svc2") } returns context.copy(
            service = context.service.copy(id = "svc2", tokenPrefix = "")
        )
        coEvery { tokenSequenceRepository.getNextTokenNumber("clinic1", context.queueDay.id, "svc2") } returns 12
        coEvery { tokenRepository.saveToken(any()) } returns Unit
        coEvery { callEventRepository.logEvent(any()) } returns Unit

        val result = useCase("svc2")

        assertTrue(result.isSuccess)
        assertEquals("012", result.getOrThrow().displayNumber)
    }
}
