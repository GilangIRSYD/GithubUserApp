package com.catatancodingku.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catatancodingku.githubuserapp.R
import com.catatancodingku.githubuserapp.callback.AdapterOnClick
import com.catatancodingku.githubuserapp.callback.DatabaseCallback
import com.catatancodingku.githubuserapp.databinding.ItemRecyclerviewFavoriteBinding
import com.catatancodingku.githubuserapp.room.Favorite

class RecyclerAdapterFavorite : RecyclerView.Adapter<RecyclerAdapterFavorite.ViewHodler>() {

    private var data: List<Favorite>? = null
    private var setOnClick : AdapterOnClick<Favorite>? = null
    private var databaseCallback : DatabaseCallback<Favorite>? = null

    fun setData(data: List<Favorite>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClick(setOnClick: AdapterOnClick<Favorite>){
        this.setOnClick = setOnClick
    }

    fun setDatabaseCallback(databaseCallback: DatabaseCallback<Favorite>){
        this.databaseCallback = databaseCallback
    }

    class ViewHodler( val binding: ItemRecyclerviewFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Favorite?) {
            binding.itemUsername.text = item?.name
            Glide.with(binding.root.context)
                .load(item?.imageUrl)
                .transform(RoundedCorners(12))
                .error(R.mipmap.ic_launcher)
                .into(binding.itemImage)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        val binding = ItemRecyclerviewFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHodler(binding)
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        holder.onBind(data?.get(position))
        holder.itemView.setOnClickListener {
            setOnClick?.onItemClick(data?.get(position))
        }

        holder.binding.btnDelete.setOnClickListener {
            databaseCallback?.onDelete(data?.get(position)!!)
        }


    }

    override fun getItemCount(): Int = data?.size ?: 0
}