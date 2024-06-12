package com.example.submissionawalintermediate.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.submissionawalintermediate.api.ApiService
import com.example.submissionawalintermediate.data.pref.UserModel
import com.example.submissionawalintermediate.data.pref.UserPreference

import kotlinx.coroutines.flow.Flow



class UserRepository private constructor(
    private val userPreference: UserPreference, private val apiService: ApiService
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun postSignUp(name: String, email: String, password: String) =
        apiService.postRegister(name, email, password)

    suspend fun postLogin(email: String, password: String) = apiService.postLogin(email, password)

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getUser() = userPreference.getSession().asLiveData()

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userPreference, apiService)
        }.also { instance = it }
    }
}
