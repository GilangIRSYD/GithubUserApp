package com.catatancodingku.githubuserapp.ui.detailUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catatancodingku.githubuserapp.network.ApiConfig
import com.catatancodingku.githubuserapp.network.response.DetailUserResponse
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.RepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRepository {

    private val detailUserLiveData = MutableLiveData<DetailUserResponse>()
    private val isLoading = MutableLiveData<Boolean>()

    fun callApiDetailUser(username : String){
        isLoading.value = true
        ApiConfig.instance.detailUserGithub(username).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    isLoading.value = false
                    detailUserLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun observeDetailUser(): LiveData<DetailUserResponse> {
        return detailUserLiveData
    }

    fun observeIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    val repositoryUserLiveData = MutableLiveData<GenericResponse<RepositoryResponse>>()

    fun callApiRepositoryUser(username : String){
        isLoading.value = true
        ApiConfig.instance.repositoryUser(username).enqueue(object : Callback<GenericResponse<RepositoryResponse>>{
            override fun onResponse(
                call: Call<GenericResponse<RepositoryResponse>>,
                response: Response<GenericResponse<RepositoryResponse>>
            ) {
                if (response.isSuccessful && response.body() != null){
                    isLoading.value = false
                    repositoryUserLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<GenericResponse<RepositoryResponse>>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun obseveRespoUser(): LiveData<GenericResponse<RepositoryResponse>> {
        return repositoryUserLiveData
    }
}