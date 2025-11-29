package com.example.tasktenacity.data

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): List<TaskEntity> = taskDao.getAllTasks()
    fun getTodayTasks(todayDate: String): List<TaskEntity> = taskDao.getTodayTasks(todayDate)
    fun insertTask(task: TaskEntity) = taskDao.insertTask(task)
    fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
    fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
}