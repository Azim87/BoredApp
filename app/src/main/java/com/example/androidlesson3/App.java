package com.example.androidlesson3;

import android.app.Application;

import com.example.androidlesson3.data.BoredApiClient;

public class App extends Application {
    public static BoredApiClient boredApiClient;


    @Override
    public void onCreate() {
        super.onCreate();

        boredApiClient = new BoredApiClient();
    }
}
