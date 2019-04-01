package com.example.qpony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Currencies {

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("error")
    @Expose
    private ErrorType error;

    public ErrorType getError() {
        return error;
    }

    public Date getDate() {
        return date;
    }

    public Rates getRates() {
        return rates;
    }

    public Boolean getSuccess() {
        return success;
    }

    @SerializedName("rates")
    @Expose
    private Rates rates;

    @SerializedName("success")
    @Expose
    private Boolean success;


}
