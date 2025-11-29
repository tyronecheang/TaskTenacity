package com.example.tasktenacity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasktenacity.data.TaskEntity
import com.example.tasktenacity.data.TaskState
import com.example.tasktenacity.ui.components.TaskCard

// Displays all tasks and allows toggling completion or deleting a task
@Composable
fun AllTasksScreen(taskState: TaskState) {
    // Refresh tasks when the screen is first composed
    LaunchedEffect(Unit) { taskState.refreshData() }

    // State to track which task is being considered for deletion
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Screen title
        Text(
            "All Tasks",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 24.dp, top = 40.dp, bottom = 16.dp)
        )

        // LazyColumn to display the list of all tasks
        LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)) {
            items(taskState.allTasks) { task ->
                // Display each task using TaskCard
                TaskCard(
                    task = task,
                    onToggle = { taskState.toggleTaskCompletion(it) }, // Toggle completed state
                    onDeleteRequest = { taskToDelete = it } // Set task to be deleted
                )
            }
        }
    }

    // Show confirmation dialog if a task is marked for deletion
    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null }, // Dismiss dialog
            title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete \"${taskToDelete!!.title}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        taskState.deleteTask(taskToDelete!!) // Delete task from repository
                        taskToDelete = null // Reset the deletion state
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { taskToDelete = null }) { // Cancel deletion
                    Text("Cancel")
                }
            }
        )
    }
}
