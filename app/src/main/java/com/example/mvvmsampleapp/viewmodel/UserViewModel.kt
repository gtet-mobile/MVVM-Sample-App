package com.example.mvvmsampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.model.room.UserEntity
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.utils.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val database: AppDatabase
) : ViewModel() {
    private val _userResponse = MutableLiveData<List<UserEntity>>()
    val userResponse: LiveData<List<UserEntity>>
        get() = _userResponse

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    val pagingData: Flow<PagingData<Data>> = Pager(
        config = PagingConfig(pageSize = 1, enablePlaceholders = false),
        pagingSourceFactory = { UserPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    init {
        getDataFromDatabase()
    }


    fun insertPagingData(pagingData: PagingData<Data>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.appDao().insertUserData(convertPagingDataToList(pagingData))
            }
        }
    }

    private fun getDataFromDatabase(){
        viewModelScope.launch {
            _userResponse.postValue(database.appDao().getUserData().value)
        }
    }

    private fun convertPagingDataToList(pagingData: PagingData<Data>): List<UserEntity> {
        val list = mutableListOf<UserEntity>()
        pagingData.map { item ->
            list.add(UserEntity(id = item.id,
                email = item.email,
                lastName = item.lastName,
                firstName = item.firstName,
                avatar = item.avatar)
            )
        }

        return list.toList()
    }



    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

