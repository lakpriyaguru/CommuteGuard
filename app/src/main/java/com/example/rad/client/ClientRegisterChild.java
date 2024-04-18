package com.example.rad.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientRegisterChild extends AppCompatActivity {

    TextInputEditText textInputEditTextChildName, textInputEditTextChildDOB, textInputEditTextGrade;
    Button buttonChildSubmit;
    String name, dob, grade;
    String school = "";
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayList<String> schoolNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_child);

        textInputEditTextChildName = findViewById(R.id.txtInputChildName);
        textInputEditTextChildDOB = findViewById(R.id.txtInputChildDOB);
        textInputEditTextGrade = findViewById(R.id.txtInputChildGrade);

        buttonChildSubmit = findViewById(R.id.btnChildSubmit);

        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        ArrayList<String> schoolNames = new ArrayList<>();
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, schoolNames);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                school = parent.getItemAtPosition(position).toString();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = getString(R.string.urlString) + "schoolSync.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    String message = jsonResponse.getString("message");

                    if (status.equals("success")) {
                        JSONArray schoolNamesArray = jsonResponse.getJSONArray("schoolNames");

                        for (int i = 0; i < schoolNamesArray.length(); i++) {
                            schoolNames.add(schoolNamesArray.getString(i));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "One or more fields are incorrectly filled, Try Again", Toast.LENGTH_SHORT).show();
                    //throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

        };
        queue.add(stringRequest);


        buttonChildSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                name = String.valueOf(textInputEditTextChildName.getText());
                dob = String.valueOf(textInputEditTextChildDOB.getText());
                grade = String.valueOf(textInputEditTextGrade.getText());

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
                                    Toast.makeText(getApplicationContext(), school, Toast.LENGTH_SHORT).show();
                                    //throw new RuntimeException(e);
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
                        paramV.put("email", sharedPreferences.getString("email", ""));
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