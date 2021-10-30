package com.catatancodingku.githubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catatancodingku.githubuserapp.network.ApiConfig
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.SearchResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    private val listUserLiveData = MutableLiveData<GenericResponse<UserResponse>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun apiCallUserGithub(){
        isLoading.value = true
        ApiConfig.instance.getGithubUser().enqueue(object : Callback<GenericResponse<UserResponse>>{
            override fun onResponse(
                call: Call<GenericResponse<UserResponse>>,
                response: Response<GenericResponse<UserResponse>>
            ) {
                if (response.isSuccessful && response.body() != null){
                    listUserLiveData.value = response.body()
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GenericResponse<UserResponse>>, t: Throwable) {
                isLoading.value  = false
            }
        })
    }

    fun observeListUser(): LiveData<GenericResponse<UserResponse>> {
        return listUserLiveData
    }

    private val searchUserLiveData = MutableLiveData<SearchResponse>()

    fun apiCallSearchUser(username : String){
        isLoading.value = true
        ApiConfig.instance.searchUserGithub(username).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    isLoading.value = false
                    val body = response.body()
                    searchUserLiveData.value = body
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun observeSearchUser(): LiveData<SearchResponse> {
        return searchUserLiveData
    }

    fun observeIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    private val keywordLiveData = MutableLiveData<String>()
    fun keywordSavedState(keyword : String){
        keywordLiveData.value = keyword
    }

    fun observeKeyword(): LiveData<String> {
        return keywordLiveData
    }
}