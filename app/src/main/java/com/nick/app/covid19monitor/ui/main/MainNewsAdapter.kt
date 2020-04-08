package com.nick.app.covid19monitor.ui.main

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.remote.response.Article
import kotlinx.android.synthetic.main.item_news.view.*

class MainNewsAdapter(var articles: List<Article>) :
    RecyclerView.Adapter<MainNewsAdapter.MainNewsViewHolder>() {

    class MainNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(get: Article) {
            itemView.txtJudulBerita.text = get.title
            Glide.with(itemView.imgListBerita)
                .load(get.urlToImage)
                .into(itemView.imgListBerita)

            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(get.url)))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainNewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: MainNewsViewHolder, position: Int) {
        holder.bindView(articles[position])
    }


}