package com.catatancodingku.githubuserapp.ui.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse

class FollowerViewModel(private val repository: FollowerRepository) : ViewModel() {
    fun fetchUserFollower(username : String){
        repository.callApiUserFollower(username)
    }

    fun getUserFollowing(): LiveData<GenericResponse<UserResponse>> {
        return repository.observerUserFollower()
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}