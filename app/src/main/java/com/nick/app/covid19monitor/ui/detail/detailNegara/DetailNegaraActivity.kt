package com.nick.app.covid19monitor.ui.detail.detailNegara

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.app.covid19monitor.data.source.remote.response.NegaraResponseItem
import com.nick.app.covid19monitor.databinding.ActivityDetailNegaraBinding
import kotlinx.android.synthetic.main.activity_detail_negara.*

class DetailNegaraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNegaraBinding
    private lateinit var viewModel: NegaraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNegaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNegara.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNegara.setHasFixedSize(true)

        val actionBar = supportActionBar
        actionBar?.title = "Data Covid"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8E9DC6")))

        callRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun callRecyclerView() {
        viewModel = ViewModelProvider(this).get(NegaraViewModel::class.java)
        viewModel.setData().observe(this, Observer { t ->
            showData(t)
            if (t != null) {
                rvNegara.visibility = View.VISIBLE
                topbarNegara.visibility = View.VISIBLE
                progressBarDetailNegara.visibility = View.GONE
            } else {
                rvNegara.visibility = View.GONE
                topbarNegara.visibility = View.GONE
                progressBarDetailNegara.visibility = View.VISIBLE
            }
        })
    }

    private fun showData(attributes: List<NegaraResponseItem>) {
        rvNegara.adapter = NegaraAdapter(attributes)
    }

}
