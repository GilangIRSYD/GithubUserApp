package com.catatancodingku.githubuserapp.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAll() : LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite")
    fun selectAll() : Cursor

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id)")
    suspend fun isItemExists(id : Long): Boolean


    @Insert(onConflict = REPLACE)
    suspend fun insertFav(user : Favorite)

    @Delete
    suspend fun deleteFav(user : Favorite)

}
