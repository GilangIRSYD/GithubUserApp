package com.catatancodingku.githubuserapp.ui.detailUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catatancodingku.githubuserapp.network.response.DetailUserResponse
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.RepositoryResponse

class DetailViewModel() : ViewModel() {
    lateinit var repository: DetailRepository

    constructor(repository: DetailRepository) : this() {
        this.repository = repository
    }

    fun fetchDetailUser(username : String){
        repository.callApiDetailUser(username)
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return repository.observeDetailUser()
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.observeIsLoading()
    }

    fun fetchRepoUser(username : String){
        repository.callApiRepositoryUser(username)
    }

    fun getUserRepos(): LiveData<GenericResponse<RepositoryResponse>> {
        return repository.obseveRespoUser()
    }


}