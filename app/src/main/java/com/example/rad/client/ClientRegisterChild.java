package com.example.rad.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class ClientRegisterChild extends AppCompatActivity {

    TextInputEditText textInputEditTextChildName, textInputEditTextChildDOB, textInputEditTextGrade, textInputEditTextSchool;
    Button buttonChildSubmit;
    String name, dob, grade, school;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_child);

        textInputEditTextChildName = findViewById(R.id.txtInputChildName);
        textInputEditTextChildDOB = findViewById(R.id.txtInputChildDOB);
        textInputEditTextGrade = findViewById(R.id.txtInputChildGrade);
        textInputEditTextSchool = findViewById(R.id.txtInputChildSchool);

        buttonChildSubmit = findViewById(R.id.btnChildSubmit);

        progressBar = findViewById(R.id.progressBar);


        buttonChildSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                name = String.valueOf(textInputEditTextChildName.getText());
                dob = String.valueOf(textInputEditTextChildDOB.getText());
                grade = String.valueOf(textInputEditTextGrade.getText());
                school = String.valueOf(textInputEditTextSchool.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = getString(R.string.urlString) + "userRegisterChild.php";

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

                                        Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
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
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("dob", dob);
                        paramV.put("grade", grade);
                        paramV.put("school", school);
                        return paramV;
                    }
                };
                queue.add(stringRequest);


            }
        });


    }

}