package com.cuecall.app.data.repository

import com.cuecall.app.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class MappersTest {

    private val testClinic = Clinic(
        id = "c1", name = "Test Clinic", logoUrl = "", address = "123 Street", phone = "0300-1234567"
    )

    private val testService = Service(
        id = "s1", clinicId = "c1", name = "General OPD", code = "GEN", tokenPrefix = "G",
        isActive = true, sortOrder = 1
    )

    private val testToken = Token(
        id = "t1", clinicId = "c1", queueDayId = "c1_2024-01-15",
        serviceId = "s1", tokenPrefix = "G", tokenNumber = 7, displayNumber = "G-007",
        status = TokenStatus.WAITING, issuedAt = 1_700_000_000_000L
    )

    @Test
    fun `clinic entity roundtrip preserves all fields`() {
        val entity = testClinic.toEntity()
        val domain = entity.toDomain()
        assertEquals(testClinic.id, domain.id)
        assertEquals(testClinic.name, domain.name)
        assertEquals(testClinic.address, domain.address)
        assertEquals(testClinic.phone, domain.phone)
    }

    @Test
    fun `service entity roundtrip preserves all fields`() {
        val entity = testService.toEntity()
        val domain = entity.toDomain()
        assertEquals(testService.id, domain.id)
        assertEquals(testService.tokenPrefix, domain.tokenPrefix)
        assertEquals(testService.isActive, domain.isActive)
        assertEquals(testService.sortOrder, domain.sortOrder)
    }

    @Test
    fun `counter with assigned service roundtrips correctly`() {
        val counter = Counter(
            id = "ctr1", clinicId = "c1", name = "Window 1",
            serviceId = "svc1"
        )
        val entity = counter.toEntity()
        val domain = entity.toDomain()
        assertEquals("svc1", domain.serviceId)
    }

    @Test
    fun `counter with no assigned service roundtrips correctly`() {
        val counter = Counter(id = "ctr2", clinicId = "c1", name = "Room 2")
        val entity = counter.toEntity()
        val domain = entity.toDomain()
        assertNull(domain.serviceId)
    }

    @Test
    fun `token entity roundtrip preserves status and timestamps`() {
        val entity = testToken.toEntity()
        val domain = entity.toDomain()
        assertEquals(testToken.tokenNumber, domain.tokenNumber)
        assertEquals(testToken.displayNumber, domain.displayNumber)
        assertEquals(TokenStatus.WAITING, domain.status)
        assertEquals(testToken.issuedAt, domain.issuedAt)
        assertNull(domain.calledAt)
    }

    @Test
    fun `token dto roundtrip preserves all fields`() {
        val dto = testToken.toDto()
        val domain = dto.toDomain()
        assertEquals(testToken.id, domain.id)
        assertEquals(testToken.displayNumber, domain.displayNumber)
        assertEquals(testToken.status, domain.status)
    }

    @Test
    fun `unknown token status in dto defaults to WAITING`() {
        val dto = testToken.toDto().copy(status = "INVALID_STATUS")
        val domain = dto.toDomain()
        assertEquals(TokenStatus.WAITING, domain.status)
    }
}
