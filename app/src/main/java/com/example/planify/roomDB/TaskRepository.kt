package com.example.planify.roomDB

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.planify.roomDB.Task
import com.example.planify.roomDB.TaskDao


class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTask() {
        taskDao.deleteAllTask()
    }

    @WorkerThread
    fun searchDatabase(searchQuery: String): LiveData<List<Task>> {
        return taskDao.searchDatabase(searchQuery)
    }
}