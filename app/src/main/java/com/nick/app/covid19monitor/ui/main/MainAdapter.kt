package com.nick.app.covid19monitor.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.local.entity.Main

class MainAdapter(private val context: Context, private val listImage: List<Main>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gejala, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.bind(listImage[position])
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgGejala: ImageView = itemView.findViewById(R.id.imageGejala)
        fun bind(item: Main) {
            Glide.with(itemView.context)
                .load(item.photo)
                .into(imgGejala)
        }
    }
}