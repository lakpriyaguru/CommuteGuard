package com.example.rad.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rad.R;

public class ClientEditChildDetails extends AppCompatActivity {

    Button buttonChildSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_child_details);

        buttonChildSubmit = findViewById(R.id.btnChildSubmit);

        buttonChildSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientProfileFragment.class);
                startActivity(intent);
                finish();
            }
        });


    }
}