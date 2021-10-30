package com.catatancodingku.githubuserapp.network.response

import com.google.gson.annotations.SerializedName


data class UserResponse(
    var id : Long,
    @SerializedName("login")
    var username : String? = null,
    @SerializedName("avatar_url")
    var imageUser : String? = null
)