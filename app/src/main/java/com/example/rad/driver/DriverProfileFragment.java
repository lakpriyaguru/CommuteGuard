package com.example.rad.driver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rad.MainActivity;
import com.example.rad.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverProfileFragment extends Fragment {


    Button buttonLogout;
    TextView textViewName, textViewEmail, textViewContact;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        buttonLogout = view.findViewById(R.id.btnLogout);

        textViewName = view.findViewById(R.id.txtViewName);
        textViewEmail = view.findViewById(R.id.txtViewEmail);
        textViewContact = view.findViewById(R.id.txtViewContact);


        sharedPreferences = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);


        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = getString(R.string.urlString) + "driverProfile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    String name = jsonResponse.getString("name");
                    String email = jsonResponse.getString("email");
                    String contact = jsonResponse.getString("contact");

                    // Display user details in relevant TextViews
                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewContact.setText(contact);
                }
                //catch (JSONException e) {
                //    e.printStackTrace();
                // Handle JSON parsing error
                //}
                catch (JSONException e) {
                    throw new RuntimeException(e);
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

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url = getString(R.string.urlString) + "driverLogout.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("success")) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged", "false");
                                    editor.putString("userType", "driver");
                                    editor.putString("name", "");
                                    editor.putString("email", "");
                                    editor.putString("apiKey", "");
                                    editor.apply();

                                    Intent intent = new Intent(requireContext(), MainActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();
                                } else {
                                    Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show();
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

            }
        });


        return view;
    }
}