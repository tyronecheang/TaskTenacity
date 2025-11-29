package com.example.tasktenacity.data

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): List<TaskEntity>
    @Query("SELECT * FROM tasks WHERE deadline = :todayDate AND isCompleted = 0 ORDER BY createdAt ASC")
    fun getTodayTasks(todayDate: String): List<TaskEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEntity)
    @Update
    fun updateTask(task: TaskEntity)
    @Delete
    fun deleteTask(task: TaskEntity)
}