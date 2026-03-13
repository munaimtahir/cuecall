package com.cuecall.app.domain.model

/**
 * Represents the clinic profile. Single clinic per installation in MVP.
 */
data class Clinic(
    val id: String,
    val name: String,
    val logoUrl: String = "",
    val address: String = "",
    val phone: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * A named queue category (e.g., General OPD, Ultrasound, Dr. Ahmed).
 * Doctor queues are represented as services in MVP.
 */
data class Service(
    val id: String,
    val clinicId: String,
    val name: String,
    val code: String,
    val tokenPrefix: String,     // e.g. "G", "U", "L"
    val isActive: Boolean = true,
    val sortOrder: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Physical window or room label. Optional; used for display board.
 */
data class Counter(
    val id: String,
    val clinicId: String,
    val name: String,            // e.g. "Window 1", "Room 3"
    val serviceId: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * A registered device in the clinic system.
 * deviceMode: UNASSIGNED | RECEPTION | COUNTER | DISPLAY
 */
data class Device(
    val id: String,
    val clinicId: String,
    val deviceName: String,
    val deviceMode: DeviceMode = DeviceMode.UNASSIGNED,
    val assignedServiceId: String? = null,
    val assignedCounterId: String? = null,
    val printerAddress: String? = null,
    val lastSeenAt: Long = System.currentTimeMillis()
)

enum class DeviceMode { UNASSIGNED, RECEPTION, COUNTER, DISPLAY }

/**
 * Represents a business operating day. ID = "{clinicId}_{businessDate}" e.g. "clinic1_2024-01-15".
 */
data class QueueDay(
    val id: String,
    val clinicId: String,
    val businessDate: String,    // "yyyy-MM-dd"
    val isOpen: Boolean = true,
    val resetStrategy: ResetStrategy = ResetStrategy.DAILY,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class ResetStrategy { DAILY, MANUAL, NEVER }

/**
 * A patient queue ticket. Core entity of the system.
 */
data class Token(
    val id: String,
    val clinicId: String,
    val queueDayId: String,
    val serviceId: String,
    val counterId: String? = null,
    val tokenPrefix: String,
    val tokenNumber: Int,
    val displayNumber: String,   // e.g. "G-005"
    val status: TokenStatus = TokenStatus.WAITING,
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

enum class TokenStatus { WAITING, CALLED, RECALLED, SKIPPED, COMPLETED, CANCELLED }

/**
 * Audit log entry for every queue action.
 */
data class CallEvent(
    val id: String,
    val clinicId: String,
    val tokenId: String,
    val actionType: ActionType,
    val serviceId: String,
    val counterId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val createdByDeviceId: String = "",
    val metadata: Map<String, String> = emptyMap()
)

enum class ActionType { ISSUED, CALLED, RECALLED, SKIPPED, COMPLETED, CANCELLED }

/**
 * Clinic-wide settings, stored locally and in Firestore.
 */
data class AppSettings(
    val clinicId: String,
    val dailyResetEnabled: Boolean = true,
    val defaultStartNumber: Int = 1,
    val ticketFooterText: String = "Please wait for your turn",
    val soundEnabled: Boolean = true,
    val displayRefreshMode: String = "realtime",
    val printerPaperWidth: Int = 58,
    val adminPin: String = "",           // empty = no PIN protection
    val deviceId: String = "",
    val deviceMode: DeviceMode = DeviceMode.UNASSIGNED,
    val assignedServiceId: String? = null,
    val assignedCounterId: String? = null,
    val pairedPrinterAddress: String? = null,
    val pairedPrinterName: String? = null
)
