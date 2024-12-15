package com.resto.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;


import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
public class logact extends AppCompatActivity {

    private static final int ROTATION_DURATION = 1000; // Duration of rotation in milliseconds
    private static final int DELAY_BETWEEN_ROTATIONS = 4000; // Delay between rotations in milliseconds

    private Context ctx;
    private static final String PREFS_NAME = "app_prefs";
    private static final String PREF_LANGUAGE = "language";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Window window = this.getWindow();

        TextView gtavTextView = findViewById(R.id.regv_register);
        ctx = logact.this;
        PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(logact.this,
                "https://your-privacy-policy-url.com",
                "https://your-terms-of-service-url.com");

        dialog.addPoliceLine("We use your personal data to provide and improve the service.");
        dialog.addPoliceLine("We also use your data to personalize content and ads.");

        dialog.setTitle("Privacy Policy");


        // Show the dialog

        String text = "Don't have an account? Register";
        SpannableString spannableString = new SpannableString(text);
        ImageView imageView = findViewById(R.id.imageView4); // Replace with your ImageView ID
        startRotationAnimation(imageView);
        int start = text.indexOf("Register");
        int end = start + "Register".length();

        // Set the color of "Register" to blue
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView
        gtavTextView.setText(spannableString);
        // FancyToast.makeText(logact.this,"Hello World !", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        EditText etPhoneNumber = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.custom_status_bar_color));
        View vx = findViewById(R.id.regv_register);
        LinearLayout backgroundLayout = findViewById(R.id.bgmove);
        TransitionDrawable transitionDrawable = (TransitionDrawable) backgroundLayout.getBackground();
        transitionDrawable.startTransition(3000);
        backgroundLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                transitionDrawable.reverseTransition(3000);
                backgroundLayout.postDelayed(this, 3000 * 2);
            }
        }, 3000 * 2);
if ((vx)== null){Log.e("null btn","the button is null rn");}else {
    Log.e("btn evaliable", "the button is avaliable \n starting the intent ");
    vx.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(logact.this, Registertwo.class);
                startActivity(intent);
                Log.e( "onClick: ", "this should be done here");
            } catch (Exception e) {
                Log.e("Error", e.toString());
                FancyToast.makeText(logact.this, "Error occurred!", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        }
    });
}

        FancyToast.makeText(logact.this,"just click this guy over here \n and register", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    dialog.show();
                    String phoneNumber = etPhoneNumber.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    Intent intent = new Intent(logact.this, ResorantActivity.class);
                    startActivity(intent);
                    if (validateInputs(phoneNumber, password)) {
                        try {

                            performLogin(phoneNumber, password);
                        }catch (Exception e) {
                            e.printStackTrace();
                            FancyToast.makeText(logact.this,"Hello World !", FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
                        }

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }





    private boolean validateInputs(String phoneNumber, String password) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 8) {
            Toast.makeText(this, "Phone number must be exactly 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 23) {
            Toast.makeText(this, "Password must be between 6 and 23 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void startRotationAnimation(final ImageView imageView) {
        final Handler handler = new Handler();
        final Runnable rotationRunnable = new Runnable() {
            @Override
            public void run() {
                RotateAnimation rotate = new RotateAnimation(
                        0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                rotate.setDuration(ROTATION_DURATION);
                rotate.setFillAfter(true); // Keeps the final rotation state

                imageView.startAnimation(rotate);

                // Schedule the next rotation after the delay
                handler.postDelayed(this, ROTATION_DURATION + DELAY_BETWEEN_ROTATIONS);
            }
        };

        // Start the rotation process
        handler.post(rotationRunnable);
    }

    private void performLogin(String phoneNumber, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-api-url.com/") // Replace with your server URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        JsonObject loginData = new JsonObject();
        loginData.addProperty("number", phoneNumber);
        loginData.addProperty("password", password);

        Call<JsonObject> call = apiService.loginUser(loginData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    Toast.makeText(logact.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Navigate to another activity if needed
                } else {
                    // Handle failure response
                    Toast.makeText(logact.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(logact.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface ApiService {
        @POST("/api/login") // Replace with your login endpoint
        Call<JsonObject> loginUser(@Body JsonObject loginData);
    }
}