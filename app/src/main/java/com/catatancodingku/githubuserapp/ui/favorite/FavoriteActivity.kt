package com.catatancodingku.githubuserapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatancodingku.githubuserapp.MainFactory
import com.catatancodingku.githubuserapp.R
import com.catatancodingku.githubuserapp.adapter.RecyclerAdapterFavorite
import com.catatancodingku.githubuserapp.callback.AdapterOnClick
import com.catatancodingku.githubuserapp.callback.DatabaseCallback
import com.catatancodingku.githubuserapp.databinding.ActivityFavoriteBinding
import com.catatancodingku.githubuserapp.room.Favorite
import com.catatancodingku.githubuserapp.ui.detailUser.DetailUserActivity
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favAdapter : RecyclerAdapterFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = instaceViewModel()
        viewModel.dbInstance(application)
        favAdapter = RecyclerAdapterFavorite()

        onClickView()

        viewModel.getUserFav()?.observe(this, {
            Log.d("_cek", "roomdb: $it ")
            favAdapter.setData(it)
            updateUI()
        })

    }

    private fun onClickView() {
        favAdapter.setOnItemClick(onItemClick())
        favAdapter.setDatabaseCallback(databaseCallback())

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun databaseCallback(): DatabaseCallback<Favorite> {
        return object : DatabaseCallback<Favorite>{

            override fun onDelete(data: Favorite) {
                viewModel.deleteFav(data)
                Snackbar.make(binding.root,"Item was Delete",Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.insertFav(data)
                    }.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.orange_tint))
                    .show()
            }
        }
    }

    private fun onItemClick(): AdapterOnClick<Favorite> {
        return object  : AdapterOnClick<Favorite>{
            override fun onItemClick(data: Favorite?) {

                startActivity(
                    Intent(this@FavoriteActivity,DetailUserActivity::class.java)
                        .putExtra(DetailUserActivity.EXTRA_USERNAME,data?.name)
                )
            }
        }
    }

    private fun updateUI(){
        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = favAdapter

    }

    private fun instaceViewModel(): FavoriteViewModel {
        val favViewModel = FavoriteViewModel()
        val repo = FavoriteRepository()
        val factory = MainFactory(repo,favViewModel)
        return ViewModelProvider(viewModelStore,factory).get(FavoriteViewModel::class.java)
    }
}