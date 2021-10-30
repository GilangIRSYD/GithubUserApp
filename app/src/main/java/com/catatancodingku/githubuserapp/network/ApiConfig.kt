package com.catatancodingku.githubuserapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL : String = "https://api.github.com/"
    const val TOKEN : String = "token 5ccb01a20d5a54cec25d8d94dcb84a6b8fc91737"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(Routes::class.java)
}