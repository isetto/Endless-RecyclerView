package com.example.qpony.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.Date

class Currencies {

    @SerializedName("date")
    @Expose
    val date: Date? = null

    @SerializedName("error")
    @Expose
    val error: ErrorType? = null

    @SerializedName("rates")
    @Expose
    val rates: Rates? = null

    @SerializedName("success")
    @Expose
    val success: Boolean? = null


}
