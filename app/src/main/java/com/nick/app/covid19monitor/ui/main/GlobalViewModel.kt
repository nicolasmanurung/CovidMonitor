package com.nick.app.covid19monitor.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick.app.covid19monitor.data.source.remote.response.GlobalMeninggal
import com.nick.app.covid19monitor.data.source.remote.response.GlobalPositif
import com.nick.app.covid19monitor.data.source.remote.response.GlobalSembuh
import com.nick.app.covid19monitor.utils.ApiClient
import retrofit2.Call
import retrofit2.Response

class GlobalViewModel : ViewModel() {
    private var globalPositif = MutableLiveData<GlobalPositif>()
    private var globalSembuh = MutableLiveData<GlobalSembuh>()
    private var globalMeninggal = MutableLiveData<GlobalMeninggal>()

    init {
        getGlobalPositif()
        getGlobalSembuh()
        getGlobalMeninggal()
    }

    private fun getGlobalPositif() {
        ApiClient().getServiceKawal().getPositifGlobal()
            .enqueue(object : retrofit2.Callback<GlobalPositif> {
                override fun onFailure(call: Call<GlobalPositif>, t: Throwable) {
                    globalPositif.value = null
                }

                override fun onResponse(
                    call: Call<GlobalPositif>,
                    response: Response<GlobalPositif>
                ) {
                    if (response.isSuccessful) {
                        globalPositif.value = response.body()
                    } else {
                        globalPositif.value = null
                    }
                }
            })
    }

    private fun getGlobalSembuh() {
        ApiClient().getServiceKawal().getSembuhGlobal()
            .enqueue(object : retrofit2.Callback<GlobalSembuh> {
                override fun onFailure(call: Call<GlobalSembuh>, t: Throwable) {
                    globalSembuh.value = null
                }

                override fun onResponse(
                    call: Call<GlobalSembuh>,
                    response: Response<GlobalSembuh>
                ) {
                    if (response.isSuccessful) {
                        globalSembuh.value = response.body()
                    } else {
                        globalSembuh.value = null
                    }
                }
            })
    }


    private fun getGlobalMeninggal() {
        ApiClient().getServiceKawal().getMeninggalGlobal()
            .enqueue(object : retrofit2.Callback<GlobalMeninggal> {
                override fun onFailure(call: Call<GlobalMeninggal>, t: Throwable) {
                    globalMeninggal.value = null
                }

                override fun onResponse(
                    call: Call<GlobalMeninggal>,
                    response: Response<GlobalMeninggal>
                ) {
                    if (response.isSuccessful) {
                        globalMeninggal.value = response.body()
                    } else {
                        globalMeninggal.value = null
                    }
                }
            })
    }


    fun setGlobalPositif(): MutableLiveData<GlobalPositif> {
        return globalPositif
    }

    fun setGlobalSembuh(): MutableLiveData<GlobalSembuh> {
        return globalSembuh
    }

    fun setGlobalMeninggal(): MutableLiveData<GlobalMeninggal> {
        return globalMeninggal
    }
}