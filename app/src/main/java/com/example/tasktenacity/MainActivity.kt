package com.example.tasktenacity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tasktenacity.data.TaskDatabase
import com.example.tasktenacity.data.TaskRepository
import com.example.tasktenacity.data.TaskState
import com.example.tasktenacity.ui.theme.TaskTenacityTheme

// Main entry point for the app
class MainActivity : ComponentActivity() {

    // ViewModel managing tasks and quotes
    private lateinit var taskState: TaskState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database and repository
        val db = TaskDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(db.taskDao())

        // Initialize the TaskState ViewModel
        taskState = TaskState(repository)

        // Set the UI content using Jetpack Compose
        setContent {
            // Apply the app theme
            TaskTenacityTheme {
                // Surface provides the background for the app
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Start the main composable of the app and pass in the ViewModel
                    TaskTenacityApp(taskState = taskState)
                }
            }
        }
    }
}
