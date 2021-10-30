package com.catatancodingku.githubuserapp.network.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    val id : Long,
    val name : String? = null,
    val login : String? = null,
    @SerializedName("avatar_url")
    val imageUser : String? = null,
    val followers : Int? = null,
    val following : Int? = null,
    @SerializedName("public_repos")
    val repositoryCount : Int? = null,
    val location : String? = null
)