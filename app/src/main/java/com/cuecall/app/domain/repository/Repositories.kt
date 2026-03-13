package com.cuecall.app.domain.repository

import com.cuecall.app.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ClinicRepository {
    fun observeClinic(clinicId: String): Flow<Clinic?>
    suspend fun saveClinic(clinic: Clinic)
    suspend fun getClinic(clinicId: String): Clinic?
    suspend fun getAnyClinic(): Clinic?
}

interface ServiceRepository {
    fun observeActiveServices(clinicId: String): Flow<List<Service>>
    suspend fun getService(serviceId: String): Service?
    suspend fun getAllServices(clinicId: String): List<Service>
    suspend fun saveService(service: Service)
    suspend fun deleteService(serviceId: String)
    suspend fun setServiceActive(serviceId: String, isActive: Boolean)
    suspend fun assignBlankClinicId(clinicId: String)
}

interface CounterRepository {
    fun observeActiveCounters(clinicId: String): Flow<List<Counter>>
    suspend fun getCounter(counterId: String): Counter?
    suspend fun getAllCounters(clinicId: String): List<Counter>
    suspend fun saveCounter(counter: Counter)
    suspend fun deleteCounter(counterId: String)
    suspend fun assignBlankClinicId(clinicId: String)
}

interface QueueDayRepository {
    suspend fun getOrCreateQueueDay(clinicId: String, businessDate: String): QueueDay
    suspend fun getQueueDay(queueDayId: String): QueueDay?
    suspend fun saveQueueDay(queueDay: QueueDay)
    fun observeQueueDay(queueDayId: String): Flow<QueueDay?>
}

interface TokenRepository {
    fun observeTokensByQueueDay(queueDayId: String): Flow<List<Token>>
    fun observeWaitingTokens(queueDayId: String, serviceId: String? = null): Flow<List<Token>>
    fun observeCalledToken(queueDayId: String, serviceId: String): Flow<Token?>
    suspend fun getToken(tokenId: String): Token?
    suspend fun saveToken(token: Token)
    suspend fun updateTokenStatus(
        tokenId: String,
        status: TokenStatus,
        timestamp: Long = System.currentTimeMillis(),
        updatedByDeviceId: String = ""
    )
    suspend fun getTokensForDay(queueDayId: String): List<Token>
}

interface CallEventRepository {
    suspend fun logEvent(event: CallEvent)
    fun observeEventsForDay(queueDayId: String): Flow<List<CallEvent>>
    suspend fun getEventsForDay(queueDayId: String): List<CallEvent>
}

interface SettingsRepository {
    suspend fun getSettings(): AppSettings
    suspend fun saveSettings(settings: AppSettings)
    fun observeSettings(): Flow<AppSettings>
}

/**
 * Handles safe atomic token sequence generation via Firestore transaction.
 * Returns the next token number for a given service on the given queue day.
 */
interface TokenSequenceRepository {
    /**
     * Atomically reserves and returns the next token number.
     * This operation REQUIRES network and throws [IllegalStateException] if offline.
     */
    suspend fun getNextTokenNumber(clinicId: String, queueDayId: String, serviceId: String): Int

    /** Resets the sequence to 0 for the given service/day (used in manual reset). */
    suspend fun resetSequence(clinicId: String, queueDayId: String, serviceId: String)
}
