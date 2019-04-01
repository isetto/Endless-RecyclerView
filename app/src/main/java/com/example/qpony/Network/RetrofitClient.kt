package com.example.qpony.Network


import android.content.Context

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.qpony.Network.CheckInternetConn.ConnectivityInterceptor

/**
 * Created by ad on 2018-01-16.
 */

object RetrofitClient {

    private var retrofit: Retrofit? = null


    fun getClient(baseUrl: String, context: Context): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()

        val dispatcher = Dispatcher()
        httpClient.dispatcher(dispatcher)


        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(ConnectivityInterceptor(context))
                .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                            .create()
                    ))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}

