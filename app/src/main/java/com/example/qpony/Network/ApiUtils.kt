package com.example.qpony.Network

import android.content.Context

object ApiUtils {

    private val BASE_URL = "https://adrian.kubahaha.tk/"


    fun getAPIService(context: Context): APIService {
        return RetrofitClient.getClient(BASE_URL, context).create(APIService::class.java)
    }


}