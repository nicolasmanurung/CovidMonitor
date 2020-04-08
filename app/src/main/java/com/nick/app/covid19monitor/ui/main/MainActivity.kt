package com.nick.app.covid19monitor.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.local.entity.Main
import com.nick.app.covid19monitor.data.source.remote.response.Article
import com.nick.app.covid19monitor.data.source.remote.response.IndonesiaResponseItem
import com.nick.app.covid19monitor.ui.chatbot.ChatbotActivity
import com.nick.app.covid19monitor.ui.detail.detailNegara.DetailNegaraActivity
import com.nick.app.covid19monitor.ui.detail.detailProvinsi.DetailProvinsiActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private val list: MutableList<Main> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private lateinit var vmGlobal: GlobalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGejalaImage.setHasFixedSize(true)

        val dataImage = resources.obtainTypedArray(R.array.gejalaImage)
        list.clear()
        for (i in 0 until dataImage.length()) {
            val main = Main(
                dataImage.getResourceId(i, 0)
            )
            list.add(main)
        }

        rvHeadlineNews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvHeadlineNews.setHasFixedSize(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            var answer: String = current.format(formatter)
            txtTanggalIndonesiaMain.text = answer
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val answer: String = formatter.format(date)
            txtTanggalIndonesiaMain.text = answer
        }

        if (checkInternetConnection()) {
            dataImage.recycle()
            showRecyclerImage()
            callIndonesia()
            callGlobalPositif()
            callGlobalSembuh()
            callGlobalMeninggal()
            callHeadlineNews()
        }

        lnrDataProvinsi.setOnClickListener {
            startActivity(Intent(this, DetailProvinsiActivity::class.java))
        }

        lnrDataNegara.setOnClickListener {
            startActivity(Intent(this, DetailNegaraActivity::class.java))
        }

        lnrCekGejala.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }

        lnrTelp.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:119")))
        }
    }


    private fun showRecyclerImage() {
        rvGejalaImage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listImageAdapter = MainAdapter(this, list)
        rvGejalaImage.adapter = listImageAdapter
    }

    private fun callIndonesia() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setDataIndonesia().observe(this, Observer { item ->
            showData(item)
        })
    }


    private fun showData(indonesia: List<IndonesiaResponseItem>) {
        txtSembuhIndonesiaMain.text = indonesia[0].sembuh
        txtMeninggalIndonesiaMain.text = indonesia[0].meninggal
        txtPositifIndonesiaMain.text = indonesia[0].positif
    }

    private fun callGlobalPositif() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalPositif().observe(this, Observer { positifData ->
            if (positifData != null) {
                lnrDataDunia.visibility = View.VISIBLE
                lnrDataIndonesia.visibility = View.VISIBLE
                skeltonBigMainOne.visibility = View.GONE
                skeltonBigMainTwo.visibility = View.GONE
                txtPositifDuniaMain.text = positifData.value
            } else {
                lnrDataDunia.visibility = View.GONE
                lnrDataIndonesia.visibility = View.GONE
                skeltonBigMainOne.visibility = View.VISIBLE
                skeltonBigMainTwo.visibility = View.VISIBLE
                txtPositifDuniaMain.text = "-"
            }
        })
    }

    private fun callGlobalSembuh() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalSembuh().observe(this, Observer { sembuhData ->
            if (sembuhData != null) {
                txtSembuhDuniaMain.text = sembuhData.value
            } else {
                txtSembuhDuniaMain.text = "-"
            }
        })
    }

    private fun callGlobalMeninggal() {
        vmGlobal = ViewModelProvider(this).get(GlobalViewModel::class.java)
        vmGlobal.setGlobalMeninggal().observe(this, Observer { meninggalData ->
            if (meninggalData != null) {
                txtMeninggalDuniaMain.text = meninggalData.value
            } else {
                txtMeninggalDuniaMain.text = "-"
            }
        })
    }

    private fun callHeadlineNews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setDataNews().observe(this, Observer { newsData ->
            showNews(newsData.articles)
            if (newsData != null) {
                rvHeadlineNews.visibility = View.VISIBLE
            } else {
                rvHeadlineNews.visibility = View.GONE
            }
        })
    }

    private fun checkInternetConnection(): Boolean {

        // get Connectivity Manager object to check connection
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
        // Check for network connections
        return if (isConnected) {
            true
        } else {
            Toast.makeText(this, "Tolong nyalain internetnya dong", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun showNews(articles: List<Article>) {
        rvHeadlineNews.adapter = MainNewsAdapter(articles)
    }
}
