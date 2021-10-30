package com.catatancodingku.githubuserapp.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catatancodingku.githubuserapp.network.ApiConfig
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingRepository {
    private val datasetUserFollowing = MutableLiveData<GenericResponse<UserResponse>>()
    val isLoading = MutableLiveData<Boolean>()

    fun callApiUserFollowing(username : String){
        isLoading.value = true
        ApiConfig.instance.listFollowing(username).enqueue(object : Callback<GenericResponse<UserResponse>>{
            override fun onResponse(
                call: Call<GenericResponse<UserResponse>>,
                response: Response<GenericResponse<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    datasetUserFollowing.value = response.body()
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GenericResponse<UserResponse>>, t: Throwable) {
                Log.e("RESPONSE", "onFailure: ${t.localizedMessage}" )
                isLoading.value = false
            }
        })
    }

    fun observerUserFollowing(): LiveData<GenericResponse<UserResponse>> {
        return datasetUserFollowing
    }

}