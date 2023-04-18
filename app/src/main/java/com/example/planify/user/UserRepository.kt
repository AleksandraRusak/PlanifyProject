package com.example.planify.user


import com.example.planify.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class UserRepository (private val appDatabase : AppDatabase, private val
coroutineScope : CoroutineScope
) {
    fun insertUser(user: User) {
        appDatabase .userDao().insertUser(user)
    }
    fun getUsers(email: String, password: String): List<User> {
        return appDatabase .userDao().getAllUsers ()
    }
    fun performDatabaseOperation (dispatcher: CoroutineDispatcher,
                                  databaseOperation : suspend () -> Unit) {
        coroutineScope .launch(dispatcher) {
            databaseOperation ()
        }
    }

    fun getUsersFlow(email: String, password: String): Flow<List<User>> {
        return appDatabase.userDao().getUsersFlow(email, password)
    }

    suspend fun updateUserPassword(email: String, newPassword: String) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().updateUserPassword(email, newPassword)
        }
    }

    suspend fun deleteUserByUsername(email: String) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().deleteUserByUsername(email)
        }
    }
}