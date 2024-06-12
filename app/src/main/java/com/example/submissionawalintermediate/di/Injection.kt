package com.example.submissionawalintermediate.di

import android.content.Context

import com.example.submissionawalintermediate.api.ApiConfig
import com.example.submissionawalintermediate.data.StoryRepository
import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.data.database.StoryDatabase
import com.example.submissionawalintermediate.data.pref.UserPreference
import com.example.submissionawalintermediate.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getStoryApiService(user.token)
        val storyDatabase = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(apiService,storyDatabase)
    }
}