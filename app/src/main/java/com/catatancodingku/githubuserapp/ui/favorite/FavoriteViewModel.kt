package com.catatancodingku.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catatancodingku.githubuserapp.room.Favorite

class FavoriteViewModel() : ViewModel() {

    private lateinit var repository : FavoriteRepository

    constructor(repository: FavoriteRepository) : this(){
        this.repository = repository
    }

    fun dbInstance(application: Application){
        repository.dbInstance(application)
    }

    fun insertFav(user : Favorite){
        repository.insert(user)
    }

    fun deleteFav(user : Favorite){
        repository.delete(user)
    }

    fun getUserFav(): LiveData<List<Favorite>>? {
        return repository.getFavoriteUser()
    }

    fun checkItemisExists(id : Long){
        repository.checkItemExists(id)
    }

    fun isItemExists(): LiveData<Boolean>? {
        return repository.isItemExists()
    }
}