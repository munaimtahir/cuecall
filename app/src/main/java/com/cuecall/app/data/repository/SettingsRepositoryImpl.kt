package com.cuecall.app.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.cuecall.app.domain.model.AppSettings
import com.cuecall.app.domain.model.DeviceMode
import com.cuecall.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object Keys {
        val CLINIC_ID = stringPreferencesKey("clinic_id")
        val DAILY_RESET_ENABLED = booleanPreferencesKey("daily_reset_enabled")
        val DEFAULT_START_NUMBER = intPreferencesKey("default_start_number")
        val TICKET_FOOTER_TEXT = stringPreferencesKey("ticket_footer_text")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val DISPLAY_REFRESH_MODE = stringPreferencesKey("display_refresh_mode")
        val PRINTER_PAPER_WIDTH = intPreferencesKey("printer_paper_width")
        val ADMIN_PIN = stringPreferencesKey("admin_pin")
        val DEVICE_ID = stringPreferencesKey("device_id")
        val DEVICE_MODE = stringPreferencesKey("device_mode")
        val ASSIGNED_SERVICE_ID = stringPreferencesKey("assigned_service_id")
        val ASSIGNED_COUNTER_ID = stringPreferencesKey("assigned_counter_id")
        val PAIRED_PRINTER_ADDRESS = stringPreferencesKey("paired_printer_address")
        val PAIRED_PRINTER_NAME = stringPreferencesKey("paired_printer_name")
    }

    override fun observeSettings(): Flow<AppSettings> =
        dataStore.data.map { prefs -> prefs.toAppSettings() }

    override suspend fun getSettings(): AppSettings =
        dataStore.data.first().toAppSettings()

    override suspend fun saveSettings(settings: AppSettings) {
        dataStore.edit { prefs ->
            prefs[Keys.CLINIC_ID] = settings.clinicId
            prefs[Keys.DAILY_RESET_ENABLED] = settings.dailyResetEnabled
            prefs[Keys.DEFAULT_START_NUMBER] = settings.defaultStartNumber
            prefs[Keys.TICKET_FOOTER_TEXT] = settings.ticketFooterText
            prefs[Keys.SOUND_ENABLED] = settings.soundEnabled
            prefs[Keys.DISPLAY_REFRESH_MODE] = settings.displayRefreshMode
            prefs[Keys.PRINTER_PAPER_WIDTH] = settings.printerPaperWidth
            prefs[Keys.ADMIN_PIN] = settings.adminPin
            prefs[Keys.DEVICE_ID] = settings.deviceId
            prefs[Keys.DEVICE_MODE] = settings.deviceMode.name
            settings.assignedServiceId?.let { prefs[Keys.ASSIGNED_SERVICE_ID] = it }
                ?: prefs.remove(Keys.ASSIGNED_SERVICE_ID)
            settings.assignedCounterId?.let { prefs[Keys.ASSIGNED_COUNTER_ID] = it }
                ?: prefs.remove(Keys.ASSIGNED_COUNTER_ID)
            settings.pairedPrinterAddress?.let { prefs[Keys.PAIRED_PRINTER_ADDRESS] = it }
                ?: prefs.remove(Keys.PAIRED_PRINTER_ADDRESS)
            settings.pairedPrinterName?.let { prefs[Keys.PAIRED_PRINTER_NAME] = it }
                ?: prefs.remove(Keys.PAIRED_PRINTER_NAME)
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        clinicId = this[Keys.CLINIC_ID] ?: "",
        dailyResetEnabled = this[Keys.DAILY_RESET_ENABLED] ?: true,
        defaultStartNumber = this[Keys.DEFAULT_START_NUMBER] ?: 1,
        ticketFooterText = this[Keys.TICKET_FOOTER_TEXT] ?: "Please wait for your turn",
        soundEnabled = this[Keys.SOUND_ENABLED] ?: true,
        displayRefreshMode = this[Keys.DISPLAY_REFRESH_MODE] ?: "realtime",
        printerPaperWidth = this[Keys.PRINTER_PAPER_WIDTH] ?: 58,
        adminPin = this[Keys.ADMIN_PIN] ?: "",
        deviceId = this[Keys.DEVICE_ID] ?: "",
        deviceMode = DeviceMode.valueOf(this[Keys.DEVICE_MODE] ?: DeviceMode.UNASSIGNED.name),
        assignedServiceId = this[Keys.ASSIGNED_SERVICE_ID],
        assignedCounterId = this[Keys.ASSIGNED_COUNTER_ID],
        pairedPrinterAddress = this[Keys.PAIRED_PRINTER_ADDRESS],
        pairedPrinterName = this[Keys.PAIRED_PRINTER_NAME]
    )
}
