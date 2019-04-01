package com.example.qpony.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rates {

    @SerializedName("USD")
    @Expose
    val usd: Double? = null

    @SerializedName("AUD")
    @Expose
    val aud: Double? = null

    @SerializedName("CAD")
    @Expose
    val cad: Double? = null

    @SerializedName("PLN")
    @Expose
    val pln: Double? = null

    @SerializedName("MXN")
    @Expose
    val mxn: Double? = null

}
