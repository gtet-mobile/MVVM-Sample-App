package com.example.mvvmsampleapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserDetailResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("support")
    val support: Support
):Parcelable