package com.resto.myapplication;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class FoodCardView extends RelativeLayout {

    private ImageView foodImageView;
    private TextView foodNameTextView;
    private TextView foodDescriptionTextView;
    private TextView foodPriceTextView;
    private TextView orderTextView;

    public FoodCardView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Set up the CardView
        CardView cardView = new CardView(context);
        LayoutParams cardParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(dpToPx(context, 8), dpToPx(context, 8), dpToPx(context, 8), dpToPx(context, 8));
        cardView.setLayoutParams(cardParams);
        cardView.setCardElevation(dpToPx(context, 4));
        cardView.setRadius(dpToPx(context, 10));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        cardView.setMaxCardElevation(dpToPx(context, 1));
        cardView.setPreventCornerOverlap(false);
        cardView.setUseCompatPadding(true);

        // Set up the inner layout
        RelativeLayout innerLayout = new RelativeLayout(context);
        LayoutParams innerParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        innerParams.setMargins(dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10));
        innerLayout.setLayoutParams(innerParams);
        innerLayout.setClickable(true);
        innerLayout.setFocusable(true);
        innerLayout.setFocusableInTouchMode(true);
        innerLayout.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.button_click_animator));

        // Set up the ImageView
        foodImageView = new ImageView(context);
        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(
                dpToPx(context, 150), dpToPx(context, 150));
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        imgParams.setMarginEnd(dpToPx(context, 16));
        foodImageView.setLayoutParams(imgParams);
        foodImageView.setId(View.generateViewId());
        foodImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        foodImageView.setImageResource(R.drawable.burger);
        foodImageView.setContentDescription(context.getString(R.string.app_name));
        foodImageView.setBackgroundResource(R.drawable.button_round);

        // Set up the Food Name TextView
        foodNameTextView = new TextView(context);
        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.END_OF, foodImageView.getId());
        nameParams.addRule(RelativeLayout.ALIGN_TOP, foodImageView.getId());
        nameParams.setMarginStart(dpToPx(context, 16));
        foodNameTextView.setLayoutParams(nameParams);
        foodNameTextView.setId(View.generateViewId());
        foodNameTextView.setText("Mlewi");
        foodNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        foodNameTextView.setTypeface(null, Typeface.BOLD);
        foodNameTextView.setTextColor(Color.BLACK);

        // Set up the Food Description TextView
        foodDescriptionTextView = new TextView(context);
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        descParams.addRule(RelativeLayout.END_OF, foodImageView.getId());
        descParams.addRule(RelativeLayout.BELOW, foodNameTextView.getId());
        descParams.setMarginStart(dpToPx(context, 16));
        descParams.topMargin = dpToPx(context, 8);
        foodDescriptionTextView.setLayoutParams(descParams);
        foodDescriptionTextView.setId(View.generateViewId());
        foodDescriptionTextView.setText("mlewi formage w harissa.");
        foodDescriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        foodDescriptionTextView.setTextColor(Color.BLACK);

        // Set up the Food Price TextView
        foodPriceTextView = new TextView(context);
        RelativeLayout.LayoutParams priceParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        priceParams.addRule(RelativeLayout.END_OF, foodImageView.getId());
        priceParams.addRule(RelativeLayout.BELOW, foodDescriptionTextView.getId());
        priceParams.setMarginStart(dpToPx(context, 16));
        priceParams.topMargin = dpToPx(context, 8);
        foodPriceTextView.setLayoutParams(priceParams);
        foodPriceTextView.setId(View.generateViewId());
        foodPriceTextView.setText("10.00DT");
        foodPriceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        foodPriceTextView.setTypeface(null, Typeface.BOLD);
        foodPriceTextView.setTextColor(ContextCompat.getColor(context, R.color.holo_green_dark));

        // Set up the Order TextView
        orderTextView = new TextView(context);
        RelativeLayout.LayoutParams orderParams = new RelativeLayout.LayoutParams(
                dpToPx(context, 189),
                dpToPx(context, 61));
        orderParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        orderParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        orderParams.setMarginEnd(dpToPx(context, 16));
        orderParams.bottomMargin = dpToPx(context, 16);
        orderTextView.setLayoutParams(orderParams);
        orderTextView.setId(View.generateViewId());
        orderTextView.setText("O r d e r");
        orderTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        orderTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
        orderTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        orderTextView.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        orderTextView.setClickable(true);
        orderTextView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.button_click_animator));

        // Add views to the inner layout
        innerLayout.addView(foodImageView);
        innerLayout.addView(foodNameTextView);
        innerLayout.addView(foodDescriptionTextView);
        innerLayout.addView(foodPriceTextView);
        innerLayout.addView(orderTextView);

        // Add inner layout to the CardView
        cardView.addView(innerLayout);

        // Add CardView to the layout
        this.addView(cardView);
    }

    // Method to convert dp to pixels
    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // Optionally, you can add methods to update the view with data
    public void setFoodImageResource(int resId) {
        foodImageView.setImageResource(resId);
    }

    public void setFoodName(String name) {
        foodNameTextView.setText(name);
    }

    public void setFoodDescription(String description) {
        foodDescriptionTextView.setText(description);
    }

    public void setFoodPrice(String price) {
        foodPriceTextView.setText(price);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("FoodCardView", "Touch event: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
