package com.catatancodingku.githubuserapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.catatancodingku.githubuserapp.ui.follower.FollowerFragment
import com.catatancodingku.githubuserapp.ui.following.FollowingFragment
import com.catatancodingku.githubuserapp.ui.repository.RepositoryFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val username : String?) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {FollowerFragment.newInstance(username)}
            1->{RepositoryFragment.newInstance(username)}
            2 -> {FollowingFragment.newInstance(username)}
            else -> throw IllegalArgumentException("No registered Position tab : $position ")
        }
    }

}