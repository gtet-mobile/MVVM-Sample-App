package com.example.mvvmsampleapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("support")
    val support: Support,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
):Parcelable

@Entity(tableName = "UserData")
@Parcelize
data class Data(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("avatar")
    @ColumnInfo(name = "avatar") val avatar: String,
    @SerializedName("email")
    @ColumnInfo(name = "email") val email: String,
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name") val firstName: String,
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name") val lastName: String
):Parcelable

@Parcelize
data class Support(
    val text: String,
    val url: String
):Parcelable