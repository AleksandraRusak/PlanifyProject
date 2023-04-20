package com.example.planify.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.planify.Task

// Task Data Access Object (DAO) interface
@Dao
interface TaskDao {

    // Insert a new task into the database
    @Insert
    suspend fun addTask(task: Task)

    // Update an existing task in the database
    @Update
    suspend fun updateTask(task: Task)

    // Delete a task from the database
    @Delete
    suspend fun deleteTask(task: Task)

    // Delete all tasks from the database
    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    // Retrieve all tasks from the database, ordered by ID
    @Query("SELECT * FROM task_table")
    fun readAllData(): LiveData<List<Task>>

}