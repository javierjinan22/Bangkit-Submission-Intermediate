package com.example.submissionawalintermediate.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.data.pref.UserModel
import com.example.submissionawalintermediate.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.postLogin(email, password)
                _loginResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _loginResponse.value =
                    LoginResponse(error = true, message = e.message ?: "Unknown error")
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}