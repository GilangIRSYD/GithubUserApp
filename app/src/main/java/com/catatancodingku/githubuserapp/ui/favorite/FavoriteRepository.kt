package com.catatancodingku.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catatancodingku.githubuserapp.room.Favorite
import com.catatancodingku.githubuserapp.room.FavoriteDao
import com.catatancodingku.githubuserapp.room.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteRepository {

    private var favoDao : FavoriteDao? = null
    private var favoriteUser : LiveData<List<Favorite>>? = null
    private var isItemExists = MutableLiveData<Boolean>()


    fun dbInstance(application: Application){
        val db = FavoriteDatabase.getInstance(application.applicationContext)
        favoDao = db?.favoriteDao()!!
        favoriteUser = favoDao?.getAll()
    }

    fun getFavoriteUser(): LiveData<List<Favorite>>? {
        return favoriteUser
    }

    fun insert(user : Favorite) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                favoDao?.insertFav(user)
            }
        }
    }

    fun delete(user : Favorite) = runBlocking{
        this.launch(Dispatchers.IO) {
            favoDao?.deleteFav(user)
        }
    }

    fun checkItemExists(id : Long){
        runBlocking {
            this.launch(Dispatchers.IO) {
                isItemExists.postValue(favoDao?.isItemExists(id))
            }
        }
    }

    fun isItemExists(): LiveData<Boolean>? {
        return isItemExists
    }
}