package com.example.rad.driver;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.rad.client.ClientLogin;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverLogin extends AppCompatActivity {

    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonDriverLogin;
    String name, email, password, apiKey;
    TextView textViewUserLogin;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        textInputEditTextEmail = findViewById(R.id.txtInputEmail);
        textInputEditTextPassword = findViewById(R.id.txtInputPassword);
        buttonDriverLogin = findViewById(R.id.btnDriverLogin);
        textViewUserLogin = findViewById(R.id.txtViewClientLogin);
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        textViewUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
                startActivity(intent);
                finish();
            }
        });


        buttonDriverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);

                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = getString(R.string.urlString) + "driverLogin.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (status.equals("success")) {
                                name = jsonObject.getString("name");
                                email = jsonObject.getString("email");
                                apiKey = jsonObject.getString("apiKey");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logged", "true");
                                editor.putString("userType", "driver");
                                editor.putString("name", name);
                                editor.putString("email", email);
                                editor.putString("apiKey", apiKey);
                                editor.apply();

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), DriverMainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
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