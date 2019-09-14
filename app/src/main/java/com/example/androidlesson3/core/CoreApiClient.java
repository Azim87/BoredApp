package com.example.androidlesson3.core;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class CoreApiClient {
    protected Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl( baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
