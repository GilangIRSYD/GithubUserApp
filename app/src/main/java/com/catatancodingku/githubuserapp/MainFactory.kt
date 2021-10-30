package com.catatancodingku.githubuserapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.catatancodingku.githubuserapp.ui.detailUser.DetailRepository
import com.catatancodingku.githubuserapp.ui.detailUser.DetailViewModel
import com.catatancodingku.githubuserapp.ui.favorite.FavoriteRepository
import com.catatancodingku.githubuserapp.ui.favorite.FavoriteViewModel

@Suppress("UNCHECKED_CAST")
class MainFactory<R, VM>(private val repository: R, private val viewModel: VM) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (viewModel) {
            is MainViewModel -> MainViewModel(repository as MainRepository) as T
            is DetailViewModel -> DetailViewModel(repository as DetailRepository) as T
            is FavoriteViewModel -> FavoriteViewModel(repository as FavoriteRepository) as T
            else -> viewModel as T
        }
    }
}