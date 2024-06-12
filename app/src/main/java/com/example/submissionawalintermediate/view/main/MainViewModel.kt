package com.example.submissionawalintermediate.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionawalintermediate.data.StoryRepository
import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.data.pref.UserModel
import com.example.submissionawalintermediate.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {
    private val _userSession = MutableLiveData<UserModel>()
    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> = _isLoading
    val storyResponse: LiveData<PagingData<ListStoryItem>> by lazy {
        storyRepository.getStories().cachedIn(viewModelScope)
    }



    init {
        loadUserSession()
    }

    private fun loadUserSession() {
        viewModelScope.launch {
            repository.getSession().collect { userModel ->
                _userSession.value = userModel
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
