package com.example.rad.driver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rad.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverMainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    DriverViewPagerAdapter driverViewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        bottomNavigationView = findViewById(R.id.driverBottomNav);
        viewPager2 = findViewById(R.id.driverViewPager);

        driverViewPagerAdapter = new DriverViewPagerAdapter(this);
        viewPager2.setAdapter(driverViewPagerAdapter);

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "false").equals("false")) {
            Intent intent = new Intent(getApplicationContext(), DriverLogin.class);
            startActivity(intent);
            finish();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.driverBottomTracking) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.driverBottomPickup) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.driverBottomDropoff) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.driverBottomProfile) {
                    viewPager2.setCurrentItem(3);
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.driverBottomTracking).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.driverBottomPickup).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.driverBottomDropoff).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.driverBottomProfile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });


    }
}