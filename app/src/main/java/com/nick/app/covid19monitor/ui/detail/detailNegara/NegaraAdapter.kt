package com.nick.app.covid19monitor.ui.detail.detailNegara

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.remote.response.NegaraResponseItem
import kotlinx.android.synthetic.main.item_negara.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NegaraAdapter(var attributes: List<NegaraResponseItem>) :
    RecyclerView.Adapter<NegaraAdapter.NegaraAdapterViewHolder>() {

    class NegaraAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: NegaraResponseItem) {
            itemView.txtNamaNegara.text = get.attributes.Country_Region
            itemView.txtMeninggalNegara.text = get.attributes.Deaths.toString()
            itemView.txtSembuhNegara.text = get.attributes.Recovered.toString()
            itemView.txtPositifNegara.text = get.attributes.Confirmed.toString()
            itemView.txtSuspectNegara.text = get.attributes.Active.toString()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                var answer: String = current.format(formatter)
                itemView.txtTanggalNegara.text = answer
            } else {
                var date = Date();
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val answer: String = formatter.format(date)
                itemView.txtTanggalNegara.text = answer
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NegaraAdapterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_negara, parent, false)
    )

    override fun getItemCount(): Int = attributes.size

    override fun onBindViewHolder(holder: NegaraAdapter.NegaraAdapterViewHolder, position: Int) {
        holder.bindView(attributes[position])
    }
}