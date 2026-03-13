package com.cuecall.app.data.local.dao

import androidx.room.*
import com.cuecall.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicDao {
    @Query("SELECT * FROM clinics WHERE id = :clinicId LIMIT 1")
    suspend fun getClinic(clinicId: String): ClinicEntity?

    @Query("SELECT * FROM clinics WHERE id = :clinicId LIMIT 1")
    fun observeClinic(clinicId: String): Flow<ClinicEntity?>

    @Query("SELECT * FROM clinics ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getAnyClinic(): ClinicEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertClinic(clinic: ClinicEntity)
}

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services WHERE clinicId = :clinicId AND isActive = 1 ORDER BY sortOrder ASC, name ASC")
    fun observeActiveServices(clinicId: String): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE clinicId = :clinicId ORDER BY sortOrder ASC, name ASC")
    suspend fun getAllServices(clinicId: String): List<ServiceEntity>

    @Query("SELECT * FROM services WHERE id = :serviceId LIMIT 1")
    suspend fun getService(serviceId: String): ServiceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertService(service: ServiceEntity)

    @Query("DELETE FROM services WHERE id = :serviceId")
    suspend fun deleteService(serviceId: String)

    @Query("UPDATE services SET isActive = :isActive, updatedAt = :updatedAt WHERE id = :serviceId")
    suspend fun setActive(serviceId: String, isActive: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE services SET clinicId = :clinicId WHERE clinicId = ''")
    suspend fun assignBlankClinicId(clinicId: String)
}

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters WHERE clinicId = :clinicId AND isActive = 1 ORDER BY name ASC")
    fun observeActiveCounters(clinicId: String): Flow<List<CounterEntity>>

    @Query("SELECT * FROM counters WHERE id = :counterId LIMIT 1")
    suspend fun getCounter(counterId: String): CounterEntity?

    @Query("SELECT * FROM counters WHERE clinicId = :clinicId ORDER BY name ASC")
    suspend fun getAllCounters(clinicId: String): List<CounterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCounter(counter: CounterEntity)

    @Query("DELETE FROM counters WHERE id = :counterId")
    suspend fun deleteCounter(counterId: String)

    @Query("UPDATE counters SET clinicId = :clinicId WHERE clinicId = ''")
    suspend fun assignBlankClinicId(clinicId: String)
}

@Dao
interface QueueDayDao {
    @Query("SELECT * FROM queue_days WHERE id = :queueDayId LIMIT 1")
    suspend fun getQueueDay(queueDayId: String): QueueDayEntity?

    @Query("SELECT * FROM queue_days WHERE id = :queueDayId LIMIT 1")
    fun observeQueueDay(queueDayId: String): Flow<QueueDayEntity?>

    @Query("SELECT * FROM queue_days WHERE clinicId = :clinicId AND businessDate = :businessDate LIMIT 1")
    suspend fun getQueueDayByDate(clinicId: String, businessDate: String): QueueDayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQueueDay(queueDay: QueueDayEntity)
}

@Dao
interface TokenDao {
    @Query("SELECT * FROM tokens WHERE queueDayId = :queueDayId ORDER BY issuedAt ASC")
    fun observeTokensByQueueDay(queueDayId: String): Flow<List<TokenEntity>>

    @Query("""
        SELECT * FROM tokens 
        WHERE queueDayId = :queueDayId 
        AND status = 'WAITING'
        AND (:serviceId IS NULL OR serviceId = :serviceId)
        ORDER BY tokenNumber ASC
    """)
    fun observeWaitingTokens(queueDayId: String, serviceId: String?): Flow<List<TokenEntity>>

    @Query("""
        SELECT * FROM tokens 
        WHERE queueDayId = :queueDayId 
        AND serviceId = :serviceId 
        AND status IN ('CALLED', 'RECALLED')
        ORDER BY calledAt DESC 
        LIMIT 1
    """)
    fun observeCalledToken(queueDayId: String, serviceId: String): Flow<TokenEntity?>

    @Query("SELECT * FROM tokens WHERE id = :tokenId LIMIT 1")
    suspend fun getToken(tokenId: String): TokenEntity?

    @Query("SELECT * FROM tokens WHERE queueDayId = :queueDayId ORDER BY issuedAt ASC")
    suspend fun getTokensForDay(queueDayId: String): List<TokenEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertToken(token: TokenEntity)

    @Query("""
        UPDATE tokens 
        SET status = :status, updatedByDeviceId = :updatedByDeviceId
        WHERE id = :tokenId
    """)
    suspend fun updateStatus(tokenId: String, status: String, updatedByDeviceId: String)

    @Query("""
        UPDATE tokens 
        SET status = :status, calledAt = :timestamp, updatedByDeviceId = :deviceId
        WHERE id = :tokenId
    """)
    suspend fun updateCalledAt(tokenId: String, status: String, timestamp: Long, deviceId: String)

    @Query("""
        UPDATE tokens 
        SET status = :status, completedAt = :timestamp, updatedByDeviceId = :deviceId
        WHERE id = :tokenId
    """)
    suspend fun updateCompletedAt(tokenId: String, status: String, timestamp: Long, deviceId: String)

    @Query("""
        UPDATE tokens 
        SET status = :status, skippedAt = :timestamp, updatedByDeviceId = :deviceId
        WHERE id = :tokenId
    """)
    suspend fun updateSkippedAt(tokenId: String, status: String, timestamp: Long, deviceId: String)

    @Query("""
        UPDATE tokens 
        SET status = :status, recalledAt = :timestamp, updatedByDeviceId = :deviceId
        WHERE id = :tokenId
    """)
    suspend fun updateRecalledAt(tokenId: String, status: String, timestamp: Long, deviceId: String)
}

@Dao
interface CallEventDao {
    @Query("SELECT * FROM call_events WHERE queueDayId = :queueDayId ORDER BY createdAt DESC")
    fun observeEventsForDay(queueDayId: String): Flow<List<CallEventEntity>>

    @Query("SELECT * FROM call_events WHERE queueDayId = :queueDayId ORDER BY createdAt DESC")
    suspend fun getEventsForDay(queueDayId: String): List<CallEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: CallEventEntity)
}

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices WHERE id = :deviceId LIMIT 1")
    suspend fun getDevice(deviceId: String): DeviceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDevice(device: DeviceEntity)
}
