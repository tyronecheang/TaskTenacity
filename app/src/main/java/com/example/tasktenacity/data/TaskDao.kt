package com.example.tasktenacity.data

import androidx.room.*

// DAO (Data Access Object) for interacting with the tasks database
@Dao
interface TaskDao {
    // Retrieve all tasks, ordered by creation date (most recent first)
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): List<TaskEntity>

    // Retrieve tasks that are due today and not yet completed, ordered by creation date
    @Query("SELECT * FROM tasks WHERE deadline = :todayDate AND isCompleted = 0 ORDER BY createdAt ASC")
    fun getTodayTasks(todayDate: String): List<TaskEntity>

    // Insert a new task into the database; replace it if there's a conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEntity)

    // Update an existing task in the database
    @Update
    fun updateTask(task: TaskEntity)

    // Delete a task from the database
    @Delete
    fun deleteTask(task: TaskEntity)
}
