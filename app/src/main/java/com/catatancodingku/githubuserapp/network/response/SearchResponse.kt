package com.catatancodingku.githubuserapp.network.response

data class SearchResponse(
    val total_count : Int? = null,
    val items : GenericResponse<UserResponse>? = null
)