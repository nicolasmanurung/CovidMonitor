package com.nick.app.covid19monitor.ui.detail.detailProvinsi

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.app.covid19monitor.data.source.remote.response.Feature

import com.nick.app.covid19monitor.databinding.ActivityDetailProvinsiBinding
import kotlinx.android.synthetic.main.activity_detail_provinsi.*
import kotlinx.android.synthetic.main.custom_progressbar.*

class DetailProvinsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProvinsiBinding
    private lateinit var viewModel: ProvinsiViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProvinsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProvinsi.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProvinsi.setHasFixedSize(true)

        val actionbar = supportActionBar
        actionbar?.title = "Data Covid"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8E9DC6")))

        callRecyclerview()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun callRecyclerview() {
        viewModel = ViewModelProvider(this).get(ProvinsiViewModel::class.java)
        viewModel.setData().observe(this, Observer { t ->
            t?.features?.let { showData(it) }
            if (t != null) {
                rvProvinsi.visibility = View.VISIBLE
                topbarProvinsi.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            } else {
                rvProvinsi.visibility = View.GONE
                topbarProvinsi.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun showData(features: List<Feature>) {
        rvProvinsi.adapter = ProvinsiAdapter(features)
    }
}
