package com.cuecall.app.printer

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class MockPrinterManagerTest {

    private val printer = MockPrinterManager()

    @Test
    fun `initial state is disconnected`() {
        assertFalse(printer.isConnected())
        assertNull(printer.connectedPrinterName())
    }

    @Test
    fun `connect sets connected state`() = runTest {
        printer.connect("AA:BB:CC:DD:EE:FF")
        assertTrue(printer.isConnected())
        assertNotNull(printer.connectedPrinterName())
    }

    @Test
    fun `disconnect clears connected state`() = runTest {
        printer.connect("AA:BB:CC:DD:EE:FF")
        printer.disconnect()
        assertFalse(printer.isConnected())
        assertNull(printer.connectedPrinterName())
    }

    @Test
    fun `printTicket always succeeds in mock`() = runTest {
        printer.connect("AA:BB:CC:DD:EE:FF")
        val ticket = TokenTicket(
            clinicName = "Test Clinic",
            serviceName = "General OPD",
            tokenDisplayNumber = "G-005",
            issuedAt = System.currentTimeMillis(),
            footerText = "Please wait for your turn"
        )
        val result = printer.printTicket(ticket)
        assertTrue(result.isSuccess)
    }
}
