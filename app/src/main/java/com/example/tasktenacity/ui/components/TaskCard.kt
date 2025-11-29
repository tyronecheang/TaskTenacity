package com.example.tasktenacity.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.tasktenacity.data.TaskEntity

@Composable
fun TaskCard(
    task: TaskEntity, // The task data to display
    onToggle: (TaskEntity) -> Unit, // Callback when task completion status is toggled
    onDeleteRequest: ((TaskEntity) -> Unit)? = null // Optional callback for deleting the task
) {
    // Animate the background color depending on whether the task is completed
    val backgroundColor by animateColorAsState(
        targetValue = if (task.isCompleted)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.surface,
        label = "TaskCardBackgroundColor"
    )

    // Animate the transparency of the card for completed tasks
    val taskAlpha by animateFloatAsState(
        targetValue = if (task.isCompleted) 0.6f else 1f,
        label = "TaskCardAlpha"
    )

    // Set text color based on task completion
    val textColor =
        if (task.isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        else MaterialTheme.colorScheme.onSurface

    // The main card container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .alpha(taskAlpha), // Apply alpha animation
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Column containing task title and additional info
            Column(
                modifier = Modifier
                    .weight(1f) // Take remaining horizontal space
                    .clickable { onToggle(task) } // Toggle completion when clicked
            ) {
                // Task title
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Spacer(Modifier.height(6.dp))

                // Row for category tag and deadline
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Task category displayed in a colored surface
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = task.category,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Spacer(Modifier.width(12.dp))

                    // Task deadline
                    Text(
                        text = if (task.deadline.isNotBlank()) "Due: ${task.deadline}" else "No Deadline",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Row for action buttons: delete and toggle completion
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                // Optional delete button
                if (onDeleteRequest != null) {
                    IconButton(onClick = { onDeleteRequest(task) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Task",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                // Completion toggle button
                IconButton(onClick = { onToggle(task) }) {
                    Icon(
                        imageVector = if (task.isCompleted)
                            Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                        contentDescription = "Toggle Task Status",
                        modifier = Modifier.size(30.dp),
                        tint = if (task.isCompleted)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
