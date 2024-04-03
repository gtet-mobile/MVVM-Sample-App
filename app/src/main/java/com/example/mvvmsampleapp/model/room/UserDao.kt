package com.example.mvvmsampleapp.model.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmsampleapp.model.Data


@Dao
interface UserDao {

    @Query("SELECT * FROM userdata")
    fun getUsers() : PagingSource<Int, Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(result: List<Data>)

    @Query("DELETE FROM userdata")
    suspend fun clearUsers()

}