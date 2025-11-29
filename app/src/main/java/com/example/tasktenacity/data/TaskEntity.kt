package com.example.tasktenacity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String,
    val deadline: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = Date().time
)