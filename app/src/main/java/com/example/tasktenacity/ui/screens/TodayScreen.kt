package com.example.tasktenacity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasktenacity.data.TaskState
import com.example.tasktenacity.ui.components.TaskCard

// Screen showing tasks due today
@Composable
fun TodayScreen(taskState: TaskState) {
    // Refresh tasks when the screen is first displayed
    LaunchedEffect(Unit) { taskState.refreshData() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Screen title
        Text(
            "Today's Focus",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 24.dp, top = 40.dp, bottom = 16.dp)
        )

        // Show placeholder if there are no tasks for today
        if (taskState.todayTasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Mood, contentDescription = null, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("No pending tasks. Great job!")
                }
            }
        } else {
            // Display today's tasks in a scrollable list
            LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)) {
                items(taskState.todayTasks) { task ->
                    TaskCard(
                        task = task,
                        onToggle = { taskState.toggleTaskCompletion(it) } // Toggle completion on click
                    )
                }
            }
        }
    }
}
