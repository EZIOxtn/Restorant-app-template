package com.resto.myapplication;

import static android.os.AsyncTask.execute;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Registertwo extends AppCompatActivity {
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
            @SuppressLint("StaticFieldLeak")
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
                    FancyToast.makeText(Registertwo.this, "All fields are required", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (names.length() < 5 || names.length() > 20) {
                    FancyToast.makeText(Registertwo.this, "Name must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (emails.length() < 5 || emails.length() > 20) {
                    FancyToast.makeText(Registertwo.this, "Email must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                if (passwords.length() < 5 || passwords.length() > 20) {
                    FancyToast.makeText(Registertwo.this, "Password must be between 5 and 20 characters", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }

                new LoginSignup("https://d74e3b7c66f18549f7c03261568611ab.serveo.net/api/auth/register", new LoginSignup.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        FancyToast.makeText(Registertwo.this, "Registration successful: " + response, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        FancyToast.makeText(Registertwo.this, "Failed: " + error, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }).execute(names, emails, passwords);
            }
        });
    }


}
