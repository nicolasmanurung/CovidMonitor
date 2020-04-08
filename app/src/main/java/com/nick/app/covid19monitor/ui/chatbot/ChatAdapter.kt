package com.nick.app.covid19monitor.ui.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.remote.response.Message
import java.util.*

class ChatAdapter(private val messageArrayList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val SELF = 100
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        // view type is to identify where to render the chat message
        // left or right
        itemView = if (viewType == SELF) {
            // self message
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item_self, parent, false)
        } else {
            // WatBot message
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item_watson, parent, false)
        }
        return ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageArrayList[position]
        return if (message.id != null && message.id.equals("1")) {
            SELF
        } else position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageArrayList[position]
        when (message.type) {
            Message.Type.TEXT -> (holder as ViewHolder).message?.text = message.message
            Message.Type.IMAGE -> {
                (holder as ViewHolder).message?.visibility = View.GONE
                val iv = holder.image
                if (iv != null) {
                    Glide
                        .with(iv.context)
                        .load(message.url)
                        .into(iv)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messageArrayList.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var message: TextView? = null
        var image: ImageView? = null

        init {
            message = itemView.findViewById(R.id.message)
            image = itemView.findViewById(R.id.image)
        }
    }

}