package com.example.rad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if (sharedPreferences.getString("userType", "").equals("client")) {
            Intent intent = new Intent(getApplicationContext(), ClientMainActivity.class);
            startActivity(intent);
            finish();
        } else if (sharedPreferences.getString("userType", "").equals("driver")) {
            Intent intent = new Intent(getApplicationContext(), DriverMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
            startActivity(intent);
            finish();
        }

    }
}