package com.example.rad.client;

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

public class ClientMainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ClientViewPagerAdapter clientViewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        bottomNavigationView = findViewById(R.id.clientBottomNav);
        viewPager2 = findViewById(R.id.clientViewPager);

        clientViewPagerAdapter = new ClientViewPagerAdapter(this);
        viewPager2.setAdapter(clientViewPagerAdapter);

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "false").equals("false")) {
            Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
            startActivity(intent);
            finish();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.clientBottomDashboard) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.clientBottomLocation) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.clientBottomPayment) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.clientBottomProfile) {
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
                        bottomNavigationView.getMenu().findItem(R.id.clientBottomDashboard).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.clientBottomLocation).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.clientBottomPayment).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.clientBottomProfile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }
}