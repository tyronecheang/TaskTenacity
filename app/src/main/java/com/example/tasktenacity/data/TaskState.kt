package com.example.tasktenacity.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktenacity.data.quotes.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class TaskState(
    private val repository: TaskRepository,
    private val quoteRepo: QuoteRepository = QuoteRepository()
) : ViewModel() {

    // Current motivational quote to display in the UI
    var quote by mutableStateOf("Loading...")
        private set

    // All tasks in the database
    var allTasks by mutableStateOf(emptyList<TaskEntity>())
        private set

    // Tasks that are due today
    var todayTasks by mutableStateOf(emptyList<TaskEntity>())
        private set

    init {
        // Load tasks and fetch a quote when ViewModel is created
        refreshData()
        fetchQuote()
    }

    // Fetches a random quote from the QuoteRepository
    fun fetchQuote() {
        viewModelScope.launch {
            try {
                val q = withContext(Dispatchers.IO) { quoteRepo.loadQuote() }
                quote = "\"${q.content}\" — ${q.author}"
            } catch (e: Exception) {
                // Update quote with error message if fetch fails
                quote = "Could not load quote: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }

    // Refreshes all tasks and today's tasks from the database
    fun refreshData() {
        viewModelScope.launch {
            val all = withContext(Dispatchers.IO) { repository.getAllTasks() }
            allTasks = all

            val today = withContext(Dispatchers.IO) { repository.getTodayTasks(getTodayDateString()) }
            todayTasks = today
        }
    }

    // Adds a new task and refreshes the data
    fun addTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            refreshData()
        }
    }

    // Deletes a task and refreshes the data
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
            refreshData()
        }
    }

    // Toggles a task's completion status
    // If marking incomplete and the deadline is past, sets deadline to today
    fun toggleTaskCompletion(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            val finalTask = if (!updatedTask.isCompleted && isDeadlineInPast(updatedTask.deadline)) {
                updatedTask.copy(deadline = getTodayDateString())
            } else updatedTask

            repository.updateTask(finalTask)
            refreshData()
        }
    }

    // Returns today's date as a string in "yyyy-MM-dd" format
    private fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    // Checks if a given deadline is before today
    private fun isDeadlineInPast(deadline: String): Boolean {
        if (deadline.isBlank()) return false

        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val deadlineDate = sdf.parse(deadline)

            // Today's date without time components
            val today = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            deadlineDate != null && deadlineDate.before(today)
        } catch (_: Exception) {
            false
        }
    }
}
