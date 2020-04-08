package com.nick.app.covid19monitor.utils

import com.nick.app.covid19monitor.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    private fun getInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    //Retrofit for Access API BNPB
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Retrofit for Access API KawalCorona

    private fun getRetrofitInstanceKawal(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_NEGARA)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Retrofit for Access API News
    private fun getRetrofitInstanceNews(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL_NEWS)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getService(): GetService {
        return getRetrofitInstance().create(GetService::class.java)
    }

    fun getServiceKawal(): GetService {
        return getRetrofitInstanceKawal().create(GetService::class.java)
    }

    fun getServiceNews(): GetService {
        return getRetrofitInstanceNews().create(GetService::class.java)
    }
}