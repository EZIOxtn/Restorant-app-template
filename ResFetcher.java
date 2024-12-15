package com.resto.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResFetcher {
    private static final String API_URL = "http://your-server/fetch_restaurants.php";
    private OkHttpClient client = new OkHttpClient();

    public interface FetchRestaurantsCallback {
        void onSuccess(List<RestorantCLS> restaurants);
        void onError(Exception e);
    }

    public void fetch(final FetchRestaurantsCallback callback) {
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        List<RestorantCLS> restaurantList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e( "json objects", jsonObject.toString());
                            RestorantCLS restaurant = new RestorantCLS();
                            restaurant.setId(jsonObject.getInt("id"));
                            restaurant.setName(jsonObject.getString("name"));
                            restaurant.setPhoto(jsonObject.getString("photo"));
                            restaurant.setDescription(jsonObject.getString("description"));
                            restaurantList.add(restaurant);
                        }
                        new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(restaurantList));
                    } catch (JSONException e) {
                        new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
                    }
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError(new IOException("Unexpected response")));
                }
            }
        });
    }
}
