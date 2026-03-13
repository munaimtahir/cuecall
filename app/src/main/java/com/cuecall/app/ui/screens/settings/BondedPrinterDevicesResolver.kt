package com.cuecall.app.ui.screens.settings

data class BondedPrinterDevicesResult(
    val devices: List<BluetoothDeviceInfo> = emptyList(),
    val error: String? = null
)

class BondedPrinterDevicesResolver {
    fun resolve(
        hasBluetoothConnectPermission: Boolean,
        bluetoothAvailable: Boolean,
        bluetoothEnabled: Boolean,
        bondedDevices: List<BluetoothDeviceInfo>
    ): BondedPrinterDevicesResult {
        if (!hasBluetoothConnectPermission) {
            return BondedPrinterDevicesResult(error = "Bluetooth permission required")
        }
        if (!bluetoothAvailable) {
            return BondedPrinterDevicesResult(error = "Bluetooth not available on this device")
        }
        if (!bluetoothEnabled) {
            return BondedPrinterDevicesResult(error = "Bluetooth is disabled")
        }
        return BondedPrinterDevicesResult(devices = bondedDevices.sortedBy { it.name.lowercase() })
    }
}
