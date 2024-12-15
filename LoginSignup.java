package com.resto.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginSignup extends AsyncTask<String, Void, String> {
    private static final String TAG = "LoginSignup";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String url;
    private final Callback callback;

    public interface Callback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public LoginSignup(String url, Callback callback) {
        this.url = url;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String email = params[1];
        String password = params[2];

        OkHttpClient client = new OkHttpClient();

        try {
            // Create JSON object
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("email", email);
            json.put("password", password);

            // Create RequestBody
            RequestBody requestBody = RequestBody.create(json.toString(), JSON);

            // Build the request
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            // Execute the request
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "Exception in doInBackground", e);
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.startsWith("Error:") || result.contains("Exception")) {
            callback.onFailure(result);
        } else {
            callback.onSuccess(result);
        }
    }
}
