package com.example.mvvmsampleapp.retrofit

import com.example.mvvmsampleapp.model.UserDetailResponse
import com.example.mvvmsampleapp.model.UserResponse
import retrofit2.Response
import javax.inject.Inject


/**
 * class that extends with ApiHelper class to fetch the data
 * */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    // get users from api
    override suspend fun getUsers(page: Int): Response<UserResponse> {
        return apiService.getUsers(page)
    }

    override suspend fun getUsersDetails(id: Int): Response<UserDetailResponse> {
        return apiService.getUserDetails(id)
    }


}