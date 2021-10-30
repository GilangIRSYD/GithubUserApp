package com.catatancodingku.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catatancodingku.favorite.room.Favorite
import kotlinx.android.synthetic.main.item_recyclerview_favorite.view.*

class FavoriteRecyclerAdapter :
    RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {

    private var listUser: List<Favorite>? = null
    private var onItemClickCallback : OnItemClickCallback? = null

    fun setListUser(listUser : List<Favorite>){
        this.listUser = listUser
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.item_image
        val username = itemView.item_username

        fun bind(item: Favorite) {
            username.text = item.name
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .transform(RoundedCorners(12))
                .error(R.mipmap.ic_launcher)
                .into(image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser?.get(position)!!)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onClick(listUser!!.get(position))
        }
    }

    override fun getItemCount(): Int = listUser?.size ?: 0

}

interface OnItemClickCallback{
    fun onClick(item : Favorite)
}