package com.example.qpony.Network.CheckInternetConn

import android.content.Context


import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ConnectivityInterceptor(private val mContext: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isOnline(mContext)) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


}
