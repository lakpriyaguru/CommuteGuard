package com.example.rad.driver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.rad.R;


public class DriverTrackingFragment extends Fragment {

    int longitude, latitude;
    Switch switchLocationUpdate;
    SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_tracking, container, false);

        switchLocationUpdate = view.findViewById(R.id.swLocationUpdate);

        sharedPreferences = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        switchLocationUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform task when switch is checked
                    performTask();
                } else {
                    // Optional: Perform task when switch is unchecked
                }
            }
        });


        return view;
    }

    private void performTask() {
/**
 RequestQueue queue = Volley.newRequestQueue(requireContext());
 String url = getString(R.string.urlString) + "driverLocationUpdate.php";

 StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
@Override public void onResponse(String response) {
try {
JSONObject jsonObject = new JSONObject(response);
String status = jsonObject.getString("status");
String message = jsonObject.getString("message");

if (status.equals("success")) {
longitude = 1000;
latitude = 1000;

Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
} else {
Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
}
} catch (JSONException e) {
Toast.makeText(requireContext(), " ", Toast.LENGTH_SHORT).show();

//throw new RuntimeException(e);
}

}
}, new Response.ErrorListener() {
@Override public void onErrorResponse(VolleyError error) {
error.printStackTrace();
}
}) {
@Override protected Map<String, String> getParams() {
Map<String, String> params = new HashMap<>();
// Add parameters to the request
params.put("email", sharedPreferences.getString("email", ""));
params.put("apiKey", sharedPreferences.getString("apiKey", ""));
params.put("longitude", String.valueOf(longitude)); // Add the state of the switch
params.put("latitude", String.valueOf(latitude)); // Add the state of the switch
return params;
}
};
 queue.add(stringRequest);

 */
    }
}