package com.catatancodingku.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.catatancodingku.githubuserapp.room.FavoriteDao
import com.catatancodingku.githubuserapp.room.FavoriteDatabase

class ContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        favoriteDao =  FavoriteDatabase.getInstance(context!!)!!.favoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor : Cursor? = null

        when (uriMatcher.match(uri)){
            FAVORITE -> {
                Log.d("_cek", "Uri Matcher: FAVORITE, URI : $uri ")
                cursor = favoriteDao.selectAll()
                cursor.setNotificationUri(context!!.contentResolver, uri)
            }
            else -> throw IllegalArgumentException("No Uri Match: $uri")
        }

        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return  0
    }

    companion object {
        const val AUTHORITY = "com.catatancodingku.githubuserapp.provider"
        const val TABLE_NAME = "favorite"
        const val FAVORITE = 1

        private lateinit var favoriteDao: FavoriteDao

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
        }
    }
}
