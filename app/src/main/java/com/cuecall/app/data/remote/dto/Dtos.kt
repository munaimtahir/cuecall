package com.cuecall.app.data.remote.dto

/**
 * Firestore DTOs — plain Maps/data classes that match the Firestore document structure.
 * Separate from domain models to allow independent evolution.
 */

data class TokenDto(
    val id: String = "",
    val clinicId: String = "",
    val queueDayId: String = "",
    val serviceId: String = "",
    val counterId: String? = null,
    val tokenPrefix: String = "",
    val tokenNumber: Int = 0,
    val displayNumber: String = "",
    val status: String = "WAITING",
    val issuedAt: Long = 0L,
    val calledAt: Long? = null,
    val completedAt: Long? = null,
    val skippedAt: Long? = null,
    val recalledAt: Long? = null,
    val cancelledAt: Long? = null,
    val notes: String = "",
    val createdByDeviceId: String = "",
    val updatedByDeviceId: String = ""
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "clinicId" to clinicId,
        "queueDayId" to queueDayId,
        "serviceId" to serviceId,
        "counterId" to counterId,
        "tokenPrefix" to tokenPrefix,
        "tokenNumber" to tokenNumber,
        "displayNumber" to displayNumber,
        "status" to status,
        "issuedAt" to issuedAt,
        "calledAt" to calledAt,
        "completedAt" to completedAt,
        "skippedAt" to skippedAt,
        "recalledAt" to recalledAt,
        "cancelledAt" to cancelledAt,
        "notes" to notes,
        "createdByDeviceId" to createdByDeviceId,
        "updatedByDeviceId" to updatedByDeviceId
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): TokenDto = TokenDto(
            id = map["id"] as? String ?: "",
            clinicId = map["clinicId"] as? String ?: "",
            queueDayId = map["queueDayId"] as? String ?: "",
            serviceId = map["serviceId"] as? String ?: "",
            counterId = map["counterId"] as? String,
            tokenPrefix = map["tokenPrefix"] as? String ?: "",
            tokenNumber = (map["tokenNumber"] as? Long)?.toInt() ?: 0,
            displayNumber = map["displayNumber"] as? String ?: "",
            status = map["status"] as? String ?: "WAITING",
            issuedAt = map["issuedAt"] as? Long ?: 0L,
            calledAt = map["calledAt"] as? Long,
            completedAt = map["completedAt"] as? Long,
            skippedAt = map["skippedAt"] as? Long,
            recalledAt = map["recalledAt"] as? Long,
            cancelledAt = map["cancelledAt"] as? Long,
            notes = map["notes"] as? String ?: "",
            createdByDeviceId = map["createdByDeviceId"] as? String ?: "",
            updatedByDeviceId = map["updatedByDeviceId"] as? String ?: ""
        )
    }
}

data class ServiceDto(
    val id: String = "",
    val clinicId: String = "",
    val name: String = "",
    val code: String = "",
    val tokenPrefix: String = "",
    val isActive: Boolean = true,
    val sortOrder: Int = 0,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "clinicId" to clinicId,
        "name" to name,
        "code" to code,
        "tokenPrefix" to tokenPrefix,
        "isActive" to isActive,
        "sortOrder" to sortOrder,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): ServiceDto = ServiceDto(
            id = map["id"] as? String ?: "",
            clinicId = map["clinicId"] as? String ?: "",
            name = map["name"] as? String ?: "",
            code = map["code"] as? String ?: "",
            tokenPrefix = map["tokenPrefix"] as? String ?: "",
            isActive = map["isActive"] as? Boolean ?: true,
            sortOrder = (map["sortOrder"] as? Long)?.toInt() ?: 0,
            createdAt = map["createdAt"] as? Long ?: 0L,
            updatedAt = map["updatedAt"] as? Long ?: 0L
        )
    }
}

data class CallEventDto(
    val id: String = "",
    val clinicId: String = "",
    val tokenId: String = "",
    val queueDayId: String = "",
    val actionType: String = "",
    val serviceId: String = "",
    val counterId: String? = null,
    val createdAt: Long = 0L,
    val createdByDeviceId: String = "",
    val metadata: Map<String, String> = emptyMap()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "clinicId" to clinicId,
        "tokenId" to tokenId,
        "queueDayId" to queueDayId,
        "actionType" to actionType,
        "serviceId" to serviceId,
        "counterId" to counterId,
        "createdAt" to createdAt,
        "createdByDeviceId" to createdByDeviceId,
        "metadata" to metadata
    )
}
