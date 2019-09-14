package com.example.androidlesson3.data;

import com.example.androidlesson3.core.CoreApiClient;
import com.example.androidlesson3.models.BoredAction;
import retrofit2.Call;
import retrofit2.http.GET;

public class BoredApiClient extends CoreApiClient implements IBoredApiClient {

    private BoredNetworkClient client = getRetrofit("http://www.boredapi.com/")
            .create(BoredNetworkClient.class);

    @Override
    public void getBoredAction(final BoredActionCallback callback ) {
        final Call<BoredAction> call = client.getBoredAction(
        );

        call.enqueue(new ResponseHandler<BoredAction>() {
            @Override
            void onSuccess(BoredAction result) {
                callback.onSuccess(result);
            }

            @Override
            void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public interface BoredNetworkClient {
        @GET("/api/activity/")
        Call<BoredAction> getBoredAction(
        );
    }
}
