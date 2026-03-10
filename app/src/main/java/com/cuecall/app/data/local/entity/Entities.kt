package com.cuecall.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "clinics")
data class ClinicEntity(
    @PrimaryKey val id: String,
    val name: String,
    val logoUrl: String = "",
    val address: String = "",
    val phone: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "services",
    indices = [Index("clinicId"), Index("isActive")]
)
data class ServiceEntity(
    @PrimaryKey val id: String,
    val clinicId: String,
    val name: String,
    val code: String,
    val tokenPrefix: String,
    val isActive: Boolean = true,
    val sortOrder: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "counters",
    indices = [Index("clinicId")]
)
data class CounterEntity(
    @PrimaryKey val id: String,
    val clinicId: String,
    val name: String,
    val serviceIdsJson: String = "[]", // JSON array of service IDs
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "queue_days",
    indices = [Index("clinicId"), Index("businessDate")]
)
data class QueueDayEntity(
    @PrimaryKey val id: String,     // "{clinicId}_{businessDate}"
    val clinicId: String,
    val businessDate: String,       // "yyyy-MM-dd"
    val isOpen: Boolean = true,
    val resetStrategy: String = "DAILY",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "tokens",
    indices = [
        Index("queueDayId"),
        Index("serviceId"),
        Index("status"),
        Index("queueDayId", "serviceId", "status")
    ]
)
data class TokenEntity(
    @PrimaryKey val id: String,
    val clinicId: String,
    val queueDayId: String,
    val serviceId: String,
    val counterId: String? = null,
    val tokenPrefix: String,
    val tokenNumber: Int,
    val displayNumber: String,
    val status: String = "WAITING",
    val issuedAt: Long = System.currentTimeMillis(),
    val calledAt: Long? = null,
    val completedAt: Long? = null,
    val skippedAt: Long? = null,
    val recalledAt: Long? = null,
    val cancelledAt: Long? = null,
    val notes: String = "",
    val createdByDeviceId: String = "",
    val updatedByDeviceId: String = ""
)

@Entity(
    tableName = "call_events",
    indices = [Index("queueDayId"), Index("tokenId")]
)
data class CallEventEntity(
    @PrimaryKey val id: String,
    val clinicId: String,
    val tokenId: String,
    val queueDayId: String,         // denormalized for efficient queries
    val actionType: String,
    val serviceId: String,
    val counterId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val createdByDeviceId: String = "",
    val metadataJson: String = "{}"
)

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val id: String,
    val clinicId: String,
    val deviceName: String,
    val deviceMode: String = "UNASSIGNED",
    val assignedServiceId: String? = null,
    val assignedCounterId: String? = null,
    val printerAddress: String? = null,
    val lastSeenAt: Long = System.currentTimeMillis()
)
