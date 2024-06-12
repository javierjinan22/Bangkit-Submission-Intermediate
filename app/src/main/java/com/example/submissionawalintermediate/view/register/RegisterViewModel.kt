package com.example.submissionawalintermediate.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel(){

    val isLoading: LiveData<Boolean> = userRepository.isLoading
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.postSignUp(name, email, password)
                _registerResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _registerResponse.value =
                    RegisterResponse(error = true, message = e.message ?: "Unknown error")
            }
        }
    }
}