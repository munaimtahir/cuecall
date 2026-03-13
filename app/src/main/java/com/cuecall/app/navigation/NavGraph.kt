package com.cuecall.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cuecall.app.ui.screens.counter.CounterScreen
import com.cuecall.app.ui.screens.display.DisplayScreen
import com.cuecall.app.ui.screens.history.DailyHistoryScreen
import com.cuecall.app.ui.screens.modeselection.ModeSelectionScreen
import com.cuecall.app.ui.screens.reception.ReceptionScreen
import com.cuecall.app.ui.screens.settings.*

@Composable
fun CueCallNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ModeSelection.route
    ) {
        composable(Screen.ModeSelection.route) {
            ModeSelectionScreen(
                onReceptionClick = { navController.navigate(Screen.Reception.route) },
                onCounterClick = { navController.navigate(Screen.Counter.route) },
                onDisplayClick = { navController.navigate(Screen.Display.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Reception.route) {
            ReceptionScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Counter.route) {
            CounterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHistory = { navController.navigate(Screen.DailyHistory.route) }
            )
        }

        composable(Screen.Display.route) {
            DisplayScreen(
                onExitDisplay = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToClinicSetup = { navController.navigate(Screen.ClinicSetup.route) },
                onNavigateToDeviceAssignment = { navController.navigate(Screen.DeviceAssignment.route) },
                onNavigateToServices = { navController.navigate(Screen.ServiceManagement.route) },
                onNavigateToCounters = { navController.navigate(Screen.CounterManagement.route) },
                onNavigateToPrinter = { navController.navigate(Screen.PrinterSettings.route) },
                onNavigateToHistory = { navController.navigate(Screen.DailyHistory.route) }
            )
        }

        composable(Screen.ClinicSetup.route) {
            ClinicSetupScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DeviceAssignment.route) {
            DeviceAssignmentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ServiceManagement.route) {
            ServiceManagementScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CounterManagement.route) {
            CounterManagementScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PrinterSettings.route) {
            PrinterSettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DailyHistory.route) {
            DailyHistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
