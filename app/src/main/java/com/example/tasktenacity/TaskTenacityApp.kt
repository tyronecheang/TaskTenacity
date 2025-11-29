package com.example.tasktenacity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasktenacity.data.TaskState
import com.example.tasktenacity.ui.screens.AddTaskScreen
import com.example.tasktenacity.ui.screens.AllTasksScreen
import com.example.tasktenacity.ui.screens.TipsScreen
import com.example.tasktenacity.ui.screens.TodayScreen
import com.example.tasktenacity.ui.navigation.BottomNav


object Destinations {
    const val TODAY = "today"
    const val ALL_TASKS = "all_tasks"
    const val ADD_TASK = "add_task"
    const val TIPS = "tips"
}

@Composable
fun TaskTenacityApp(taskState: TaskState) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNav(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destinations.TODAY,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destinations.TODAY) { TodayScreen(taskState) }
            composable(Destinations.ALL_TASKS) { AllTasksScreen(taskState) }
            composable(Destinations.ADD_TASK) {
                AddTaskScreen(taskState) { navController.navigate(Destinations.TODAY) }
            }
            composable(Destinations.TIPS) { TipsScreen(taskState) }
        }
    }
}
