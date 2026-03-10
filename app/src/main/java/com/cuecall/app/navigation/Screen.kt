package com.cuecall.app.navigation

sealed class Screen(val route: String) {
    object ModeSelection : Screen("mode_selection")
    object Reception : Screen("reception")
    object Counter : Screen("counter")
    object Display : Screen("display")
    object Settings : Screen("settings")
    object ServiceManagement : Screen("service_management")
    object CounterManagement : Screen("counter_management")
    object PrinterSettings : Screen("printer_settings")
    object DailyHistory : Screen("daily_history")
    object Setup : Screen("setup")
}
