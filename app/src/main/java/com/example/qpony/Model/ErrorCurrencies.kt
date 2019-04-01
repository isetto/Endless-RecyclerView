package com.example.qpony.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorCurrencies {

    @SerializedName("error")
    @Expose
    val error: ErrorType? = null
}
