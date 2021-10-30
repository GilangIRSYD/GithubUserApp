package com.catatancodingku.githubuserapp.ui.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatancodingku.githubuserapp.adapter.RecyclerAdapterMain
import com.catatancodingku.githubuserapp.databinding.FragmentFollowerBinding

private const val ARG_PARAM1 = "param1"

class FollowerFragment : Fragment() {
    private var username: String? = null
    private lateinit var binding :  FragmentFollowerBinding
    private lateinit var adapterFollower : RecyclerAdapterMain
    private lateinit var viewModel : FollowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }

        adapterFollower = RecyclerAdapterMain()
        viewModel = initViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getListFollower()
    }

    private fun getListFollower() {
        viewModel.fetchUserFollower(username ?: throw IllegalArgumentException("User not pass | user : $username"))
        viewModel.getUserFollowing().observe(viewLifecycleOwner,{
            adapterFollower.setData(it)
            updateUI()
        })

        viewModel.isLoading().observe(viewLifecycleOwner,{ state ->
            if (state){
                binding.progressbar.visibility = View.VISIBLE
            }else{
                binding.progressbar.visibility = View.GONE
            }
        })
    }

    private fun updateUI() {
        binding.rvFollower.apply {
            adapter = adapterFollower
            layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    private fun initViewModel(): FollowerViewModel {
        val factory = FollowerFactory(repository = FollowerRepository())
        return ViewModelProvider(viewModelStore,factory).get(FollowerViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String?) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}