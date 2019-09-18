package com.example.androidlesson3.data;

import com.example.androidlesson3.core.CoreApiClient;
import com.example.androidlesson3.models.ActionRequestOptions;
import com.example.androidlesson3.models.BoredAction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BoredApiClient extends CoreApiClient implements IBoredApiClient {

    private final static String urlBoredApi = "http://www.boredapi.com/";

    private BoredNetworkClient client = getRetrofit(urlBoredApi)
            .create(BoredNetworkClient.class);

    @Override
    public void getBoredAction(
            ActionRequestOptions requestOptions,
            final BoredActionCallback callback) {
        final Call<BoredAction> call = client.getBoredAction(
                requestOptions.getType(),
                requestOptions.getMinPrice(),
                requestOptions.getMaxPrice(),
                requestOptions.getMinAccessibility(),
                requestOptions.getMaxAccessibility()

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
                @Query("type") String type,
                @Query("minprice") Float minPrice,
                @Query("maxprice") Float maxPrice,
                @Query("minaccessibility") Float minAccessibility,
                @Query("maxaccessibility") Float maxAccessibility

        );
    }
}
