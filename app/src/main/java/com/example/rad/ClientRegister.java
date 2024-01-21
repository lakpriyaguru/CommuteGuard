package com.example.rad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.textfield.TextInputEditText;


import java.util.HashMap;
import java.util.Map;

public class ClientRegister extends AppCompatActivity {


    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonRegister;
    String name, email, password;
    TextView textViewLoginNow;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        textInputEditTextName = findViewById(R.id.txtInputName);
        textInputEditTextEmail = findViewById(R.id.txtInputEmail);
        textInputEditTextPassword = findViewById(R.id.txtInputPassword);
        buttonRegister = findViewById(R.id.btnRegister);
        textViewLoginNow = findViewById(R.id.txtViewLoginNow);
        progressBar = findViewById(R.id.progressBar);




        textViewLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                name = String.valueOf(textInputEditTextName.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.5/rad/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }
}