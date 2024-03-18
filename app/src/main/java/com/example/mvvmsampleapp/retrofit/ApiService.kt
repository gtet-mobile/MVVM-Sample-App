package com.example.mvvmsampleapp.retrofit

import com.example.mvvmsampleapp.model.UserDetailResponse
import com.example.mvvmsampleapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *api service class
 * */
interface ApiService {
    // get news from api
    @GET("users")
    suspend fun getUsers(@Query("page") page:Int): Response<UserResponse>

    @GET("users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: Int): Response<UserDetailResponse>

}