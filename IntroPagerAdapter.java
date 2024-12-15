package com.resto.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.tabs.TabLayout;

public class IntroPagerAdapter extends RecyclerView.Adapter<IntroPagerAdapter.IntroViewHolder> {

    private final int[] animations = {R.raw.intro1, R.raw.intro2, R.raw.intro3};
    private final String[] texts = {
            "Welcome to Our Restaurant App",
            "Fast delivery, exclusively in Gafsa",
            "Set your language before you start"
    };
    public static final int LANGUAGE_NONE = 0;
    public static final int LANGUAGE_ARABIC = 1;
    public static final int LANGUAGE_FRENCH = 2;
    private int selectedLanguage = LANGUAGE_NONE;

    private final OnStartClickListener onStartClickListener;

    public IntroPagerAdapter(OnStartClickListener onStartClickListener) {
        this.onStartClickListener = onStartClickListener;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intro, parent, false);
        return new IntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.lottieAnimationView.setAnimation(animations[position]);
        holder.lottieAnimationView.playAnimation();
        holder.textView.setText(texts[position]);

if (position == 1) {
    try {
        animateTextView(holder.textView);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                animateTextViewSlide(holder.textView);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
th.start();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
        if (position == getItemCount() - 1) {
            holder.startButton.setVisibility(View.VISIBLE);
            holder.arabi.setVisibility(View.VISIBLE);
            holder.fransai.setVisibility(View.VISIBLE);
            holder.lottieAnimationView.loop(false);
            holder.lottieAnimationView.setMaxWidth(200);
            holder.lottieAnimationView.setMaxHeight(100);
            holder.lottieAnimationView.getLayoutParams().width = 200;
            holder.lottieAnimationView.getLayoutParams().height = 100;
            holder.lottieAnimationView.getLayoutParams().width = convertPxToDp(holder.lottieAnimationView.getContext(), 200);
            holder.lottieAnimationView.getLayoutParams().height = convertPxToDp(holder.lottieAnimationView.getContext(), 100);


            updateButtonStyles(holder);

            holder.arabi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedLanguage = LANGUAGE_ARABIC;
                    updateButtonStyles(holder);
                }
            });

            holder.fransai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedLanguage = LANGUAGE_FRENCH;
                    updateButtonStyles(holder);
                }
            });
            holder.startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartClickListener.onStartClick();
                }
            });
        } else {
            holder.startButton.setVisibility(View.GONE);
            holder.arabi.setVisibility(View.GONE);
            holder.fransai.setVisibility(View.GONE);
            holder.lottieAnimationView.loop(true);
            holder.lottieAnimationView.setMaxWidth(411);
            holder.lottieAnimationView.setMaxHeight(452);
            holder.lottieAnimationView.getLayoutParams().width = convertPxToDp(holder.lottieAnimationView.getContext(), 411);
            holder.lottieAnimationView.getLayoutParams().height = convertPxToDp(holder.lottieAnimationView.getContext(), 452);

        }
    }
    public static int convertPxToDp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px * density);
    }
    @Override
    public int getItemCount() {
        return animations.length;
    }
    private void updateButtonStyles(@NonNull IntroViewHolder holder) {
        if (selectedLanguage == LANGUAGE_ARABIC) {
            holder.arabi.setBackgroundColor(Color.parseColor("#FF332F31"));
            holder.fransai.setBackgroundResource(R.drawable.button_border);
        } else if (selectedLanguage == LANGUAGE_FRENCH) {
            holder.arabi.setBackgroundResource(R.drawable.button_border);
            holder.fransai.setBackgroundColor(Color.parseColor("#FF332F31"));
        } else {
            holder.arabi.setBackgroundResource(R.drawable.button_border);
            holder.fransai.setBackgroundResource(R.drawable.button_border);
        }
    }
    static class IntroViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView lottieAnimationView;
        TextView textView;
        Button fransai;
        Button arabi;
        TabLayout tabLayout;
        Button startButton;

        IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            lottieAnimationView = itemView.findViewById(R.id.lottieAnimationView);
            textView = itemView.findViewById(R.id.textView);
            fransai = itemView.findViewById(R.id.franch);
            arabi = itemView.findViewById(R.id.arabic);
            startButton = itemView.findViewById(R.id.start_button);


            // Debugging log to check if tabLayout is null
            if (tabLayout == null) {
                Log.e("IntroPagerAdapter", "TabLayout is null");
            }
        }
    }

    private void animateTextViewSlide(TextView textView) {
        // Slide in animation
        TranslateAnimation slideIn = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,  // Start from left (off screen)
                Animation.RELATIVE_TO_PARENT, 0.0f,   // End at the original position
                Animation.RELATIVE_TO_PARENT, 0.0f,   // No vertical movement
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        slideIn.setDuration(5000);  // Duration in milliseconds
        slideIn.setFillAfter(true); // Keep the view at the final position

        // Slide out animation
        TranslateAnimation slideOut = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,   // Start at the original position
                Animation.RELATIVE_TO_PARENT, 1.0f,   // Move to right (off screen)
                Animation.RELATIVE_TO_PARENT, 0.0f,   // No vertical movement
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        slideOut.setDuration(5000);
        slideOut.setFillAfter(true);

        // Create an AnimationSet to combine both animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideIn);
        animationSet.addAnimation(slideOut);
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.RESTART);

        textView.startAnimation(animationSet);
    }

    private void animateTextView(TextView textView) throws InterruptedException {
        //Animation fadeOut = new AlphaAnimation(1, 0);

        //fadeOut.setDuration(1000); // Duration in milliseconds
       // fadeOut.setInterpolator(new AccelerateInterpolator());

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(5000);
        fadeIn.setInterpolator(new AccelerateInterpolator());

        AnimationSet animation = new AnimationSet(true);

        animation.addAnimation(fadeIn);

        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);

        textView.startAnimation(animation);
        
    }
    public interface OnStartClickListener {
        void onStartClick();
    }
}
