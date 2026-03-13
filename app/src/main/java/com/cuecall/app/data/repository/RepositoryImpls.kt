package com.cuecall.app.data.repository

import com.cuecall.app.data.local.dao.*
import com.cuecall.app.data.remote.source.FirestoreTokenSource
import com.cuecall.app.domain.model.*
import com.cuecall.app.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClinicRepositoryImpl @Inject constructor(
    private val clinicDao: ClinicDao
) : ClinicRepository {

    override fun observeClinic(clinicId: String): Flow<Clinic?> =
        clinicDao.observeClinic(clinicId).map { it?.toDomain() }

    override suspend fun saveClinic(clinic: Clinic) =
        clinicDao.upsertClinic(clinic.toEntity())

    override suspend fun getClinic(clinicId: String): Clinic? =
        clinicDao.getClinic(clinicId)?.toDomain()

    override suspend fun getAnyClinic(): Clinic? =
        clinicDao.getAnyClinic()?.toDomain()
}

@Singleton
class ServiceRepositoryImpl @Inject constructor(
    private val serviceDao: ServiceDao
) : ServiceRepository {

    override fun observeActiveServices(clinicId: String): Flow<List<Service>> =
        serviceDao.observeActiveServices(clinicId).map { list -> list.map { it.toDomain() } }

    override suspend fun getService(serviceId: String): Service? =
        serviceDao.getService(serviceId)?.toDomain()

    override suspend fun getAllServices(clinicId: String): List<Service> =
        serviceDao.getAllServices(clinicId).map { it.toDomain() }

    override suspend fun saveService(service: Service) =
        serviceDao.upsertService(service.toEntity())

    override suspend fun deleteService(serviceId: String) =
        serviceDao.deleteService(serviceId)

    override suspend fun setServiceActive(serviceId: String, isActive: Boolean) =
        serviceDao.setActive(serviceId, isActive)

    override suspend fun assignBlankClinicId(clinicId: String) =
        serviceDao.assignBlankClinicId(clinicId)
}

@Singleton
class CounterRepositoryImpl @Inject constructor(
    private val counterDao: CounterDao
) : CounterRepository {

    override fun observeActiveCounters(clinicId: String): Flow<List<Counter>> =
        counterDao.observeActiveCounters(clinicId).map { list -> list.map { it.toDomain() } }

    override suspend fun getCounter(counterId: String): Counter? =
        counterDao.getCounter(counterId)?.toDomain()

    override suspend fun getAllCounters(clinicId: String): List<Counter> =
        counterDao.getAllCounters(clinicId).map { it.toDomain() }

    override suspend fun saveCounter(counter: Counter) =
        counterDao.upsertCounter(counter.toEntity())

    override suspend fun deleteCounter(counterId: String) =
        counterDao.deleteCounter(counterId)

    override suspend fun assignBlankClinicId(clinicId: String) =
        counterDao.assignBlankClinicId(clinicId)
}

@Singleton
class QueueDayRepositoryImpl @Inject constructor(
    private val queueDayDao: QueueDayDao
) : QueueDayRepository {

    override suspend fun getOrCreateQueueDay(clinicId: String, businessDate: String): QueueDay {
        val existing = queueDayDao.getQueueDayByDate(clinicId, businessDate)
        if (existing != null) return existing.toDomain()
        val queueDay = QueueDay(
            id = "${clinicId}_${businessDate}",
            clinicId = clinicId,
            businessDate = businessDate
        )
        queueDayDao.upsertQueueDay(queueDay.toEntity())
        return queueDay
    }

    override suspend fun getQueueDay(queueDayId: String): QueueDay? =
        queueDayDao.getQueueDay(queueDayId)?.toDomain()

    override suspend fun saveQueueDay(queueDay: QueueDay) =
        queueDayDao.upsertQueueDay(queueDay.toEntity())

    override fun observeQueueDay(queueDayId: String): Flow<QueueDay?> =
        queueDayDao.observeQueueDay(queueDayId).map { it?.toDomain() }
}

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val tokenDao: TokenDao,
    private val firestoreSource: FirestoreTokenSource
) : TokenRepository {

    override fun observeTokensByQueueDay(queueDayId: String): Flow<List<Token>> =
        tokenDao.observeTokensByQueueDay(queueDayId).map { list -> list.map { it.toDomain() } }

    override fun observeWaitingTokens(queueDayId: String, serviceId: String?): Flow<List<Token>> =
        tokenDao.observeWaitingTokens(queueDayId, serviceId).map { list -> list.map { it.toDomain() } }

    override fun observeCalledToken(queueDayId: String, serviceId: String): Flow<Token?> =
        tokenDao.observeCalledToken(queueDayId, serviceId).map { it?.toDomain() }

    override suspend fun getToken(tokenId: String): Token? =
        tokenDao.getToken(tokenId)?.toDomain()

    override suspend fun saveToken(token: Token) {
        tokenDao.upsertToken(token.toEntity())
        firestoreSource.saveToken(token.clinicId, token.toDto())
    }

    override suspend fun updateTokenStatus(
        tokenId: String,
        status: TokenStatus,
        timestamp: Long,
        updatedByDeviceId: String
    ) {
        val token = tokenDao.getToken(tokenId) ?: return
        val fields: MutableMap<String, Any?> = mutableMapOf(
            "status" to status.name,
            "updatedByDeviceId" to updatedByDeviceId
        )
        when (status) {
            TokenStatus.CALLED -> {
                tokenDao.updateCalledAt(tokenId, status.name, timestamp, updatedByDeviceId)
                fields["calledAt"] = timestamp
            }
            TokenStatus.RECALLED -> {
                tokenDao.updateRecalledAt(tokenId, status.name, timestamp, updatedByDeviceId)
                fields["recalledAt"] = timestamp
            }
            TokenStatus.COMPLETED -> {
                tokenDao.updateCompletedAt(tokenId, status.name, timestamp, updatedByDeviceId)
                fields["completedAt"] = timestamp
            }
            TokenStatus.SKIPPED -> {
                tokenDao.updateSkippedAt(tokenId, status.name, timestamp, updatedByDeviceId)
                fields["skippedAt"] = timestamp
            }
            else -> tokenDao.updateStatus(tokenId, status.name, updatedByDeviceId)
        }
        firestoreSource.updateTokenFields(token.clinicId, tokenId, fields)
    }

    override suspend fun getTokensForDay(queueDayId: String): List<Token> =
        tokenDao.getTokensForDay(queueDayId).map { it.toDomain() }
}

@Singleton
class CallEventRepositoryImpl @Inject constructor(
    private val callEventDao: CallEventDao
) : CallEventRepository {

    override suspend fun logEvent(event: CallEvent) {
        // Derive queueDayId from token context — caller must pass it via metadata
        val queueDayId = event.metadata["queueDayId"] ?: ""
        callEventDao.insertEvent(event.toEntity(queueDayId))
    }

    override fun observeEventsForDay(queueDayId: String): Flow<List<CallEvent>> =
        callEventDao.observeEventsForDay(queueDayId).map { list -> list.map { it.toDomain() } }

    override suspend fun getEventsForDay(queueDayId: String): List<CallEvent> =
        callEventDao.getEventsForDay(queueDayId).map { it.toDomain() }
}

@Singleton
class TokenSequenceRepositoryImpl @Inject constructor(
    private val firestoreSource: FirestoreTokenSource
) : TokenSequenceRepository {

    override suspend fun getNextTokenNumber(
        clinicId: String,
        queueDayId: String,
        serviceId: String
    ): Int = firestoreSource.getNextTokenNumberAtomic(clinicId, queueDayId, serviceId)

    override suspend fun resetSequence(clinicId: String, queueDayId: String, serviceId: String) =
        firestoreSource.resetSequence(clinicId, queueDayId, serviceId)
}
