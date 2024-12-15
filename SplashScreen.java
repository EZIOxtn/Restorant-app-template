package com.resto.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class SplashScreen extends RelativeLayout {
    private ProgressBar prg;

    public SplashScreen(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        // Set layout parameters for SplashScreen
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.parseColor("#FFF5E1"));
        this.setId(R.id.idRLContainer);

        // Create and add the ProgressBar
        prg = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        prg.setId(R.id.idPBLoading);
        prg.setIndeterminateTintList(getResources().getColorStateList(R.color.black, null));

        RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        this.addView(prg, progressBarParams);
    }

    public ProgressBar getProgressBar() {
        return prg;
    }
}
