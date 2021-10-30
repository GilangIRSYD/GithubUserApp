package com.catatancodingku.githubuserapp.ui.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatancodingku.githubuserapp.MainFactory
import com.catatancodingku.githubuserapp.adapter.RecyclerAdapterRepos
import com.catatancodingku.githubuserapp.databinding.FragmentRepositoryBinding
import com.catatancodingku.githubuserapp.ui.detailUser.DetailRepository
import com.catatancodingku.githubuserapp.ui.detailUser.DetailViewModel

private const val ARG_PARAM1 = "param1"

class RepositoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var username: String? = null
    private lateinit var binding : FragmentRepositoryBinding
    private lateinit var adapterRepos : RecyclerAdapterRepos
    private lateinit var viewModelRepos : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }
        adapterRepos = RecyclerAdapterRepos()
        viewModelRepos = initViewModel()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepositoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getListReposUser()

    }

    private fun getListReposUser() {

        viewModelRepos.fetchRepoUser(username?: throw IllegalArgumentException("Username not passed : $username"))

        viewModelRepos.getUserRepos().observe(viewLifecycleOwner, {
            Log.d("reposList", "getListReposUser: $it")
            adapterRepos.setData(it)
            updateUI()
        })

        viewModelRepos.isLoading().observe(viewLifecycleOwner,{ state ->
            if (state){
                binding.progressbar.visibility = View.VISIBLE
            }else{
                binding.progressbar.visibility = View.GONE
            }
        })
    }

    private fun updateUI() {
        binding.rvRepos.apply {
            adapter = adapterRepos
            layoutManager = LinearLayoutManager(binding.root.context)
            setHasFixedSize(true)
        }
    }

    private fun initViewModel(): DetailViewModel {
        val factory = MainFactory(repository = DetailRepository(),viewModel = DetailViewModel())
        return ViewModelProvider(viewModelStore,factory).get(DetailViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?) =
            RepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}