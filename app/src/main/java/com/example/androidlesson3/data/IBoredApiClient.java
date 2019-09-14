package com.example.androidlesson3.data;

import com.example.androidlesson3.models.BoredAction;
import com.example.androidlesson3.models.RequestActionOptions;

public  interface IBoredApiClient {
    void getBoredAction(
            BoredActionCallback callback
    );
    interface BoredActionCallback extends CoreCallback <BoredAction>{
    }
}
