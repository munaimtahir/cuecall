package com.cuecall.app.domain.usecase

import com.cuecall.app.domain.model.*
import com.cuecall.app.domain.repository.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

/**
 * Generates a new token for a service.
 *
 * This is the most critical use case — it uses a Firestore transaction to guarantee
 * unique token numbering even under concurrent reception devices.
 *
 * IMPORTANT: This operation REQUIRES network connectivity.
 * Throws [IllegalStateException] if offline.
 */
class GenerateTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val tokenSequenceRepository: TokenSequenceRepository,
    private val queueDayRepository: QueueDayRepository,
    private val serviceRepository: ServiceRepository,
    private val settingsRepository: SettingsRepository,
    private val callEventRepository: CallEventRepository
) {
    suspend operator fun invoke(serviceId: String): Result<Token> = runCatching {
        val settings = settingsRepository.getSettings()
        val clinicId = settings.clinicId.ifBlank { error("Clinic not configured. Please complete setup.") }
        val deviceId = settings.deviceId

        val service = serviceRepository.getService(serviceId)
            ?: error("Service not found: $serviceId")

        val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

        val tokenNumber = tokenSequenceRepository.getNextTokenNumber(clinicId, queueDay.id, serviceId)
        val displayNumber = formatDisplayNumber(service.tokenPrefix, tokenNumber)

        val token = Token(
            id = UUID.randomUUID().toString(),
            clinicId = clinicId,
            queueDayId = queueDay.id,
            serviceId = serviceId,
            tokenPrefix = service.tokenPrefix,
            tokenNumber = tokenNumber,
            displayNumber = displayNumber,
            status = TokenStatus.WAITING,
            issuedAt = System.currentTimeMillis(),
            createdByDeviceId = deviceId
        )

        tokenRepository.saveToken(token)
        callEventRepository.logEvent(
            CallEvent(
                id = UUID.randomUUID().toString(),
                clinicId = clinicId,
                tokenId = token.id,
                actionType = ActionType.ISSUED,
                serviceId = serviceId,
                createdAt = System.currentTimeMillis(),
                createdByDeviceId = deviceId,
                metadata = mapOf("queueDayId" to queueDay.id)
            )
        )
        token
    }

    private fun formatDisplayNumber(prefix: String, number: Int): String {
        val padded = number.toString().padStart(3, '0')
        return if (prefix.isBlank()) padded else "$prefix-$padded"
    }
}

/**
 * Calls the next waiting token at a counter/service.
 * Sets any currently called token to CALLED (replaced by the new call).
 */
class CallNextTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val settingsRepository: SettingsRepository,
    private val callEventRepository: CallEventRepository,
    private val queueDayRepository: QueueDayRepository
) {
    suspend operator fun invoke(serviceId: String): Result<Token?> = runCatching {
        val settings = settingsRepository.getSettings()
        val clinicId = settings.clinicId.ifBlank { error("Clinic not configured.") }
        val deviceId = settings.deviceId

        val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val queueDay = queueDayRepository.getOrCreateQueueDay(clinicId, businessDate)

        // Take the first waiting token (lowest tokenNumber)
        val nextToken = tokenRepository.getTokensForDay(queueDay.id)
            .filter { it.serviceId == serviceId && it.status == TokenStatus.WAITING }
            .minByOrNull { it.tokenNumber }
            ?: return@runCatching null

        val now = System.currentTimeMillis()
        tokenRepository.updateTokenStatus(nextToken.id, TokenStatus.CALLED, now, deviceId)
        callEventRepository.logEvent(
            CallEvent(
                id = UUID.randomUUID().toString(),
                clinicId = clinicId,
                tokenId = nextToken.id,
                actionType = ActionType.CALLED,
                serviceId = serviceId,
                counterId = settings.assignedCounterId,
                createdAt = now,
                createdByDeviceId = deviceId,
                metadata = mapOf("queueDayId" to queueDay.id)
            )
        )
        nextToken.copy(status = TokenStatus.CALLED, calledAt = now)
    }
}

/** Recalls the currently called/active token (patient didn't respond first time). */
class RecallTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val settingsRepository: SettingsRepository,
    private val callEventRepository: CallEventRepository
) {
    suspend operator fun invoke(tokenId: String, queueDayId: String): Result<Unit> = runCatching {
        val settings = settingsRepository.getSettings()
        val token = tokenRepository.getToken(tokenId) ?: error("Token not found: $tokenId")
        check(token.status == TokenStatus.CALLED || token.status == TokenStatus.RECALLED) {
            "Can only recall a CALLED or RECALLED token, current status: ${token.status}"
        }
        val now = System.currentTimeMillis()
        tokenRepository.updateTokenStatus(tokenId, TokenStatus.RECALLED, now, settings.deviceId)
        callEventRepository.logEvent(
            CallEvent(
                id = java.util.UUID.randomUUID().toString(),
                clinicId = settings.clinicId,
                tokenId = tokenId,
                actionType = ActionType.RECALLED,
                serviceId = token.serviceId,
                counterId = settings.assignedCounterId,
                createdAt = now,
                createdByDeviceId = settings.deviceId,
                metadata = mapOf("queueDayId" to queueDayId)
            )
        )
    }
}

/** Skips a token (patient absent or refused). */
class SkipTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val settingsRepository: SettingsRepository,
    private val callEventRepository: CallEventRepository
) {
    suspend operator fun invoke(tokenId: String, queueDayId: String): Result<Unit> = runCatching {
        val settings = settingsRepository.getSettings()
        val token = tokenRepository.getToken(tokenId) ?: error("Token not found: $tokenId")
        check(token.status in listOf(TokenStatus.WAITING, TokenStatus.CALLED, TokenStatus.RECALLED)) {
            "Cannot skip token in status: ${token.status}"
        }
        val now = System.currentTimeMillis()
        tokenRepository.updateTokenStatus(tokenId, TokenStatus.SKIPPED, now, settings.deviceId)
        callEventRepository.logEvent(
            CallEvent(
                id = java.util.UUID.randomUUID().toString(),
                clinicId = settings.clinicId,
                tokenId = tokenId,
                actionType = ActionType.SKIPPED,
                serviceId = token.serviceId,
                counterId = settings.assignedCounterId,
                createdAt = now,
                createdByDeviceId = settings.deviceId,
                metadata = mapOf("queueDayId" to queueDayId)
            )
        )
    }
}

/** Marks a token as completed (patient seen). */
class CompleteTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val settingsRepository: SettingsRepository,
    private val callEventRepository: CallEventRepository
) {
    suspend operator fun invoke(tokenId: String, queueDayId: String): Result<Unit> = runCatching {
        val settings = settingsRepository.getSettings()
        val token = tokenRepository.getToken(tokenId) ?: error("Token not found: $tokenId")
        check(token.status in listOf(TokenStatus.CALLED, TokenStatus.RECALLED)) {
            "Can only complete a CALLED or RECALLED token, current status: ${token.status}"
        }
        val now = System.currentTimeMillis()
        tokenRepository.updateTokenStatus(tokenId, TokenStatus.COMPLETED, now, settings.deviceId)
        callEventRepository.logEvent(
            CallEvent(
                id = java.util.UUID.randomUUID().toString(),
                clinicId = settings.clinicId,
                tokenId = tokenId,
                actionType = ActionType.COMPLETED,
                serviceId = token.serviceId,
                counterId = settings.assignedCounterId,
                createdAt = now,
                createdByDeviceId = settings.deviceId,
                metadata = mapOf("queueDayId" to queueDayId)
            )
        )
    }
}

/** Resets the queue for a new day (or manual reset). */
class ResetQueueDayUseCase @Inject constructor(
    private val queueDayRepository: QueueDayRepository,
    private val tokenSequenceRepository: TokenSequenceRepository,
    private val serviceRepository: ServiceRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Result<QueueDay> = runCatching {
        val settings = settingsRepository.getSettings()
        val clinicId = settings.clinicId.ifBlank { error("Clinic not configured.") }
        val businessDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        // Create new queue day with a timestamp suffix to force a new day even if same calendar date
        val newQueueDayId = "${clinicId}_${businessDate}_${System.currentTimeMillis()}"
        val newQueueDay = com.cuecall.app.domain.model.QueueDay(
            id = newQueueDayId,
            clinicId = clinicId,
            businessDate = businessDate
        )
        queueDayRepository.saveQueueDay(newQueueDay)

        // Reset sequences for all active services
        val services = serviceRepository.getAllServices(clinicId).filter { it.isActive }
        services.forEach { service ->
            tokenSequenceRepository.resetSequence(clinicId, newQueueDayId, service.id)
        }

        newQueueDay
    }
}

/** Initializes clinic setup on first launch. Seeds sample services. */
class SetupClinicUseCase @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(
        clinicId: String,
        clinicName: String,
        deviceId: String
    ): Result<Unit> = runCatching {
        val settings = settingsRepository.getSettings().copy(
            clinicId = clinicId,
            deviceId = deviceId
        )
        settingsRepository.saveSettings(settings)

        // Seed 3 demo services if no services exist
        val existing = serviceRepository.getAllServices(clinicId)
        if (existing.isEmpty()) {
            listOf(
                Service(
                    id = "svc_general_${clinicId}",
                    clinicId = clinicId,
                    name = "General OPD",
                    code = "GEN",
                    tokenPrefix = "G",
                    sortOrder = 1
                ),
                Service(
                    id = "svc_ultrasound_${clinicId}",
                    clinicId = clinicId,
                    name = "Ultrasound",
                    code = "USG",
                    tokenPrefix = "U",
                    sortOrder = 2
                ),
                Service(
                    id = "svc_lab_${clinicId}",
                    clinicId = clinicId,
                    name = "Laboratory",
                    code = "LAB",
                    tokenPrefix = "L",
                    sortOrder = 3
                )
            ).forEach { serviceRepository.saveService(it) }
        }
    }
}
