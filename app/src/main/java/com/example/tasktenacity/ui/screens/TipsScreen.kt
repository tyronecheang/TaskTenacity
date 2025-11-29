package com.example.tasktenacity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tasktenacity.data.TaskState

// Screen that shows motivational productivity tips
@Composable
fun TipsScreen(taskState: TaskState) {
    // Fetch a quote when the screen is first displayed
    LaunchedEffect(Unit) { taskState.fetchQuote() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            "Productivity Tips",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f)) // Push content toward the center

        // Icon representing tips or insights
        Icon(Icons.Filled.Lightbulb, contentDescription = null, modifier = Modifier.size(72.dp))
        Spacer(Modifier.height(16.dp))

        // Subtitle
        Text("Actionable Insights", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        // Card displaying the current quote
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Text(
                taskState.quote, // Display fetched quote
                modifier = Modifier.padding(32.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(32.dp))

        // Button to fetch a new tip
        Button(onClick = { taskState.fetchQuote() }) {
            Icon(Icons.Filled.Refresh, null)
            Spacer(Modifier.width(8.dp))
            Text("Get New Tip")
        }

        Spacer(Modifier.weight(1f)) // Push content toward the center
    }
}
