package com.catatancodingku.githubuserapp.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catatancodingku.githubuserapp.R
import com.catatancodingku.githubuserapp.databinding.ItemRecyclerRepositoryBinding
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.RepositoryResponse
import java.util.*

class RecyclerAdapterRepos : RecyclerView.Adapter<RecyclerAdapterRepos.viewHodler>() {
    private var data : GenericResponse<RepositoryResponse>? = null

    fun setData(data : GenericResponse<RepositoryResponse>){
        this.data = data
    }

    class viewHodler(private val binding : ItemRecyclerRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data : RepositoryResponse?) {
            binding.itemTitle.text = data?.name
            binding.itemDesc.text = data?.description

            checkLanguage(data?.language)
        }

        private fun checkLanguage(language: String?) {
            when (language?.toLowerCase(Locale.getDefault())){
                "java" -> {
                    binding.itemChip.apply {
                        setChipBackgroundColorResource(R.color.orange_tint)
                        text = language
                    }
                }

                "kotlin" -> {
                    binding.itemChip.apply {
                        setChipBackgroundColorResource(R.color.purple_tint)
                        text = language
                        binding.cardView.setCardBackgroundColor(resources.getColor(R.color.purple_tint))
                    }
                }

                "ruby" -> {
                    binding.itemChip.apply {
                        setChipBackgroundColorResource(R.color.red_tint)
                        text = language
                        binding.cardView.setCardBackgroundColor(resources.getColor(R.color.red_tint))
                    }
                }

                "javascript" -> {
                    binding.itemChip.apply {
                        setChipBackgroundColorResource(R.color.yellowe_tint)
                        text = language
                        binding.cardView.setCardBackgroundColor(resources.getColor(R.color.yellowe_tint))
                    }
                }

                null -> {
                    binding.itemChip.visibility = View.GONE
                }

                else -> {
                    binding.itemChip.apply {
                        setChipBackgroundColorResource(R.color.blue_tint)
                        text = language
                        binding.cardView.setCardBackgroundColor(resources.getColor(R.color.blue_tint))
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHodler {
        val binding = ItemRecyclerRepositoryBinding.inflate(LayoutInflater.from(parent.context)
        ,parent
        ,false)
        return viewHodler(binding)
    }

    override fun onBindViewHolder(holder: viewHodler, position: Int) {
        holder.bind(data?.get(position))
    }

    override fun getItemCount(): Int = data?.size ?: 0
}