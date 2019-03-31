package com.example.qpony.Network.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ErrorType {

    public String getType() {
        return type;
    }

    @SerializedName("type")
    @Expose
    private String type;

}
