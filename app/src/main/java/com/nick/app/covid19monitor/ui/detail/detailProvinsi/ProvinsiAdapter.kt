package com.nick.app.covid19monitor.ui.detail.detailProvinsi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.remote.response.Feature
import kotlinx.android.synthetic.main.item_provinsi.view.*

class ProvinsiAdapter(var features: List<Feature>) :
    RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder>() {

    class ProvinsiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Feature) {
            itemView.txtProvinsi.text = get.attributes.Provinsi
            itemView.txtMeninggal.text = get.attributes.Kasus_Meni.toString()
            itemView.txtPositif.text = get.attributes.Kasus_Posi.toString()
            itemView.txtSembuh.text = get.attributes.Kasus_Semb.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvinsiViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_provinsi, parent, false
        )
    )

    override fun getItemCount(): Int = features.size

    override fun onBindViewHolder(holder: ProvinsiViewHolder, position: Int) {
        holder.bindView(features[position])
    }
}