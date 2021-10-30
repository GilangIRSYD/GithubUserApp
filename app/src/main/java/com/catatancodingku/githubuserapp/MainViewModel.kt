package com.catatancodingku.githubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.SearchResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse

class MainViewModel() : ViewModel() {

    private lateinit var repository: MainRepository

    constructor(repository: MainRepository) : this(){
        this.repository = repository
    }

    fun fetchApiListUser(){
        repository.apiCallUserGithub()
    }

    fun getUserGithub(): LiveData<GenericResponse<UserResponse>> {
        return repository.observeListUser()
    }

    fun fetchApiSearchUser(username : String){
        repository.apiCallSearchUser(username)
    }

    fun getUserSearch(): LiveData<SearchResponse> {
        return repository.observeSearchUser()
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.observeIsLoading()
    }

    fun setKeywordSaved(keyword : String){
        repository.keywordSavedState(keyword)
    }

    fun getKeywordSaved(): LiveData<String> {
        return repository.observeKeyword()
    }

}