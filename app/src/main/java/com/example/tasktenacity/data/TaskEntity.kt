package com.example.tasktenacity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Defines a table in the Room database named "tasks"
@Entity(tableName = "tasks")
data class TaskEntity(
    // Primary key of the table, auto-generated
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Title or description of the task
    val title: String,

    // Category of the task (e.g., School, Work, Personal)
    val category: String,

    // Deadline for the task, stored as a String (format: yyyy-MM-dd)
    val deadline: String,

    // Boolean indicating whether the task has been completed
    val isCompleted: Boolean = false,

    // Timestamp of when the task was created, defaulting to current time
    val createdAt: Long = Date().time
)
