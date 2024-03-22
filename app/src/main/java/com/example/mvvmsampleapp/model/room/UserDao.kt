package com.example.mvvmsampleapp.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserData(data: List<UserEntity>)

    @Query("SELECT * FROM userdata")
    fun getUserData(): LiveData<List<UserEntity>>

}