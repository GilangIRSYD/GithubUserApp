package com.catatancodingku.githubuserapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if (instance == null) {
                synchronized(FavoriteDatabase::class) {
                    instance =
                        Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "favorite.db")
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

}