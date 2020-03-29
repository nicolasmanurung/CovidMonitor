package com.nick.app.covid19monitor.ui.detail.detailNegara

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick.app.covid19monitor.data.source.remote.response.NegaraResponse
import com.nick.app.covid19monitor.utils.ApiClient
import retrofit2.Call
import retrofit2.Response


class NegaraViewModel : ViewModel() {
    private var negara = MutableLiveData<NegaraResponse>()

    init {
        getNegara()
    }

    private fun getNegara() {
        ApiClient().getServiceKawal().getNegara()
            .enqueue(object : retrofit2.Callback<NegaraResponse> {
                override fun onFailure(call: Call<NegaraResponse>, t: Throwable) {
                    negara.value = null
                }

                override fun onResponse(
                    call: Call<NegaraResponse>,
                    response: Response<NegaraResponse>
                ) {
                    if (response.isSuccessful) {
                        negara.value = response.body()
                    } else {
                        negara.value = null
                    }
                }
            })
    }

    fun setData(): MutableLiveData<NegaraResponse> {
        return negara
    }
}