package com.nick.app.covid19monitor.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick.app.covid19monitor.data.source.remote.response.IndonesiaResponse
import com.nick.app.covid19monitor.data.source.remote.response.NewsResponse
import com.nick.app.covid19monitor.utils.ApiClient
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var indonesia = MutableLiveData<IndonesiaResponse>()
    private var news = MutableLiveData<NewsResponse>()

    init {
        getIndonesia()
        getNews()
    }

    private fun getIndonesia() {
        ApiClient().getServiceKawal().getIndonesia()
            .enqueue(object : retrofit2.Callback<IndonesiaResponse> {
                override fun onFailure(call: Call<IndonesiaResponse>, t: Throwable) {
                    indonesia.value = null
                }

                override fun onResponse(
                    call: Call<IndonesiaResponse>,
                    response: Response<IndonesiaResponse>
                ) {
                    if (response.isSuccessful) {
                        indonesia.value = response.body()
                    } else {
                        indonesia.value = null
                    }
                }
            })
    }

    private fun getNews() {
        ApiClient().getServiceNews().getHeadlineNews()
            .enqueue(object : retrofit2.Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    news.value = null
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        news.value = response.body()
                    } else {
                        news.value = null
                    }
                }
            })
    }


    fun setDataIndonesia(): MutableLiveData<IndonesiaResponse> {
        return indonesia
    }

    fun setDataNews(): MutableLiveData<NewsResponse> {
        return news
    }

}