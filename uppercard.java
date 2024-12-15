package com.resto.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class uppercard extends RelativeLayout implements OnMapReadyCallback {

    private ImageView imageView;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private MapView mapView;
    private GoogleMap gMap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    public uppercard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public uppercard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public uppercard(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.uppercard, this, true);

        imageView = findViewById(R.id.card_image);
        nameTextView = findViewById(R.id.card_name);
        descriptionTextView = findViewById(R.id.card_description);
        mapView = findViewById(R.id.card_map);

        Bundle mapViewBundle = null;
        if (context instanceof AppCompatActivity) {
            mapViewBundle = ((AppCompatActivity) context).getIntent().getExtras();
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    public void setLocation(double lat, double lon) {
        if (gMap != null) {
            LatLng location = new LatLng(lat, lon);
            gMap.clear();
            gMap.addMarker(new MarkerOptions().position(location).title(nameTextView.getText().toString()));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
    }

    // MapView lifecycle methods
    public void onResume() {
        mapView.onResume();
    }

    public void onStart() {
        mapView.onStart();
    }

    public void onStop() {
        mapView.onStop();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onDestroy() {
        mapView.onDestroy();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
}
