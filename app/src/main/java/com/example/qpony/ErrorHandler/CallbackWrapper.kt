package com.example.ad.retrofittest.Common_Clases.ErrorHandler

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException


open class CallbackWrapper<T>(context: Context) : DisposableObserver<T>() {

    val ctx = context

     fun onSuccess(t: T) {}

     override fun onNext(response: T) {
        onSuccess(response)
    }

    override fun onError(e: Throwable) {
        Log.i("errorConn", e.message)
        Log.i("differentError0", e.localizedMessage)

        when (e) {
            is HttpException -> {
                Log.i("errorConn1", "dziala HttpException")
                val responseBody = e.response().errorBody()
                responseBody?.let {
                    try {
                        val statusString = e.response().errorBody()!!.string()
                        val status = JSONObject(statusString)
                        val response = e.response()


                        Log.e("errorConn2", statusString)
                    } catch (e1: IOException) {
                        Log.e("errorConn3", e1.toString())
                        e1.printStackTrace()
                    } catch (e1: JSONException) {
                        Log.e("errorConn4", e1.toString())
                        e1.printStackTrace()
                    }
                }
            }
            is SocketTimeoutException -> {
                Log.i("errorConn5", "dziala socket")
                val responseBody = e.stackTrace
                Log.i("errorConn5.5", responseBody.toString())
            }
            is IOException -> {
                Log.i("errorConn6", "dziala Io")
                if (e is com.example.qpony.Network.CheckInternetConn.NoConnectivityException) {
                    Toast.makeText(ctx, "nie masz internetu", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                e.message?.let {
                    Log.i("differentError1", e.toString())
                }
            }
        }
    }



    override fun onComplete() {

    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        val jsonObject = JSONObject(responseBody.string())
        return jsonObject.getString(("message"))
    }
}