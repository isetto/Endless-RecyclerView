package com.example.qpony.Network;


import android.content.Context;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.qpony.Network.CheckInternetConn.ConnectivityInterceptor;

/**
 * Created by ad on 2018-01-16.
 */

public class RetrofitClient {

	private static Retrofit retrofit = null;




	public static Retrofit getClient(String baseUrl, Context context) {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

		Dispatcher dispatcher=new Dispatcher();
		httpClient.dispatcher(dispatcher);


		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.addInterceptor(new ConnectivityInterceptor(context))
				.build();

		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.client(client)
					.baseUrl(baseUrl)
					.addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
							.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
							.create()
					))
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return retrofit;
	}
}

