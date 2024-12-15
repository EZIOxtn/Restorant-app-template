package com.resto.myapplication;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class ResorantActivity extends AppCompatActivity {
    private LinearLayout selectedNavItem;
    public static LinearLayout restaurantsContainer;
    private  Context ctx;
    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resorant);
        Window window = this.getWindow();
        ctx = getApplicationContext();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorSelected));
        LinearLayout navHome = findViewById(R.id.nav_home);
        LinearLayout navMenu = findViewById(R.id.nav_menu);
        LinearLayout navcart = findViewById(R.id.nav_cart);
        LinearLayout navabout = findViewById(R.id.nav_about_us);

        navHome.setOnClickListener(navClickListener);
        navMenu.setOnClickListener(navClickListener);
        navcart.setOnClickListener(navClickListener);
        navabout.setOnClickListener(navClickListener);

        restaurantsContainer = findViewById(R.id.restaurant_container);
        for (int i = 0; i < 10; i++) {
            RestaurantCardView cardView = new RestaurantCardView(this);
            cardView.setTitle("Restaurant " + i);
            cardView.setDescription("Description " + i);
            cardView.setImageResource(R.drawable.store);
            restaurantsContainer.addView(cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ResorantActivity", "Restaurant card clicked");
                    restaurantsContainer.addView(new uppercard(ResorantActivity.this));
                    restaurantsContainer.removeAllViews();
                    for (int j = 0; j < 5; j++) {
                        FoodCardView foodCardView = new FoodCardView(ResorantActivity.this); // Correct context
                        foodCardView.setFoodName("Food Item " + (j + 1));
                        foodCardView.setFoodDescription("Description for Food Item " + (j + 1));
                        foodCardView.setFoodImageResource(R.drawable.burger);
                        restaurantsContainer.addView(foodCardView);
                    }
                }
            });
        }
    }


    private void fetchAndDisplayRestaurants() {
        new ResFetcher().fetch(new ResFetcher.FetchRestaurantsCallback() {
            @Override
            public void onSuccess(List<RestorantCLS> restaurants) {
                for (RestorantCLS res : restaurants) {
                    RestaurantCardView cardView = new RestaurantCardView(ResorantActivity.this);
                    cardView.setTitle(res.getName());
                    cardView.setDescription(res.getDescription());
                    cardView.setImageResource(R.drawable.store);
                    restaurantsContainer.addView(cardView);

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("ResorantActivity", "Restaurant card clicked");
                            restaurantsContainer.removeAllViews();
                            for (int j = 0; j < 5; j++) {
                                FoodCardView foodCardView = new FoodCardView(ResorantActivity.this);
                                foodCardView.setFoodName("Food Item " + (j + 1));
                                foodCardView.setFoodDescription("Description for Food Item " + (j + 1));
                                foodCardView.setFoodImageResource(R.drawable.burger);
                                restaurantsContainer.addView(foodCardView);
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                FancyToast.makeText(ResorantActivity.this, "Error fetching data: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        });
    }
private  void testAddView() {
    restaurantsContainer = findViewById(R.id.restaurant_container);
    for (int i = 0; i < 10; i++) {
        RestaurantCardView cardView = new RestaurantCardView(this);
        cardView.setTitle("Restaurant " + i);
        cardView.setDescription("Description " + i);
        cardView.setImageResource(R.drawable.store);
        System.out.println("adding card n°"+i);
        restaurantsContainer.addView(cardView);
        System.out.println("card n°"+i+"should be added");

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ResorantActivity", "Restaurant card clicked");
                System.out.println("removing all views");
                restaurantsContainer.removeAllViews();
                System.out.println("adding cards");
                for (int j = 0; j < 5; j++) {
                    FoodCardView foodCardView = new FoodCardView(ResorantActivity.this); // Correct context
                    foodCardView.setFoodName("Food Item " + (j + 1));
                    foodCardView.setFoodDescription("Description for Food Item " + (j + 1));
                    foodCardView.setFoodImageResource(R.drawable.burger);
                    restaurantsContainer.addView(foodCardView);

                }
            }
        });
        restaurantsContainer.requestLayout();
        restaurantsContainer.invalidate();

}}
    private View.OnClickListener navClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            if (selectedNavItem != null) {
                selectedNavItem.setSelected(false);
            }
            v.setSelected(true);
            selectedNavItem = (LinearLayout) v;
            int id = v.getId();
            if (id == R.id.nav_home) {
                restaurantsContainer.removeAllViews();
                //fetchAndDisplayRestaurants();
System.out.println("starting adding cards");
RelativeLayout ucrd = new uppercard(restaurantsContainer.getContext());

                try {
                    restaurantsContainer.addView(ucrd);

                }catch (Exception e) {
                    Log.e("ex while getting card ",e.getMessage());
                }
                testAddView();
                System.out.println("cards should be added now");
            } else if (id == R.id.nav_menu) {
                restaurantsContainer.removeAllViews();
                loadLayout(R.layout.activity_menu);
            } else if (id == R.id.nav_cart) {
                restaurantsContainer.removeAllViews();
                loadLayout(R.layout.activity_cartlogic);
                Thread trh = new Thread(new Runnable() {
int k;
                    @Override
                    public void run() {
                        while (true){
                            NotificationHandler.showNotification(ctx, "havozli9","ive got a metal to play with in here, i feel RAHMA",R.drawable.channels4_profile, this.getClass());
                            try {
                                sleep(1000);
                                System.out.println("thead sleep for 1000 ms  loop N° " + k);
                                k++;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                // trh.start();     // notification test , test PASSED !!!!!!!!!!!!!!!!
            } else if (id == R.id.nav_about_us) {
                restaurantsContainer.removeAllViews();
                //loadLayout(R.layout.activity_settings);
                settingUI rlv = new settingUI(ResorantActivity.this);

                rlv.ctxgt(ResorantActivity.this);
                restaurantsContainer.removeAllViews();
                restaurantsContainer.addView(rlv);
            } else {
                restaurantsContainer.removeAllViews();
                FancyToast.makeText(ResorantActivity.this, "Unknown navigation item", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        }
    };

    private void loadLayout(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(layoutId, restaurantsContainer, false);
        restaurantsContainer.removeAllViews();
        restaurantsContainer.addView(view);
    }
}
