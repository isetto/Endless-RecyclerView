package com.example.qpony.Network;

import android.content.Context;

public class ApiUtils {

    private static final String BASE_URL = "https://adrian.kubahaha.tk/";


    private ApiUtils() {
    }


    public static APIService getAPIService(Context context) {
        return RetrofitClient.getClient(BASE_URL, context).create(APIService.class);
    }


}