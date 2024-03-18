package com.example.mvvmsampleapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appDao(): UserDao
}

private lateinit var INSTANCE : AppDatabase

fun getDatabase(context: Context): AppDatabase {

    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database"
            ).build()
        }
    }

    return INSTANCE
}