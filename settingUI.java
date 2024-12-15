package com.resto.myapplication;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

import com.shashank.sony.fancytoastlib.FancyToast;

public class settingUI extends RelativeLayout {
 private Context con;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public settingUI(Context context) {
        super(context);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private LinearLayout createSettingButton(Context context, String text, @DrawableRes int iconRes, boolean isClickable) {
        LinearLayout button = new LinearLayout(context);
        button.setOrientation(LinearLayout.HORIZONTAL);
        button.setBackgroundResource(R.drawable.button_background);
        button.setPadding(dpToPx(this.getContext(),16), dpToPx(this.getContext(),16), dpToPx(this.getContext(),16), dpToPx(this.getContext(),16));
        button.setClickable(isClickable);
        button.setFocusable(isClickable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isClickable) {
            button.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.button_click_animator));
        }
        button.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        ((LayoutParams) button.getLayoutParams()).setMargins(0, 0, 0, 8);

        ImageView icon = new ImageView(context);
        icon.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(this.getContext(),24), ViewGroup.LayoutParams.MATCH_PARENT));
        icon.setImageResource(iconRes);


        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                1
        ));
        textView.setText(text);
        textView.setTextColor(0xFF000000); // black
        textView.setTextSize(16);
        textView.setGravity(CENTER_VERTICAL);

        button.addView(icon);
        button.addView(textView);

        return button;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private LinearLayout createLanguageSelector(Context context) {
        LinearLayout button = createSettingButton(context, "Language", R.drawable.translate, true);
        button.setOrientation(LinearLayout.HORIZONTAL);

        Spinner languageSpinner = new Spinner(context);
        languageSpinner.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));
  // spinner language aaray , I need to set it FUCKING SOON
        button.addView(languageSpinner);
        return button;
    }
    public void init(Context context) {

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        this.setLayoutParams(relativeLayoutParams);
        this.setPadding(16, 16, 16, 16);
        this.setBackgroundColor(0xFFFFF5E1); // color as mem #FFF5E1    From chat GPT

        // Profile Section
        LinearLayout profileSection = new LinearLayout(context);
        profileSection.setId(View.generateViewId());
        profileSection.setOrientation(LinearLayout.VERTICAL);
        profileSection.setGravity(CENTER_HORIZONTAL);
        profileSection.setBackgroundResource(R.drawable.rounded_bottom_corners);
        profileSection.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        profileSection.setPadding(16, 16, 16, 16);
        ((LayoutParams) profileSection.getLayoutParams()).setMargins(0, 0, 0, 16);

        ImageView profilePhoto = new ImageView(context);
        profilePhoto.setId(View.generateViewId());
        profilePhoto.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        profilePhoto.setImageResource(R.drawable.channels4_profile);
        profilePhoto.setForegroundGravity(CENTER_IN_PARENT);
        profilePhoto.setBackgroundResource(R.drawable.circular_image_background);
        profilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        profilePhoto.setContentDescription("Profile photo");

        TextView accountName = new TextView(context);
        accountName.setId(View.generateViewId());
        accountName.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));
        accountName.setText("Hafozli9 (Admin)");
        accountName.setTextColor(0xFF000000); // black
        accountName.setTextSize(24);
        accountName.setGravity(CENTER_HORIZONTAL);
        accountName.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        profileSection.addView(profilePhoto);
        profileSection.addView(accountName);
        this.addView(profileSection);

        // BUTTON ABOUT US FUCK
        LinearLayout settingsButtons = new LinearLayout(context);
        settingsButtons.setOrientation(LinearLayout.VERTICAL);
        settingsButtons.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        settingsButtons.setPadding(dpToPx(this.getContext(),16),dpToPx(this.getContext(),16),dpToPx(this.getContext(),16),dpToPx(this.getContext(),16));

        settingsButtons.isClickable();
        settingsButtons.focusableViewAvailable(this);
        ((LayoutParams) settingsButtons.getLayoutParams()).addRule(BELOW, profileSection.getId());

        LinearLayout btnAboutUs = createSettingButton(context, "About Us", R.drawable.info, true);
        btnAboutUs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(con, "Hello World !", FancyToast.LENGTH_LONG, FancyToast.ERROR,true);
            }
        });
        settingsButtons.addView(btnAboutUs);

        LinearLayout langSelector = createLanguageSelector(context);
        langSelector.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        settingsButtons.addView(langSelector);

        LinearLayout btnDarkMode = createSettingButton(context, "Dark Mode", R.drawable.lightmode, false);
        btnDarkMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Dark Mode button click
            }
        });
        settingsButtons.addView(btnDarkMode);

        LinearLayout btnNotifications = createSettingButton(context, "Notifications", R.drawable.notification, false);
        btnNotifications.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Notifications button click
            }
        });
        settingsButtons.addView(btnNotifications);

        this.addView(settingsButtons);

        // Logout Button
        LinearLayout logoutButton = new LinearLayout(context);
        logoutButton.setOrientation(LinearLayout.HORIZONTAL);
        logoutButton.setBackgroundResource(R.drawable.button_background);
        logoutButton.setPadding(16, 16, 16, 16);
        logoutButton.setClickable(true);
        logoutButton.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            logoutButton.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.button_click_animator));
        }
        logoutButton.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        ((LayoutParams) logoutButton.getLayoutParams()).addRule(ALIGN_PARENT_BOTTOM);
        ((LayoutParams) logoutButton.getLayoutParams()).setMargins(0, 0, 0, 16);

        ImageView logoutIcon = new ImageView(context);
        logoutIcon.setLayoutParams(new LinearLayout.LayoutParams(24, 24));
        logoutIcon.setImageResource(R.drawable.logout);

        TextView logoutText = new TextView(context);
        logoutText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                1
        ));
        logoutText.setText("Logout");
        logoutText.setTextColor(0xFF000000); // black
        logoutText.setTextSize(16);
        logoutText.setGravity(CENTER_VERTICAL);

        logoutButton.addView(logoutIcon);
        logoutButton.addView(logoutText);

        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Logout button click
            }
        });

        this.addView(logoutButton);

    }    public void ctxgt( Context ctx){
this.con =ctx;
    }
    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
