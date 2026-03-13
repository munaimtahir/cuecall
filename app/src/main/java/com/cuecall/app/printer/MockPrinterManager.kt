package com.cuecall.app.printer

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock printer for development and testing.
 * Logs the ticket to Logcat and always succeeds.
 * Used when no printer is paired or in debug builds.
 */
@Singleton
class MockPrinterManager @Inject constructor() : PrinterManager {

    private var connected = false
    private var printerName: String? = null

    override suspend fun connect(address: String): Result<Unit> {
        connected = true
        printerName = "Mock Printer ($address)"
        logDebug("Connected to mock printer at $address")
        return Result.success(Unit)
    }

    override fun disconnect() {
        connected = false
        printerName = null
        logDebug("Disconnected from mock printer")
    }

    override fun isConnected(): Boolean = connected

    override fun connectedPrinterName(): String? = printerName

    override suspend fun printTicket(ticket: TokenTicket): Result<Unit> {
        logInfo(buildTicketLog(ticket))
        return Result.success(Unit)
    }

    private fun logDebug(message: String) {
        runCatching { Log.d("MockPrinter", message) }
            .getOrElse { println("MockPrinter DEBUG: $message") }
    }

    private fun logInfo(message: String) {
        runCatching { Log.i("MockPrinter", message) }
            .getOrElse { println("MockPrinter INFO: $message") }
    }

    private fun buildTicketLog(ticket: TokenTicket): String {
        val separator = "-".repeat(32)
        return buildString {
            appendLine()
            appendLine(separator)
            appendLine("  ${ticket.clinicName}")
            appendLine(separator)
            appendLine("  Token: ${ticket.tokenDisplayNumber}")
            appendLine("  Service: ${ticket.serviceName}")
            appendLine("  Time: ${java.text.SimpleDateFormat("HH:mm dd-MM-yyyy", java.util.Locale.getDefault()).format(java.util.Date(ticket.issuedAt))}")
            appendLine(separator)
            appendLine("  ${ticket.footerText}")
            appendLine(separator)
        }
    }
}
