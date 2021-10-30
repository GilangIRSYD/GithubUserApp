package com.catatancodingku.githubuserapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    val id : Long,
    val name : String,
    val imageUrl : String
)