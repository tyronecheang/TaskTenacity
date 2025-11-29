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
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("School") }
    var deadline by remember { mutableStateOf("") }
    val categories = listOf("School", "Work", "Personal", "Health")
    var showDatePicker by remember { mutableStateOf(false) }

    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcMillis: Long): Boolean {
            val todayCal = Calendar.getInstance()
            todayCal.set(Calendar.HOUR_OF_DAY, 0)
            todayCal.set(Calendar.MINUTE, 0)
            todayCal.set(Calendar.SECOND, 0)
            todayCal.set(Calendar.MILLISECOND, 0)
            val todayStartMillis = todayCal.timeInMillis

            val dateCal = Calendar.getInstance()
            dateCal.timeInMillis = utcMillis
            dateCal.set(Calendar.HOUR_OF_DAY, 0)
            dateCal.set(Calendar.MINUTE, 0)
            dateCal.set(Calendar.SECOND, 0)
            dateCal.set(Calendar.MILLISECOND, 0)

            return dateCal.timeInMillis >= todayStartMillis
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis,
        selectableDates = selectableDates
    )

    LaunchedEffect(Unit) {
        val today = Calendar.getInstance()
        deadline = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.time)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Text(
            "New Task",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("What needs to be done?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        Text("Category", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

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

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    taskState.addTask(
                        TaskEntity(
                            title = title,
                            category = category,
                            deadline = deadline
                        )
                    )
                    onSave()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Icon(Icons.Filled.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("Create Task")
        }
    }
}
