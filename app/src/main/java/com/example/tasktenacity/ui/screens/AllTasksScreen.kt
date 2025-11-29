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

@Composable
fun AllTasksScreen(taskState: TaskState) {
    LaunchedEffect(Unit) { taskState.refreshData() }

    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "All Tasks",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 24.dp, top = 40.dp, bottom = 16.dp)
        )

        LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)) {
            items(taskState.allTasks) { task ->
                TaskCard(
                    task = task,
                    onToggle = { taskState.toggleTaskCompletion(it) },
                    onDeleteRequest = { taskToDelete = it }
                )
            }
        }
    }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete \"${taskToDelete!!.title}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        taskState.deleteTask(taskToDelete!!)
                        taskToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { taskToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
