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

// Defines all navigation destinations for the app
object Destinations {
    const val TODAY = "today"
    const val ALL_TASKS = "all_tasks"
    const val ADD_TASK = "add_task"
    const val TIPS = "tips"
}

@Composable
fun TaskTenacityApp(taskState: TaskState) {
    // NavController manages navigation between screens
    val navController = rememberNavController()

    // Scaffold provides the basic material layout structure (top bars, bottom bars, etc.)
    Scaffold(
        bottomBar = { BottomNav(navController) } // Add the bottom navigation bar
    ) { paddingValues ->

        // NavHost holds the composable destinations and handles navigation
        NavHost(
            navController = navController,
            startDestination = Destinations.TODAY, // Default screen
            modifier = Modifier.padding(paddingValues) // Respect padding from Scaffold
        ) {
            // Compose each screen and pass the taskState ViewModel

            // "Today" screen showing today's tasks
            composable(Destinations.TODAY) { TodayScreen(taskState) }

            // "All Tasks" screen showing all tasks
            composable(Destinations.ALL_TASKS) { AllTasksScreen(taskState) }

            // "Add Task" screen to create a new task
            composable(Destinations.ADD_TASK) {
                AddTaskScreen(taskState) {
                    // Navigate back to "Today" after adding a task
                    navController.navigate(Destinations.TODAY)
                }
            }

            // "Tips" screen showing motivational tips
            composable(Destinations.TIPS) { TipsScreen(taskState) }
        }
    }
}
