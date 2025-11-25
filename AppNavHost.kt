package com.example.calculatorbmi

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun BmiApp() {
    val navController = rememberNavController()
    val vm: BmiViewModel = viewModel()

    NavHost(navController = navController, startDestination = "input") {
        composable("input") {
            InputScreen(
                onCalculate = { w, h -> navController.navigate("result/$w/$h") },
                onHistory = { navController.navigate("history") }
            )
        }
        composable(
            route = "result/{weight}/{height}",
            arguments = listOf(
                navArgument("weight") { type = NavType.FloatType },
                navArgument("height") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val w = backStackEntry.arguments?.getFloat("weight") ?: 0f
            val h = backStackEntry.arguments?.getFloat("height") ?: 0f

            // Autozapis jednokrotny dla tej pary parametrów
            val saved = remember(w, h) { mutableStateOf(false) }
            LaunchedEffect(w, h) {
                if (!saved.value) {
                    vm.autoSave(w, h)
                    saved.value = true
                }
            }

            ResultScreen(
                weightKg = w,
                heightCm = h,
                onBack = { navController.popBackStack() },
                onHistory = { navController.navigate("history") }
            )
        }
        composable("history") {
            val history by vm.history.collectAsState()
            HistoryScreen(
                items = history,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
