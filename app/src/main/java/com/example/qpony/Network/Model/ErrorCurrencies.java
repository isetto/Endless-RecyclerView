package com.example.qpony.Network.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorCurrencies {

    @SerializedName("error")
    @Expose
    private ErrorType error;

    public ErrorType getError() {
        return error;
    }
}
