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

class MainActivity : ComponentActivity() {
    private lateinit var taskState: TaskState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TaskDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(db.taskDao())
        taskState = TaskState(repository)

        setContent {
            TaskTenacityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskTenacityApp(taskState = taskState)
                }
            }
        }
    }
}