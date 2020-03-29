package com.nick.app.covid19monitor.ui.detail.detailProvinsi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick.app.covid19monitor.data.source.remote.response.ProvinsiResponse
import com.nick.app.covid19monitor.utils.ApiClient
import retrofit2.Call
import retrofit2.Response

class ProvinsiViewModel : ViewModel() {
    private var provinsi = MutableLiveData<ProvinsiResponse>()

    init {
        getProvinsi()
    }

    private fun getProvinsi() {

        ApiClient().getService().getProvinsi()
            .enqueue(object : retrofit2.Callback<ProvinsiResponse> {
                override fun onFailure(call: Call<ProvinsiResponse>, t: Throwable) {
                    provinsi.value = null
                }

                override fun onResponse(
                    call: Call<ProvinsiResponse>,
                    response: Response<ProvinsiResponse>
                ) {
                    if (response.isSuccessful) {
                        provinsi.value = response.body()
                    } else {
                        provinsi.value = null
                    }
                }

            })
    }

    fun setData(): MutableLiveData<ProvinsiResponse> {
        return provinsi
    }

}