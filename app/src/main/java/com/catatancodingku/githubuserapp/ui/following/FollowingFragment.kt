package com.catatancodingku.githubuserapp.ui.following

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatancodingku.githubuserapp.adapter.RecyclerAdapterMain
import com.catatancodingku.githubuserapp.databinding.FragmentFollowingBinding

private const val ARG_PARAM1 = "param1"

class FollowingFragment : Fragment() {
    private var username: String? = null
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapeterFollowing : RecyclerAdapterMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }

        viewModel = initViewModel()
        adapeterFollowing = RecyclerAdapterMain()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchUserFollowing(
            username ?: throw IllegalArgumentException("User not passed : $username")
        )

        viewModel.getUserFollowing().observe(viewLifecycleOwner, {
            Log.d("Following", "onActivityCreated: $it ")
            adapeterFollowing.setData(it)
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
        binding.rvFollowing.apply {
            adapter = adapeterFollowing
            layoutManager = LinearLayoutManager(binding.root.context)
            setHasFixedSize(true)
        }
    }

    private fun initViewModel(): FollowingViewModel {
        val factory = FollowingFactory(repository = FollowingRepository())
        return ViewModelProvider(viewModelStore, factory).get(FollowingViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}