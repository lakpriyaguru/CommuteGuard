package com.example.rad.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientEditDetails extends AppCompatActivity {

    Button buttonSubmitEditDetails;
    TextView textViewName, textViewNIC, textViewAddress, textViewContact;
    String name, nic, contact, address, email;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_details);

        buttonSubmitEditDetails = findViewById(R.id.btnClientEditDetailsSubmit);

        textViewName = findViewById(R.id.txtInputFullName);
        textViewNIC = findViewById(R.id.txtInputNIC);
        textViewAddress = findViewById(R.id.txtInputAddress);
        textViewContact = findViewById(R.id.txtInputContact);

        sharedPreferences = getApplication().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = getString(R.string.urlString) + "userEditDetailsSync.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override


            public void onResponse(String response) {
                try {
                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    String message = jsonResponse.getString("message");

                    if (status.equals("success")) {

                        String name = jsonResponse.getString("name");
                        email = jsonResponse.getString("email");
                        String nic = jsonResponse.getString("nic");
                        String address = jsonResponse.getString("address");
                        String contact = jsonResponse.getString("contact");

                        textViewName.setText(name);
                        textViewNIC.setText(nic);
                        textViewAddress.setText(address);
                        textViewContact.setText(contact);
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
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("email", sharedPreferences.getString("email", ""));
                paramV.put("apiKey", sharedPreferences.getString("apiKey", ""));
                return paramV;
            }
        };
        queue.add(stringRequest);

        buttonSubmitEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = String.valueOf(textViewName.getText());
                nic = String.valueOf(textViewNIC.getText());
                address = String.valueOf(textViewAddress.getText());
                contact = String.valueOf(textViewContact.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = getString(R.string.urlString) + "userEditDetailsSubmit.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");

                                    if (status.equals("success")) {

                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
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