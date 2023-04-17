package com.example.planify.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE task LIKE :searchQuery OR title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Task>>

}