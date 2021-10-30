package com.catatancodingku.githubuserapp.ui.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FollowerFactory(private val repository: FollowerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowerViewModel(repository) as T
    }
}