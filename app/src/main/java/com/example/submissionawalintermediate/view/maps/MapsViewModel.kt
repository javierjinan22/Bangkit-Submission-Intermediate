package com.example.submissionawalintermediate.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionawalintermediate.data.StoryRepository
import com.example.submissionawalintermediate.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            try {
                val storyList = storyRepository.getStoriesWithLocation()
                _stories.postValue(storyList)
            } catch (e: Exception) {
                _error.postValue("Failed to fetch stories: ${e.message}")
            }
        }
    }
}