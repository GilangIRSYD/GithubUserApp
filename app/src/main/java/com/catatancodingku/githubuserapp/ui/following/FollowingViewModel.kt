package com.catatancodingku.githubuserapp.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse

class FollowingViewModel(private val repository: FollowingRepository) : ViewModel() {

    fun fetchUserFollowing(username : String){
        repository.callApiUserFollowing(username)
    }

    fun getUserFollowing(): LiveData<GenericResponse<UserResponse>> {
        return repository.observerUserFollowing()
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}