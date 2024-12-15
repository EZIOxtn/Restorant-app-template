package com.resto.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class GroupRestorantFragment extends Fragment {

    private LinearLayout restaurantsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_prestorant, container, false);
        restaurantsContainer = view.findViewById(R.id.restaurant_container);

        // Fetch and add restaurant data (simulated here with a placeholder method)
        fetchRestaurantData();

        return view;
    }

    private void fetchRestaurantData() {
        for (int i = 0; i < 5; i++) {
            addRestaurantCard("Restaurant " + (i + 1), R.drawable.store, "Rating: 4." + i, "123-456-7890");
        }
    }

    private void addRestaurantCard(String name, int imageResId, String rating, String phone) {
        CardView cardView = new CardView(getContext());
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.card_height)); // Set height to 200dp
        cardParams.setMargins(10, 10, 10, 2); // Set margins with very little bottom margin
        cardView.setLayoutParams(cardParams);
        cardView.setCardElevation(10);
        cardView.setRadius(20);
        cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        cardView.setMaxCardElevation(12);
        cardView.setUseCompatPadding(true);

        // Create inner LinearLayout
        LinearLayout innerLayout = new LinearLayout(getContext());
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams innerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        innerLayout.setLayoutParams(innerParams);

        // Add ImageView
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.image_width),
                (int) getResources().getDimension(R.dimen.image_height),
                1);
        imgParams.setMargins(8, 8, 8, 8);
        imageView.setLayoutParams(imgParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageResId);

        // Add TextViews
        LinearLayout textLayout = new LinearLayout(getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
        textLayout.setLayoutParams(textParams);

        TextView nameTextView = new TextView(getContext());
        nameTextView.setText(name);
        nameTextView.setTextSize(18);
        nameTextView.setTextColor(Color.BLACK);

        TextView ratingTextView = new TextView(getContext());
        ratingTextView.setText(rating);
        ratingTextView.setTextSize(16);
        ratingTextView.setTextColor(Color.parseColor("#FF5722"));

        TextView phoneTextView = new TextView(getContext());
        phoneTextView.setText(phone);
        phoneTextView.setTextSize(14);
        phoneTextView.setTextColor(Color.parseColor("#757575"));

        textLayout.addView(nameTextView);
        textLayout.addView(ratingTextView);
        textLayout.addView(phoneTextView);

        innerLayout.addView(imageView);
        innerLayout.addView(textLayout);

        cardView.addView(innerLayout);
        restaurantsContainer.addView(cardView);
    }
}
