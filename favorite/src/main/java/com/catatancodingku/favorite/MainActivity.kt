package com.catatancodingku.favorite

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catatancodingku.favorite.helper.MappingHelper
import com.catatancodingku.favorite.room.Favorite


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FavoriteRecyclerAdapter()
        adapter.setOnItemClickCallback(onItemClickCallback())
        val rvFavorite = findViewById<RecyclerView>(R.id.rv_fav)

        Thread {
            fetchProvider()
        }.start()

        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = LinearLayoutManager(this)

    }

    private fun onItemClickCallback(): OnItemClickCallback {
        return object : OnItemClickCallback {
            override fun onClick(item: Favorite) {
                Toast.makeText(this@MainActivity, "hello ${item.name}", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun fetchProvider() {

        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        val data = MappingHelper.mapCursorToArrayList(cursor)

        Log.d("_cek", "tester: $data ")
        adapter.setListUser(data)
    }

    companion object {
        const val AUTHORITY = "com.catatancodingku.githubuserapp.provider"
        const val SCHEME = "content"
        const val TABLE_NAME = "favorite"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

//        val URI_USER_FAV: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}
