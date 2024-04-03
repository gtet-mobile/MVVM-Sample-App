package com.example.mvvmsampleapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.viewmodel.UserDetailViewModel
import com.example.mvvmsampleapp.viewmodel.UserViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val database: AppDatabase,
    private val networkConnection: NetworkConnection
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(repository,database, networkConnection) as T
            }
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> {
                UserDetailViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}