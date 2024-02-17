package com.example.rad.client;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rad.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientRegister extends AppCompatActivity {


    TextInputEditText textInputEditTextFullName, textInputEditTextEmail, textInputEditTextPassword, textInputEditTextNIC, textInputEditTextAddress, textInputEditTextContact;
    Button buttonClientRegister;
    String name, email, password;
    String nic, contact, address;
    TextView textViewLoginNow;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);


        textInputEditTextFullName = findViewById(R.id.txtInputFullName);
        textInputEditTextEmail = findViewById(R.id.txtInputEmail);
        textInputEditTextPassword = findViewById(R.id.txtInputPassword);

        textInputEditTextNIC = findViewById(R.id.txtInputNIC);
        textInputEditTextAddress = findViewById(R.id.txtInputAddress);
        textInputEditTextContact = findViewById(R.id.txtInputContact);

        buttonClientRegister = findViewById(R.id.btnClientRegister);

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

        buttonClientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                name = String.valueOf(textInputEditTextFullName.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                nic = String.valueOf(textInputEditTextNIC.getText());
                address = String.valueOf(textInputEditTextAddress.getText());
                contact = String.valueOf(textInputEditTextContact.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = getString(R.string.urlString) + "userRegister.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");

                                    if (status.equals("success")) {

                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), ClientRegisterChild.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                    }
                                } catch (JSONException e) {
                                    //throw new RuntimeException(e);
                                    Toast.makeText(getApplicationContext(), "One or more fields are incorrectly filled, Try Again", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        paramV.put("nic", nic);
                        paramV.put("address", address);
                        paramV.put("contact", contact);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }
}