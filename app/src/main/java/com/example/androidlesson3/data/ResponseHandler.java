package com.example.androidlesson3.data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseHandler<T> implements Callback<T> {

    abstract void onSuccess(T result);

    abstract void onFailure(Exception e);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                onSuccess(response.body());
            } else {
                onFailure(new Exception("Body is null"));
            }
        } else {
            onFailure(new Exception("Response code  " + response.code()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(new Exception(t));
    }
}
