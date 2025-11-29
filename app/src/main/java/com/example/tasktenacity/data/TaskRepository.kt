package com.example.tasktenacity.data

// Repository that provides an abstraction layer over the TaskDao
// Handles CRUD operations for TaskEntity objects
class TaskRepository(private val taskDao: TaskDao) {
    // Retrieves all tasks from the database
    fun getAllTasks(): List<TaskEntity> = taskDao.getAllTasks()

    // Retrieves only today's tasks (based on the provided date string)
    fun getTodayTasks(todayDate: String): List<TaskEntity> = taskDao.getTodayTasks(todayDate)

    // Inserts a new task into the database
    fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    // Updates an existing task in the database
    fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    // Deletes a task from the database
    fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
}
