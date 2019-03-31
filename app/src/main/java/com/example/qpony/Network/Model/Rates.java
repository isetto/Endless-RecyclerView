package com.example.qpony.Network.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {

    @SerializedName("USD")
    @Expose
    private String USD;

    @SerializedName("AUD")
    @Expose
    private String AUD;

    @SerializedName("CAD")
    @Expose
    private String CAD;

    @SerializedName("PLN")
    @Expose
    private String PLN;

    public String getUSD() {
        return USD;
    }

    public String getAUD() {
        return AUD;
    }

    public String getCAD() {
        return CAD;
    }

    public String getPLN() {
        return PLN;
    }

    public String getMXN() {
        return MXN;
    }

    @SerializedName("MXN")
    @Expose
    private String MXN;

}
