package com.example.androidlesson3.data;

import com.example.androidlesson3.models.BoredAction;

public  interface IBoredApiClient {

    void getBoredAction(
            RequestActionOptions requestOption,
            BoredActionCallback callback
    );

    interface BoredActionCallback extends CoreCallback <BoredAction>{
    }
}
