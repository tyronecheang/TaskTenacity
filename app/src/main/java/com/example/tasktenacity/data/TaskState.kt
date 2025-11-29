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

    var quote by mutableStateOf("Loading...")
        private set

    var allTasks by mutableStateOf(emptyList<TaskEntity>())
        private set

    var todayTasks by mutableStateOf(emptyList<TaskEntity>())
        private set

    init {
        refreshData()
        fetchQuote()
    }

    fun fetchQuote() {
        viewModelScope.launch {
            try {
                val q = withContext(Dispatchers.IO) { quoteRepo.loadQuote() }
                quote = "\"${q.content}\" — ${q.author}"
            } catch (e: Exception) {
                quote = "Could not load quote: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }



    fun refreshData() {
        viewModelScope.launch {
            val all = withContext(Dispatchers.IO) { repository.getAllTasks() }
            allTasks = all
            val today = withContext(Dispatchers.IO) { repository.getTodayTasks(getTodayDateString()) }
            todayTasks = today
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            refreshData()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
            refreshData()
        }
    }

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

    private fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    private fun isDeadlineInPast(deadline: String): Boolean {
        if (deadline.isBlank()) return false
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val deadlineDate = sdf.parse(deadline)
            val today = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            deadlineDate != null && deadlineDate.before(today)
        } catch (e: Exception) {
            false
        }
    }
}
