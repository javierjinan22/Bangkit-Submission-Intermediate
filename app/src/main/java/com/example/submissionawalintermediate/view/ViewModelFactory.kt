package com.example.submissionawalintermediate.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionawalintermediate.data.StoryRepository
import com.example.submissionawalintermediate.data.UserRepository
import com.example.submissionawalintermediate.di.Injection
import com.example.submissionawalintermediate.view.login.LoginViewModel
import com.example.submissionawalintermediate.view.main.MainViewModel
import com.example.submissionawalintermediate.view.maps.MapsViewModel
import com.example.submissionawalintermediate.view.register.RegisterViewModel
import com.example.submissionawalintermediate.view.upload.AddStoryViewModel

class ViewModelFactory(private val repository: UserRepository, private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, storyRepository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(storyRepository, repository) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            val userRepository = Injection.provideUserRepository(context)
            val storyRepository = Injection.provideStoryRepository(context)
            return ViewModelFactory(userRepository, storyRepository)
    }

}}