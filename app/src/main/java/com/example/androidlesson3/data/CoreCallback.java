package com.example.androidlesson3.data;

public interface CoreCallback <T>{
    void onSuccess(T action);
    void onFailure(Exception e);
}
