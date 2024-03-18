package com.example.mvvmsampleapp.repository

import com.example.mvvmsampleapp.model.UserDetailResponse
import com.example.mvvmsampleapp.model.UserResponse
import com.example.mvvmsampleapp.retrofit.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers(page:Int): Response<UserResponse> {
        return apiHelper.getUsers(page)
    }

    suspend fun getUsersDetails(id:Int): Response<UserDetailResponse> {
        return apiHelper.getUsersDetails(id)
    }
}
