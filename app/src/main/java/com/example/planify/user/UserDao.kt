package com.example.planify.user


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface UserDao {

    @Insert
    fun insertUser(user:User)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUsersFlow(email: String, password: String): Flow<List<User>>

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Delete
    fun deleteUser(user: User)

    @Query("UPDATE user SET password = :newPassword WHERE email = :email")
    fun updateUserPassword(email: String, newPassword: String)


    @Query("DELETE FROM user WHERE email = :email")
    suspend fun deleteUserByUsername(email: String)


}


 */