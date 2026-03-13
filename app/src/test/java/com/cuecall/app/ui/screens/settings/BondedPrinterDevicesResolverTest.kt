package com.cuecall.app.ui.screens.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BondedPrinterDevicesResolverTest {

    private val resolver = BondedPrinterDevicesResolver()

    @Test
    fun `returns permission error when bluetooth connect permission is missing`() {
        val result = resolver.resolve(
            hasBluetoothConnectPermission = false,
            bluetoothAvailable = true,
            bluetoothEnabled = true,
            bondedDevices = listOf(BluetoothDeviceInfo("Printer", "AA:BB"))
        )

        assertEquals("Bluetooth permission required", result.error)
        assertTrue(result.devices.isEmpty())
    }

    @Test
    fun `returns bonded devices sorted by name when bluetooth is ready`() {
        val result = resolver.resolve(
            hasBluetoothConnectPermission = true,
            bluetoothAvailable = true,
            bluetoothEnabled = true,
            bondedDevices = listOf(
                BluetoothDeviceInfo("Zebra", "AA:BB"),
                BluetoothDeviceInfo("Alpha", "CC:DD")
            )
        )

        assertEquals(listOf("Alpha", "Zebra"), result.devices.map { it.name })
    }
}
