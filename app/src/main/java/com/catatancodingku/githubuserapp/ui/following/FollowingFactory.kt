package com.catatancodingku.githubuserapp.ui.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FollowingFactory(private val repository: FollowingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowingViewModel(repository) as T
    }
}