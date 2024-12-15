package com.resto.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.marsad.stylishdialogs.StylishAlertDialog;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView profilePhoto = findViewById(R.id.profile_photo);
        TextView accountName = findViewById(R.id.account_name);
        LinearLayout btnSettings = findViewById(R.id.btn_langSelector);
        LinearLayout btnAboutUs = findViewById(R.id.btn_about_us);
        LinearLayout btnLogout = findViewById(R.id.btn_logout);

findViewById(R.id.profile_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings button click
                showNotification(v.getContext(), "Title", "This is the message to display", getIntent(), 1);
                Toast.makeText(getApplicationContext(),"mozin nuget",Toast.LENGTH_SHORT).show();
                FancyToast.makeText(SettingsActivity.this,"Hello World !", FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings button click
                Toast.makeText(getApplicationContext(),"mozin nuget",Toast.LENGTH_SHORT).show();
                FancyToast.makeText(SettingsActivity.this,"Hello World !", FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle about us button click
                NotificationHandler.showNotification(v.getContext(), "Notification Title", "Notification Message", R.drawable.store, MainActivity.class);

                FancyToast.makeText(SettingsActivity.this,"Hello World !", FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout button click
                Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                // Perform logout action here
                StylishAlertDialog stylishDialog = new StylishAlertDialog(SettingsActivity.this, StylishAlertDialog.PROGRESS);
                stylishDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                stylishDialog.setTitleText("Loading")
                        .setCancellable(false)
                        //.setCancelledOnTouchOutside(false)
                        .show();

            }
        });

    }
    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.store)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }
}
