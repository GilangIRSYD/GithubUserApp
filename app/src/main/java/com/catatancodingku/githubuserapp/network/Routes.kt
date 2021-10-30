package com.catatancodingku.githubuserapp.network

import com.catatancodingku.githubuserapp.network.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Routes {

    @GET("users")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun getGithubUser() : Call<GenericResponse<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun searchUserGithub(
        @Query("q")
        username : String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun detailUserGithub(
        @Path("username")
        username : String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun repositoryUser(
        @Path("username")
        username : String
    ) : Call<GenericResponse<RepositoryResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun listFollowing(
        @Path("username") username : String
    ) : Call<GenericResponse<UserResponse>>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${ApiConfig.TOKEN}")
    fun listFollowers(
        @Path("username") username : String
    ) : Call<GenericResponse<UserResponse>>


}