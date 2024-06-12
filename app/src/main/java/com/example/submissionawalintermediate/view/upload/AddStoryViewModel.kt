package com.example.submissionawalintermediate.view.upload

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionawalintermediate.data.StoryRepository
import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.data.pref.UserModel
import com.example.submissionawalintermediate.response.AddStoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddStoryViewModel( private val repository: StoryRepository, private val userRepository: UserRepository): ViewModel(){

    private val _addStoryResponse = MutableLiveData<AddStoryResponse>()
    val addStoryResponse: LiveData<AddStoryResponse> = _addStoryResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addStory(
        multipartBody: MultipartBody.Part,
        description: RequestBody,
        lat: Float? = null,
        lon: Float? = null
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.addStory(multipartBody, description, lat, lon)
                _addStoryResponse.postValue(response)
                _isLoading.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, AddStoryResponse::class.java)
                val errorMessage = errorBody.message

                _isLoading.postValue(false)
                _addStoryResponse.postValue(errorBody)

                Log.d(TAG, "Upload File Error: $errorMessage")
            }
        }
    }

    fun getUser(): LiveData<UserModel> {
        return userRepository.getUser()
    }
}