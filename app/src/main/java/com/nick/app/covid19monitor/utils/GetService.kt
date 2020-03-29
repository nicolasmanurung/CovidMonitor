package com.nick.app.covid19monitor.utils

import com.nick.app.covid19monitor.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET

interface GetService {

    @GET(Constans.API_PROVINSI)
    fun getProvinsi(): Call<ProvinsiResponse>

    @GET(Constans.API_NEGARA)
    fun getNegara(): Call<NegaraResponse>

    @GET(Constans.API_INDONESIA)
    fun getIndonesia(): Call<IndonesiaResponse>

    @GET(Constans.API_GLOBAL_MENINGGAL)
    fun getMeninggalGlobal(): Call<GlobalMeninggal>

    @GET(Constans.API_GLOBAL_POSITIF)
    fun getPositifGlobal(): Call<GlobalPositif>

    @GET(Constans.API_GLOBAL_SEMBUH)
    fun getSembuhGlobal(): Call<GlobalSembuh>


}