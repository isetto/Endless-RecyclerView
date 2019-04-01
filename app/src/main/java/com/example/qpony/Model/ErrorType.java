package com.example.qpony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorType {

    public String getType() {
        return type;
    }

    @SerializedName("type")
    @Expose
    private String type;

}
