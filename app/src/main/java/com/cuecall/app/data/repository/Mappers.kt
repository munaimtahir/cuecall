package com.cuecall.app.data.repository

import com.cuecall.app.data.local.entity.*
import com.cuecall.app.data.remote.dto.TokenDto
import com.cuecall.app.domain.model.*

// --- Entity → Domain ---

fun ClinicEntity.toDomain() = Clinic(
    id = id, name = name, logoUrl = logoUrl,
    address = address, phone = phone, createdAt = createdAt, updatedAt = updatedAt
)

fun ServiceEntity.toDomain() = Service(
    id = id, clinicId = clinicId, name = name, code = code,
    tokenPrefix = tokenPrefix, isActive = isActive, sortOrder = sortOrder,
    createdAt = createdAt, updatedAt = updatedAt
)

fun CounterEntity.toDomain(): Counter {
    val ids = if (serviceIdsJson.isBlank() || serviceIdsJson == "[]") emptyList()
    else serviceIdsJson.removeSurrounding("[", "]").split(",")
        .map { it.trim().removeSurrounding("\"") }.filter { it.isNotBlank() }
    return Counter(
        id = id, clinicId = clinicId, name = name, serviceIds = ids,
        isActive = isActive, createdAt = createdAt, updatedAt = updatedAt
    )
}

fun QueueDayEntity.toDomain() = QueueDay(
    id = id, clinicId = clinicId, businessDate = businessDate, isOpen = isOpen,
    resetStrategy = ResetStrategy.valueOf(resetStrategy),
    createdAt = createdAt, updatedAt = updatedAt
)

fun TokenEntity.toDomain() = Token(
    id = id, clinicId = clinicId, queueDayId = queueDayId, serviceId = serviceId,
    counterId = counterId, tokenPrefix = tokenPrefix, tokenNumber = tokenNumber,
    displayNumber = displayNumber, status = TokenStatus.valueOf(status),
    issuedAt = issuedAt, calledAt = calledAt, completedAt = completedAt,
    skippedAt = skippedAt, recalledAt = recalledAt, cancelledAt = cancelledAt,
    notes = notes, createdByDeviceId = createdByDeviceId, updatedByDeviceId = updatedByDeviceId
)

fun CallEventEntity.toDomain() = CallEvent(
    id = id, clinicId = clinicId, tokenId = tokenId,
    actionType = ActionType.valueOf(actionType), serviceId = serviceId,
    counterId = counterId, createdAt = createdAt, createdByDeviceId = createdByDeviceId
)

// --- Domain → Entity ---

fun Clinic.toEntity() = ClinicEntity(
    id = id, name = name, logoUrl = logoUrl,
    address = address, phone = phone, createdAt = createdAt, updatedAt = updatedAt
)

fun Service.toEntity() = ServiceEntity(
    id = id, clinicId = clinicId, name = name, code = code,
    tokenPrefix = tokenPrefix, isActive = isActive, sortOrder = sortOrder,
    createdAt = createdAt, updatedAt = updatedAt
)

fun Counter.toEntity(): CounterEntity {
    val idsJson = "[${serviceIds.joinToString(",") { "\"$it\"" }}]"
    return CounterEntity(
        id = id, clinicId = clinicId, name = name, serviceIdsJson = idsJson,
        isActive = isActive, createdAt = createdAt, updatedAt = updatedAt
    )
}

fun QueueDay.toEntity() = QueueDayEntity(
    id = id, clinicId = clinicId, businessDate = businessDate, isOpen = isOpen,
    resetStrategy = resetStrategy.name, createdAt = createdAt, updatedAt = updatedAt
)

fun Token.toEntity() = TokenEntity(
    id = id, clinicId = clinicId, queueDayId = queueDayId, serviceId = serviceId,
    counterId = counterId, tokenPrefix = tokenPrefix, tokenNumber = tokenNumber,
    displayNumber = displayNumber, status = status.name,
    issuedAt = issuedAt, calledAt = calledAt, completedAt = completedAt,
    skippedAt = skippedAt, recalledAt = recalledAt, cancelledAt = cancelledAt,
    notes = notes, createdByDeviceId = createdByDeviceId, updatedByDeviceId = updatedByDeviceId
)

fun CallEvent.toEntity(queueDayId: String) = CallEventEntity(
    id = id, clinicId = clinicId, tokenId = tokenId, queueDayId = queueDayId,
    actionType = actionType.name, serviceId = serviceId, counterId = counterId,
    createdAt = createdAt, createdByDeviceId = createdByDeviceId
)

// --- Domain → Firestore DTO ---

fun Token.toDto() = TokenDto(
    id = id, clinicId = clinicId, queueDayId = queueDayId, serviceId = serviceId,
    counterId = counterId, tokenPrefix = tokenPrefix, tokenNumber = tokenNumber,
    displayNumber = displayNumber, status = status.name,
    issuedAt = issuedAt, calledAt = calledAt, completedAt = completedAt,
    skippedAt = skippedAt, recalledAt = recalledAt, cancelledAt = cancelledAt,
    notes = notes, createdByDeviceId = createdByDeviceId, updatedByDeviceId = updatedByDeviceId
)

fun TokenDto.toDomain() = Token(
    id = id, clinicId = clinicId, queueDayId = queueDayId, serviceId = serviceId,
    counterId = counterId, tokenPrefix = tokenPrefix, tokenNumber = tokenNumber,
    displayNumber = displayNumber, status = runCatching { TokenStatus.valueOf(status) }.getOrDefault(TokenStatus.WAITING),
    issuedAt = issuedAt, calledAt = calledAt, completedAt = completedAt,
    skippedAt = skippedAt, recalledAt = recalledAt, cancelledAt = cancelledAt,
    notes = notes, createdByDeviceId = createdByDeviceId, updatedByDeviceId = updatedByDeviceId
)

fun TokenDto.toEntity() = toDomain().toEntity()
