package com.example.planify.roomDB

import androidx.lifecycle.LiveData
import com.example.planify.Task

// Task repository for handling database operations
class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()

    // Add a task to the database
    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    // Update a task in the database
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    // Delete a task from the database
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    // Delete all tasks from the database
    suspend fun deleteAllTask() {
        taskDao.deleteAllTask()
    }

}
