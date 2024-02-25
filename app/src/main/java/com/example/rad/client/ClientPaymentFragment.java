package com.example.rad.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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


public class ClientPaymentFragment extends Fragment {

    TextView textViewAmount;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_payment, container, false);


        textViewAmount = view.findViewById(R.id.txtViewAmount);


        sharedPreferences = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        // fetching user details from the database
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = getString(R.string.urlString) + "userPaymentSync.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    String amount = jsonResponse.getString("totalPayAmount");

                    // Display user details in relevant TextViews
                    textViewAmount.setText("Rs. " + amount);
                }
                //catch (JSONException e) {
                //    e.printStackTrace();
                // Handle JSON parsing error
                //}
                catch (JSONException e) {
                    Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show();
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


        return view;
    }
}