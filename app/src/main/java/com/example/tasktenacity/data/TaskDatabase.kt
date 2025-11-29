package com.example.tasktenacity.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room database setup for storing TaskEntity objects
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    // Provides access to DAO methods
    abstract fun taskDao(): TaskDao

    companion object {
        // Ensures only one instance of the database exists at a time
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Returns the singleton instance of the database
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                // Build the Room database if it doesn't exist
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"  // Name of the database file
                )
                    .allowMainThreadQueries() // Allows queries on the main thread (useful for simplicity)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
