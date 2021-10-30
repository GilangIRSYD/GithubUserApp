package com.catatancodingku.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catatancodingku.githubuserapp.R
import com.catatancodingku.githubuserapp.callback.AdapterOnClick
import com.catatancodingku.githubuserapp.databinding.ItemRecyclerviewUserBinding
import com.catatancodingku.githubuserapp.network.response.GenericResponse
import com.catatancodingku.githubuserapp.network.response.UserResponse

class RecyclerAdapterMain : RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder>() {

    private var data: GenericResponse<UserResponse>? = null
    private var onItemClickCallback: AdapterOnClick<UserResponse>? = null

    fun setData(data: GenericResponse<UserResponse>?) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClickCallback: AdapterOnClick<UserResponse>) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemRecyclerviewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserResponse?) {
            Glide.with(binding.root.context)
                .load(data?.imageUser)
                .transform(RoundedCorners(12))
                .error(R.mipmap.ic_launcher)
                .into(binding.itemImage)

            binding.itemUsername.text = data?.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data?.get(position))
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClick(data?.get(holder.adapterPosition))
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0


}