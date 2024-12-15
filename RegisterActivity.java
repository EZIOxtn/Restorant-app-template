package com.resto.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private String names;
    private String emails;
    private String passwords;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertwo);

        btn = findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from input fields
                TextView name = findViewById(R.id.et_name);
                TextView email = findViewById(R.id.et_email);
                TextView password = findViewById(R.id.et_password);

                names = name.getText().toString();
                emails = email.getText().toString();
                passwords = password.getText().toString();

                // Validate input
                if (names.isEmpty() || emails.isEmpty() || passwords.isEmpty()) {
                    FancyToast.makeText(RegisterActivity.this, "All fields are required", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (names.length() < 5 || names.length() > 20) {
                    FancyToast.makeText(RegisterActivity.this, "Name must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (emails.length() < 5 || emails.length() > 20) {
                    FancyToast.makeText(RegisterActivity.this, "Email must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (passwords.length() < 5 || passwords.length() > 20) {
                    FancyToast.makeText(RegisterActivity.this, "Password must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                new RegisterTask().execute(names, emails, passwords);
            }
        });
    }

    private class RegisterTask extends AsyncTask<String, Void, String> {
        private boolean isSuccess = false;


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
                RequestBody requestBody = RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json; charset=utf-8")
                );

                // Build the request
                System.out.println("Request body: " + requestBody);
                Request request = new Request.Builder()
                        .url("https://d74e3b7c66f18549f7c03261568611ab.serveo.net/api/auth/register")
                        .post(requestBody)
                        .build();

                // Execute request
                Response response = client.newCall(request).execute();
System.out.println(response);
                if (response.isSuccessful()) {
                    isSuccess = true;
                    return response.body().string();
                } else {
                    return "Error: " + response.code();
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (isSuccess) {
                FancyToast.makeText(RegisterActivity.this, "Registration successful: " + result, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
            } else {
                FancyToast.makeText(RegisterActivity.this, "Failed: " + result, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }

        }
    }
}
