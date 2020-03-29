package com.nick.app.covid19monitor.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.local.entity.Main
import com.nick.app.covid19monitor.data.source.remote.response.IndonesiaResponseItem
import com.nick.app.covid19monitor.databinding.ActivityMainBinding
import com.nick.app.covid19monitor.ui.detail.detailNegara.DetailNegaraActivity
import com.nick.app.covid19monitor.ui.detail.detailProvinsi.DetailProvinsiActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list: MutableList<Main> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private lateinit var vmGlobal: GlobalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGejalaImage.setHasFixedSize(true)

        val dataImage = resources.obtainTypedArray(R.array.gejalaImage)
        list.clear()
        for (i in 0 until dataImage.length()) {
            val main = Main(
                dataImage.getResourceId(i, 0)
            )
            list.add(main)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            var answer: String = current.format(formatter)
            binding.txtTanggalIndonesiaMain.text = answer
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val answer: String = formatter.format(date)
            binding.txtTanggalIndonesiaMain.text = answer
        }

        dataImage.recycle()
        showRecyclerImage()
        callIndonesia()
        callGlobalPositif()
        callGlobalSembuh()
        callGlobalMeninggal()

        binding.lnrDataProvinsi.setOnClickListener {
            startActivity(Intent(this, DetailProvinsiActivity::class.java))
        }

        binding.lnrDataNegara.setOnClickListener {
            startActivity(Intent(this, DetailNegaraActivity::class.java))
        }
    }


    private fun showRecyclerImage() {
        binding.rvGejalaImage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listImageAdapter = MainAdapter(this, list)
        binding.rvGejalaImage.adapter = listImageAdapter
    }

    private fun callIndonesia() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setDataIndonesia().observe(this, Observer { item ->
            showData(item)
        })
    }


    private fun showData(indonesia: List<IndonesiaResponseItem>) {
        binding.txtSembuhIndonesiaMain.text = indonesia[0].sembuh
        binding.txtMeninggalIndonesiaMain.text = indonesia[0].meninggal
        binding.txtPositifIndonesiaMain.text = indonesia[0].positif
    }

    private fun callGlobalPositif() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalPositif().observe(this, Observer { positifData ->
            if (positifData != null) {
                binding.lnrDataDunia.visibility = View.VISIBLE
                binding.lnrDataIndonesia.visibility = View.VISIBLE
                binding.skeltonBigMainOne.root.visibility = View.GONE
                binding.skeltonBigMainTwo.root.visibility = View.GONE
                binding.txtPositifDuniaMain.text = positifData.value
            } else {
                binding.lnrDataDunia.visibility = View.GONE
                binding.lnrDataIndonesia.visibility = View.GONE
                binding.skeltonBigMainOne.root.visibility = View.VISIBLE
                binding.skeltonBigMainTwo.root.visibility = View.VISIBLE
                binding.txtPositifDuniaMain.text = "-"
            }
        })
    }

    private fun callGlobalSembuh() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalSembuh().observe(this, Observer { sembuhData ->
            if (sembuhData != null) {
                binding.txtSembuhDuniaMain.text = sembuhData.value
            } else {
                binding.txtSembuhDuniaMain.text = "-"
            }
        })
    }

    private fun callGlobalMeninggal() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalMeninggal().observe(this, Observer { meninggalData ->
            if (meninggalData != null) {
                binding.txtMeninggalDuniaMain.text = meninggalData.value
            } else {
                binding.txtMeninggalDuniaMain.text = "-"
            }
        })
    }
}
