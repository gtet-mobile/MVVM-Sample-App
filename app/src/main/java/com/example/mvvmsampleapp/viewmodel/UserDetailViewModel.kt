package com.example.mvvmsampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userDetailResponse = MutableLiveData<Data>()
    val userDetailResponse: LiveData<Data>
        get() = _userDetailResponse

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    fun getUserDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getUsersDetails(id)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetailResponse.postValue(responseBody.data)
                    } else {
                        _errorData.postValue("Response body is null")
                    }
                } else {
                    _errorData.postValue("Request failed with code ${response.code()}")
                }
            } catch (e: IOException) {
                _errorData.postValue("Network error: ${e.message}")
            } catch (e: Exception) {
                _errorData.postValue("An error occurred: ${e.message}")
            }
        }
    }
}
