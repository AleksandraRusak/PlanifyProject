package com.example.planify.user

import android.app.Application
import androidx.lifecycle.*
import com.example.planify.AppDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(AppDatabase.getInstance(application), viewModelScope)
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.getUsersFlow(email, password).collect { userList ->
                val isUserValid = userList.any { it.email == email && it.password == password }
                _isLoggedIn.postValue(isUserValid)
            }
        }
    }

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.insertUser(User(email, password))
        }
        _isLoggedIn.postValue(true)
    }

    fun logoutUser() {
        _isLoggedIn.postValue(false)

    }

}