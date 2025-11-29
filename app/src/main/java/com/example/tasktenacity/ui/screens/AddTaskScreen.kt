package com.example.tasktenacity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasktenacity.data.TaskEntity
import com.example.tasktenacity.data.TaskState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTaskScreen(taskState: TaskState, onSave: () -> Unit) {
    // State variables for the input fields
    var title by remember { mutableStateOf("") } // Task title
    var category by remember { mutableStateOf("School") } // Selected category
    var deadline by remember { mutableStateOf("") } // Task deadline
    val categories = listOf("School", "Work", "Personal", "Health") // Available categories
    var showDatePicker by remember { mutableStateOf(false) } // Controls date picker visibility

    // Logic to allow only future or today's dates to be selected
    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val todayUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val selectedUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = utcTimeMillis
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            return !selectedUtc.before(todayUtc)
        }
    }

    // Date picker state initialization with today as default
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis,
        selectableDates = selectableDates
    )

    // Initialize deadline to today's date on first composition
    LaunchedEffect(Unit) {
        val today = Calendar.getInstance()
        deadline = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.time)
    }

    // Display the DatePickerDialog if showDatePicker is true
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    // Set deadline to the selected date in yyyy-MM-dd format
                    datePickerState.selectedDateMillis?.let { millis ->
                        deadline = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }.format(Date(millis))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState, modifier = Modifier.defaultMinSize(minHeight = 360.dp))
        }
    }

    // Main layout column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        // Screen header
        Text(
            "New Task",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Spacer(Modifier.height(24.dp))

        // Title input field
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("What needs to be done?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        // Category selection label
        Text("Category", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // Row of filter chips for selecting category
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                FilterChip(
                    selected = category == cat,
                    onClick = { category = cat },
                    label = { Text(cat) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Deadline field (read-only) with calendar icon to show date picker
        OutlinedTextField(
            value = deadline,
            onValueChange = {},
            readOnly = true,
            label = { Text("Due Date") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        // Save task button
        Button(
            onClick = {
                // Only add task if title is not empty
                if (title.isNotBlank()) {
                    taskState.addTask(
                        TaskEntity(
                            title = title,
                            category = category,
                            deadline = deadline
                        )
                    )
                    onSave() // Call callback after saving
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(Icons.Filled.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("Create Task")
        }
    }
}
