package com.catatancodingku.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatancodingku.githubuserapp.adapter.RecyclerAdapterMain
import com.catatancodingku.githubuserapp.callback.AdapterOnClick
import com.catatancodingku.githubuserapp.callback.AlarmCallback
import com.catatancodingku.githubuserapp.databinding.ActivityMainBinding
import com.catatancodingku.githubuserapp.dialog.CustomDialog
import com.catatancodingku.githubuserapp.network.response.UserResponse
import com.catatancodingku.githubuserapp.ui.detailUser.DetailUserActivity
import com.catatancodingku.githubuserapp.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private var adapterMain: RecyclerAdapterMain? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = initViewModel()
        adapterMain = RecyclerAdapterMain()
        adapterMain?.setOnItemClick(onClick())
        alarmReceiver = AlarmReceiver()
        CustomDialog.setAlarmCallback(onAlarmCallback())


        if (viewModel.getKeywordSaved().value != null) {
            binding.searchView.editText?.apply {
                setText(viewModel.getKeywordSaved().value)
            }
            viewModel.fetchApiSearchUser(viewModel.getKeywordSaved().value!!)
            Log.d("search", "onRotate: ${viewModel.getKeywordSaved().value} ")
        } else {
            viewModel.fetchApiListUser()
        }

        buttonUI()


        showUserGithub()
        searchUserGithub()
        stateUI()


        Log.d("cek_alarm", "Alrm is active: ${AlarmReceiver.isAlarmSet(this)} ")



    }

    private fun onAlarmCallback(): AlarmCallback {
        return object : AlarmCallback{

            override fun onAlarmTurnON() {
                alarmReceiver.setAlarm(this@MainActivity)
                Log.d("cek_alarm", "onAlarmTurnON: ")
            }

            override fun onAlarmCencel() {
                alarmReceiver.cencelAlarm(this@MainActivity)
            }
        }
    }


    private fun buttonUI() {
        binding.fabFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        binding.btnReminder.setOnClickListener {

            val dialog = CustomDialog()
            dialog.show(supportFragmentManager, "dialog")
        }
    }

    private fun searchUserGithub() {
        emptyState(false)
        binding.searchView.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.trim().isNullOrEmpty()) {
                viewModel.fetchApiListUser()
                emptyState(false)
            } else {
                viewModel.setKeywordSaved(text.toString())
                viewModel.fetchApiSearchUser(text.toString())
            }
        }
    }

    private fun showUserGithub() {
        viewModel.getUserGithub().observe(this, {
            adapterMain?.setData(it)
        })

        viewModel.getUserSearch().observe(this, {
            Log.d("search", "showUserGithub: ${it.total_count}")
            adapterMain?.setData(it.items)
            if (it.total_count == 0) {
                emptyState(true)
            } else {
                emptyState(false)
            }
        })

        updateUI()
    }

    private fun emptyState(isState: Boolean) {
        if (isState) {
            binding.icNotFound.visibility = View.VISIBLE
            binding.tvEmptyUser.visibility = View.VISIBLE
            binding.tvUsernotfound.visibility = View.VISIBLE
        } else {
            binding.icNotFound.visibility = View.GONE
            binding.tvEmptyUser.visibility = View.GONE
            binding.tvUsernotfound.visibility = View.GONE
        }
    }

    private fun stateUI() {
        viewModel.isLoading().observe(this, {
            if (it) {
                binding.progressbarMain.visibility = View.VISIBLE
            } else {
                binding.progressbarMain.visibility = View.INVISIBLE
            }
        })

    }

    private fun onClick(): AdapterOnClick<UserResponse> {
        return object : AdapterOnClick<UserResponse> {
            override fun onItemClick(data: UserResponse?) {
                val toDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                toDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, data?.username)
                startActivity(toDetail)
            }

        }
    }

    private fun updateUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapterMain
    }

    private fun initViewModel(): MainViewModel {
        val repository = MainRepository()
        val viewModelMain = MainViewModel()
        val factory = MainFactory(repository, viewModelMain)
        return ViewModelProvider(viewModelStore, factory).get(MainViewModel::class.java)
    }



}