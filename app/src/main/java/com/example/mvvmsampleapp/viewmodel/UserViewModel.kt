package com.example.mvvmsampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.utils.NetworkConnection
import com.example.mvvmsampleapp.utils.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val database: AppDatabase,
    networkConnection: NetworkConnection
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingData: Flow<PagingData<Data>> = networkConnection.isConnected
        .flatMapLatest { isConnected ->
            if (isConnected) {
                Pager(
                    config = PagingConfig(pageSize = 1, enablePlaceholders = false),
                    pagingSourceFactory = { UserPagingSource(repository, database) }
                ).flow
            } else {
                Pager(
                    config = PagingConfig(pageSize = 1, enablePlaceholders = false),
                    pagingSourceFactory = {
                        database.appDao().getUsers()
                    }
                ).flow
            }
        }.cachedIn(viewModelScope)

}

