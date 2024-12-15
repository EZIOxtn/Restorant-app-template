package com.resto.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.AsyncTask;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shashank.sony.fancytoastlib.FancyToast;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

public class MainActivity extends AppCompatActivity implements IntroPagerAdapter.OnStartClickListener {
    private static final String[] tabTitles = {"Welcome", "Delivery", "Start"};
private int crpage = 0;
    private static final int REQUEST_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
checkPermissions();
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ImageButton btleft = findViewById(R.id.btn_left);
        ImageButton btright = findViewById(R.id.btn_right);


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.custom_status_bar_color));
        btleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crpage > 0) {
                    crpage--;
                    viewPager.setCurrentItem(crpage);
                }
            }
        });

        btright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crpage < tabTitles.length - 1) {
                    crpage++;
                    viewPager.setCurrentItem(crpage);
                }
            }
        });
        IntroPagerAdapter adapter = new IntroPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new SetupViewPagerTask(viewPager, tabLayout, btleft,btright).execute();

    }

    @Override
    public void onStartClick() {
        startActivity(new Intent(this, logact.class));
        finish();
    }

    private static class SetupViewPagerTask extends AsyncTask<Void, Void, Void> {
        private final ViewPager2 viewPager;
        private final TabLayout tabLayout;
        private final ImageButton btleft;
        private final ImageButton btright;
        SetupViewPagerTask(ViewPager2 viewPager, TabLayout tabLayout, ImageButton btleft, ImageButton btright) {
            this.viewPager = viewPager;
            this.tabLayout = tabLayout;
            this.btleft = btleft;
            this.btright = btright;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null; // Background work, if needed
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                   if (position == 0) {
                       btleft.setVisibility(View.GONE);
                       btright.setVisibility(View.VISIBLE);

                   } else if (position == 2) {
                       btleft.setVisibility(View.VISIBLE);
                       btright.setVisibility(View.GONE);
                   }else {
                       btleft.setVisibility(View.VISIBLE);
                       btright.setVisibility(View.VISIBLE);
                   }
                }
            });
            return null;
        }
    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                requestFilePermissions();
            } else {
                // Permission already granted, continue with app logic
                continueApp();
            }
        } else {
            // SDK below Marshmallow, no runtime permissions required
            continueApp();
        }
    }

    private void requestFilePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permissions Required")
                    .setMessage("This app needs storage access to function properly. Please grant the requested permissions.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            FancyToast.makeText(MainActivity.this,"you need to give permission to start", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                            exitApp();
                        }
                    })
                    .create()
                    .show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, continue with app logic
                continueApp();
            } else {
                showSettingsDialog();
            }
        }
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permissions Denied")
                .setMessage("You have denied the necessary permissions. Please grant them from the settings or the app will exit.")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(android.net.Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApp();
                    }
                })
                .create()
                .show();
    }

    private void continueApp() {
        // Continue with the app logic after permissions are granted
    }

    private void exitApp() {
        finish();
        System.exit(0);
    }
}
