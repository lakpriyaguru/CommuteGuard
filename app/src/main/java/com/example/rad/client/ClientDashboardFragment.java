package com.example.rad.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
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


public class ClientDashboardFragment extends Fragment {

    Button buttonShiftSubmit;
    Switch switchMorningShift, switchAfternoonShift;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_dashboard, container, false);

        buttonShiftSubmit = view.findViewById(R.id.btnSubmitShift);

        switchMorningShift = view.findViewById(R.id.swMorningShift);
        switchAfternoonShift = view.findViewById(R.id.swAfternoonShift);

        sharedPreferences = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        buttonShiftSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shiftMorning = switchMorningShift.isChecked();
                boolean shiftAfternoon = switchAfternoonShift.isChecked();

                // fetching user details from the database
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url = getString(R.string.urlString) + "userShiftSubmit.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (status.equals("success")) {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        // Add parameters to the request
                        params.put("email", sharedPreferences.getString("email", ""));
                        params.put("apiKey", sharedPreferences.getString("apiKey", ""));
                        params.put("shiftMorning", String.valueOf(shiftMorning)); // Add the state of the switch
                        params.put("shiftAfternoon", String.valueOf(shiftAfternoon)); // Add the state of the switch
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });


        return view;
    }
}