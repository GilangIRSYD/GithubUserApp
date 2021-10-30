package com.catatancodingku.githubuserapp.ui.detailUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catatancodingku.githubuserapp.MainFactory
import com.catatancodingku.githubuserapp.R
import com.catatancodingku.githubuserapp.adapter.ViewPagerAdapter
import com.catatancodingku.githubuserapp.databinding.ActivityDetailUserBinding
import com.catatancodingku.githubuserapp.room.Favorite
import com.catatancodingku.githubuserapp.ui.favorite.FavoriteRepository
import com.catatancodingku.githubuserapp.ui.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelFavorite: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = initViewModel()
        viewModelFavorite = initViewModelFavorite()
        viewModelFavorite.dbInstance(application)

        buttonUI()
        val userSelected = intent.getStringExtra(EXTRA_USERNAME)!!
        showDetailUser(userSelected)

        //viewPager adapter
        binding.viewpagerUserDetail.adapter = ViewPagerAdapter(this,userSelected)
        attachTabLayout()


    }

    private fun attachTabLayout() {
        val tabLayoutManager =
            TabLayoutMediator(binding.tabUserDetail, binding.viewpagerUserDetail) { tab, position ->

                binding.tabUserDetail.selectTab(binding.tabUserDetail.getTabAt(1))
                when(position){
                    0 -> tab.text = "Followers"
                    1 -> tab.text = "Repository"
                    2 -> tab.text = "Following"
                }
            }

        tabLayoutManager.attach()
    }


    private fun showDetailUser(username: String) {
        viewModel.fetchDetailUser(username)
        viewModel.fetchRepoUser(username)

        viewModel.getUserDetail().observe(this, {
            viewModelFavorite.checkItemisExists(it.id)
            binding.detailFollower.text = it.followers.toString()
            binding.detailFollowing.text = it.following.toString()
            binding.detailRepos.text = it.repositoryCount.toString()
            binding.detailUsername.text = it.name ?: it.login
            binding.detailLocation.text = it.location ?: "Penduduk Bumi"
            Glide.with(this)
                .load(it.imageUser)
                .error(R.mipmap.ic_launcher)
                .transform(RoundedCorners(12))
                .into(binding.detailImage)

            buttonFav(it.id, it.login!!, it.imageUser!!)


        })

    }

    private fun buttonFav(id: Long, login: String, imageUser: String) {

        val userFavorite = Favorite(id, login, imageUser)

        //switch image favorite
        viewModelFavorite.isItemExists()?.observe(this, {

            if (it) {
                binding.btnFav.setImageResource(R.drawable.ic_favorited)
            } else {
                binding.btnFav.setImageResource(R.drawable.ic_unfavorite)
            }
        })

        //button fav system
        binding.btnFav.setOnClickListener { view ->
            if (viewModelFavorite.isItemExists()?.value!!) {
                Snackbar.make(binding.root, "Remove from Favorite", Snackbar.LENGTH_SHORT).show()
                viewModelFavorite.dbInstance(application)
                viewModelFavorite.deleteFav(userFavorite)
                viewModelFavorite.checkItemisExists(id)
            } else {
                Snackbar.make(binding.root, "Add to Favorite", Snackbar.LENGTH_SHORT).show()
                viewModelFavorite.dbInstance(application)
                viewModelFavorite.insertFav(userFavorite)
                viewModelFavorite.checkItemisExists(id)
            }
        }


    }


    private fun buttonUI() {
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun initViewModel(): DetailViewModel {
        val repos = DetailRepository()
        val detailVM = DetailViewModel()
        val factory = MainFactory(repos, detailVM)
        return ViewModelProvider(viewModelStore, factory).get(DetailViewModel::class.java)
    }

    private fun initViewModelFavorite(): FavoriteViewModel {
        val favViewModel = FavoriteViewModel()
        val repo = FavoriteRepository()
        val factory = MainFactory(repo, favViewModel)
        return ViewModelProvider(viewModelStore, factory).get(FavoriteViewModel::class.java)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}