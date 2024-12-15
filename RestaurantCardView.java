package com.resto.myapplication;

import static com.resto.myapplication.ResorantActivity.restaurantsContainer;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class RestaurantCardView extends RelativeLayout {

    private ImageView imageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView heartImageView;
    private ImageView starImageView;
    private boolean isHeartFilled = true;
    private boolean isStarFilled = true;

    public RestaurantCardView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        CardView cardView = new CardView(context);
        LayoutParams cardParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(dpToPx(context, 8), dpToPx(context, 8), dpToPx(context, 8), dpToPx(context, 8));
        cardView.setLayoutParams(cardParams);
        cardView.setCardElevation(dpToPx(context, 4));
        cardView.setRadius(dpToPx(context, 10));
        cardView.setClickable(true);
        cardView.setFocusable(true);
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        cardView.setMaxCardElevation(dpToPx(context, 1));
        cardView.setPreventCornerOverlap(false);
        cardView.setUseCompatPadding(true);
        StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.button_click_animator);
        cardView.setStateListAnimator(stateListAnimator);

        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Restaurant card clicked inside the class");
                if (getContext() instanceof OnRestaurantCardClickListener) {
                    ((OnRestaurantCardClickListener) getContext()).onRestaurantCardClick(RestaurantCardView.this);
                }

                restaurantsContainer.removeAllViews();

                for (int i = 0; i < 5; i++) {
                    FoodCardView foodCardView = new FoodCardView(v.getContext());
                    foodCardView.setFoodName("Food Item " + (i + 1));
                    foodCardView.setFoodDescription("Description for Food Item " + (i + 1));
                    foodCardView.setFoodImageResource(R.drawable.burger);
                    restaurantsContainer.addView(foodCardView);
                }
            }
        });
        RelativeLayout innerLayout = new RelativeLayout(context);
        LayoutParams innerParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        innerLayout.setLayoutParams(innerParams);
        innerLayout.setPadding(dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10));

        imageView = new ImageView(context);
        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(
                dpToPx(context, 150), dpToPx(context, 133));
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        imgParams.setMarginEnd(dpToPx(context, 16));
        imageView.setLayoutParams(imgParams);
        imageView.setId(View.generateViewId());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.store);
        imageView.setContentDescription(context.getString(R.string.app_name));
        imageView.setClickable(false);
        imageView.setFocusable(false);


        titleTextView = new TextView(context);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        titleParams.addRule(RelativeLayout.END_OF, imageView.getId());
        titleParams.addRule(RelativeLayout.ALIGN_TOP, imageView.getId());
        titleParams.setMarginStart(dpToPx(context, 16));
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setId(View.generateViewId());
        titleTextView.setText(context.getString(R.string.app_name));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleTextView.setTypeface(null, Typeface.BOLD);
        titleTextView.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        titleTextView.setTextColor(Color.BLACK);


        descriptionTextView = new TextView(context);
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        descParams.addRule(RelativeLayout.END_OF, imageView.getId());
        descParams.addRule(RelativeLayout.BELOW, titleTextView.getId());
        descParams.setMarginStart(dpToPx(context, 16));
        descParams.topMargin = dpToPx(context, 10);
        descriptionTextView.setLayoutParams(descParams);
        descriptionTextView.setId(View.generateViewId());
        descriptionTextView.setText("This is a text about the app.");
        descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        descriptionTextView.setTypeface(null, Typeface.BOLD);
        descriptionTextView.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        descriptionTextView.setTextColor(Color.BLACK);

        heartImageView = new ImageView(context);
        RelativeLayout.LayoutParams heartParams = new RelativeLayout.LayoutParams(
                dpToPx(context, 30), dpToPx(context, 30));
        heartParams.addRule(RelativeLayout.BELOW, imageView.getId());
        heartParams.addRule(RelativeLayout.ALIGN_START, descriptionTextView.getId());
        heartParams.setMarginStart(dpToPx(context, 16));
        heartImageView.setLayoutParams(heartParams);
        heartImageView.setId(View.generateViewId());
        heartImageView.setImageResource(R.drawable.ic_heart_fillled);
        heartImageView.setClickable(true);
        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeartIcon();
            }
        });

        starImageView = new ImageView(context);
        RelativeLayout.LayoutParams starParams = new RelativeLayout.LayoutParams(
                dpToPx(context, 30), dpToPx(context, 30));
        starParams.addRule(RelativeLayout.BELOW, imageView.getId());

     //   starParams.set(dpToPx(context, 0));
        starImageView.setLayoutParams(starParams);
        starImageView.setId(View.generateViewId());
        starImageView.setImageResource(R.drawable.ic_star_filled);

        starImageView.setClickable(true);
        starImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleStarIcon();
            }
        });
        innerLayout.addView(imageView);
        innerLayout.addView(titleTextView);
        innerLayout.addView(descriptionTextView);
        innerLayout.addView(heartImageView);
        innerLayout.addView(starImageView);

        cardView.addView(innerLayout);
this.addView(cardView);

    }

    private void toggleHeartIcon() {
        if (isHeartFilled) {
            heartImageView.setImageResource(R.drawable.ic_heart_empty);
        } else {
            heartImageView.setImageResource(R.drawable.ic_heart_fillled);
        }
        isHeartFilled = !isHeartFilled;
    }

    private void toggleStarIcon() {
        if (isStarFilled) {
            starImageView.setImageResource(R.drawable.ic_star_empty); // Update with the empty star drawable
        } else {
            starImageView.setImageResource(R.drawable.ic_star_filled); // Update with the filled star drawable
        }
        isStarFilled = !isStarFilled;
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    private void addUpperCard() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        uppercard crd = (uppercard) inflater.inflate(R.layout.uppercard, restaurantsContainer, false);

        crd.setImage(R.drawable.channels4_profile);
        crd.setName("مطعم الرحمة");
        crd.setDescription("شاف : السكرتح");
        crd.setLocation(34.0522, -118.2437);

        restaurantsContainer.addView(crd, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
    }


    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }
    public interface OnRestaurantCardClickListener {
        void onRestaurantCardClick(RestaurantCardView card);
    }
}
