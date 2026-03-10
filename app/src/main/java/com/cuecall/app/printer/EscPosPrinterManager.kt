package com.cuecall.app.printer

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ESC/POS Bluetooth printer implementation stub.
 *
 * This class contains the full scaffolding for a real ESC/POS printer integration:
 * - Bluetooth connection via RFCOMM
 * - ESC/POS byte command formatting
 * - Ticket layout
 *
 * ⚠ INTEGRATION POINT: The actual vendor SDK call (if needed beyond raw sockets)
 * should be placed in the [printTicket] method where TODO is marked.
 * Raw RFCOMM + ESC/POS byte streams work with most 58mm thermal printers without
 * any vendor SDK.
 */
@Singleton
class EscPosPrinterManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter?
) : PrinterManager {

    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var connectedDevice: BluetoothDevice? = null

    // Standard SPP UUID for Bluetooth serial communication
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    override suspend fun connect(address: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            val adapter = bluetoothAdapter ?: error("Bluetooth not available on this device")
            val device = adapter.getRemoteDevice(address)
            val newSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            adapter.cancelDiscovery()
            newSocket.connect()
            socket = newSocket
            outputStream = newSocket.outputStream
            connectedDevice = device
            Log.i("EscPosPrinter", "Connected to ${device.name} ($address)")
        }
    }

    override fun disconnect() {
        runCatching {
            outputStream?.close()
            socket?.close()
        }
        outputStream = null
        socket = null
        connectedDevice = null
        Log.i("EscPosPrinter", "Printer disconnected")
    }

    override fun isConnected(): Boolean = socket?.isConnected == true

    override fun connectedPrinterName(): String? = connectedDevice?.name

    override suspend fun printTicket(ticket: TokenTicket): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            val out = outputStream ?: error("Printer not connected")

            // TODO: If using a vendor SDK (e.g., iposprinter, Sunmi), replace the
            // raw byte stream calls below with the vendor SDK's print API.
            // For generic 58mm ESC/POS printers, the raw commands work as-is.

            out.write(ESC_INIT)
            out.write(CENTER_ALIGN)
            out.write(BOLD_ON)
            out.write(formatLine(ticket.clinicName))
            out.write(BOLD_OFF)
            out.write(LINE_FEED)

            out.write(CENTER_ALIGN)
            out.write(LARGE_TEXT)
            out.write(formatLine(ticket.tokenDisplayNumber))
            out.write(NORMAL_TEXT)
            out.write(LINE_FEED)

            out.write(CENTER_ALIGN)
            out.write(formatLine(ticket.serviceName))
            out.write(LINE_FEED)

            val timeStr = SimpleDateFormat("HH:mm  dd-MM-yyyy", Locale.getDefault())
                .format(Date(ticket.issuedAt))
            out.write(CENTER_ALIGN)
            out.write(formatLine(timeStr))
            out.write(LINE_FEED)

            out.write(DASHED_LINE)
            out.write(CENTER_ALIGN)
            out.write(formatLine(ticket.footerText))
            out.write(LINE_FEED)
            out.write(LINE_FEED)
            out.write(LINE_FEED)
            out.write(PAPER_CUT)
            out.flush()
        }
    }

    // --- ESC/POS byte command constants ---
    private val ESC_INIT = byteArrayOf(0x1B, 0x40)
    private val CENTER_ALIGN = byteArrayOf(0x1B, 0x61, 0x01)
    private val BOLD_ON = byteArrayOf(0x1B, 0x45, 0x01)
    private val BOLD_OFF = byteArrayOf(0x1B, 0x45, 0x00)
    private val LARGE_TEXT = byteArrayOf(0x1D, 0x21, 0x11) // double width + height
    private val NORMAL_TEXT = byteArrayOf(0x1D, 0x21, 0x00)
    private val LINE_FEED = byteArrayOf(0x0A)
    private val DASHED_LINE = "--------------------------------\n".toByteArray(Charsets.UTF_8)
    private val PAPER_CUT = byteArrayOf(0x1D, 0x56, 0x41, 0x00) // partial cut

    private fun formatLine(text: String): ByteArray = "$text\n".toByteArray(Charsets.UTF_8)
}
