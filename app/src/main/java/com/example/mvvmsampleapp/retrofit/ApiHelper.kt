package com.example.mvvmsampleapp.retrofit

import com.example.mvvmsampleapp.model.UserDetailResponse
import com.example.mvvmsampleapp.model.UserResponse
import retrofit2.Response

/**
 *interface to fetch data using retrofit
 * */
interface ApiHelper {
    // get users from api
    suspend fun getUsers(page:Int): Response<UserResponse>
    suspend fun getUsersDetails(id:Int): Response<UserDetailResponse>
}