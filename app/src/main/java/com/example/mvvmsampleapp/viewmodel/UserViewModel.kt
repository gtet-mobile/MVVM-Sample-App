package com.example.mvvmsampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.model.room.UserEntity
import com.example.mvvmsampleapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val database: AppDatabase
) : ViewModel() {
    private val _userResponse = MutableLiveData<List<Data>>()
    val userResponse: LiveData<List<Data>>
        get() = _userResponse

    var page= MutableStateFlow(1)


    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    init {
        viewModelScope.launch {
            getNews()
        }
    }


    private suspend fun getNews() {
        try {
            val response = repository.getUsers(page.value)
            if (response.isSuccessful) {
                val response = response.body()
                if (response != null) {
                    if (page.value <response.totalPages){
                        page.value++
                    }
                    response.data.forEach {
                        insertData(UserEntity(
                            id=it.id,
                            avatar = it.avatar,
                            email = it.email,
                            firstName = it.firstName,
                            lastName = it.lastName
                        ))
                    }
                    _userResponse.postValue(response.data)
                } else {
                    _errorData.postValue("Response body is null")
                }
            } else {
                _errorData.postValue(
                    "Request failed with code ${response.code()}"
                )
            }
        } catch (e: IOException) {
            _errorData.postValue("Network error: ${e.message}")
        } catch (e: Exception) {
            _errorData.postValue("An error occurred: ${e.message}")
        }
    }



    private suspend fun getDataFromDatabase() {
        withContext(Dispatchers.IO) {
            val data = database.appDao().getUserData()
        }
    }

    private suspend fun insertData(data: UserEntity) {
        withContext(Dispatchers.IO) {
            database.appDao().insertUserData(data)
        }
    }

      override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

