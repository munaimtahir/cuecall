package com.cuecall.app.printer

/**
 * Ticket data passed to the printer. Pure data — no domain model dependencies.
 */
data class TokenTicket(
    val clinicName: String,
    val serviceName: String,
    val tokenDisplayNumber: String,
    val issuedAt: Long,
    val footerText: String = "Please wait for your turn"
)

/**
 * PrinterManager interface. All printer implementations must implement this.
 * The printer layer is intentionally decoupled from queue domain logic.
 */
interface PrinterManager {
    /** Connect to a Bluetooth printer by MAC address. */
    suspend fun connect(address: String): Result<Unit>

    /** Disconnect the current printer. */
    fun disconnect()

    /** Returns true if a printer is currently connected. */
    fun isConnected(): Boolean

    /** Print a token ticket. */
    suspend fun printTicket(ticket: TokenTicket): Result<Unit>

    /** Returns the name of the currently connected printer, or null. */
    fun connectedPrinterName(): String?
}
