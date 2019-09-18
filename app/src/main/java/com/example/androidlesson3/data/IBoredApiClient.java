package com.example.androidlesson3.data;

import com.example.androidlesson3.models.ActionRequestOptions;
import com.example.androidlesson3.models.BoredAction;

public  interface IBoredApiClient {
    void getBoredAction(
            ActionRequestOptions requestOptions,
            BoredActionCallback callback
    );
    interface BoredActionCallback extends CoreCallback <BoredAction>{
    }
}
