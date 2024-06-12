package com.example.submissionawalintermediate.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionawalintermediate.api.ApiService
import com.example.submissionawalintermediate.data.database.StoryDatabase
import com.example.submissionawalintermediate.data.database.StoryRemoteMediator
import com.example.submissionawalintermediate.response.ListStoryItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase

){
     fun getStories(): LiveData<PagingData<ListStoryItem>>{
         @OptIn(ExperimentalPagingApi::class)
         return Pager(
             config = PagingConfig(
                 pageSize = 5
             ),
             remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
             pagingSourceFactory = {
                 StoryPagingSource(apiService)
                 storyDatabase.storyDao().getAllStories()
             }
         ).liveData
    }

    suspend fun getStoriesWithLocation(): List<ListStoryItem> {
        val response = apiService.getStoriesWithLocation()
        return if (!response.error) {
            response.listStory ?: emptyList()
        } else {
            throw Exception("Error fetching location stories: ${response.message}")
        }
    }

    suspend fun addStory(
        multipartBody: MultipartBody.Part,
        description: RequestBody,
        lat: Float? = null,
        lon: Float? = null
    ) = apiService.addStory(multipartBody, description, lat, lon)

    companion object {
        fun getInstance(apiService: ApiService, storyDatabase: StoryDatabase): StoryRepository {
            return StoryRepository(apiService,storyDatabase)
        }
    }
}