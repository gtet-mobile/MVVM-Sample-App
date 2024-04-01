package com.example.mvvmsampleapp.utils

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.repository.UserRepository

class UserPagingSource(private val repository: UserRepository,private val database: AppDatabase) : PagingSource<Int, Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val nextPageNumber = params.key ?: 0
            Log.d(TAG,"asdfghjk $params")
            val response = repository.getUsers(nextPageNumber)
            Log.d(TAG,"asdfghj $response")
            val data = response.body()?.data ?: emptyList()
            database.appDao().insertUsers(data)
            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        // The key for refreshing data is typically the key of the first item
        // In this case, if the first page is available, return its key
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}