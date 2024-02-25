package com.example.rad.driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DriverPickupFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DataModel> dataholder;

    //fragment_driver_pickup

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_pickup, container, false);

        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = getString(R.string.urlString) + "shiftSyncM.php";

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
                        JSONArray childNamesArray = jsonResponse.getJSONArray("childNames");

                        for (int i = 0; i < schoolNamesArray.length(); i++) {
                            DataModel ob = new DataModel(childNamesArray.getString(i), schoolNamesArray.getString(i));
                            dataholder.add(ob);
                        }
                        recyclerView.setAdapter(new MyAdapter(dataholder));
                        // Set the adapter after the data is fetched and added to dataholder
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(requireContext(), "One or more fields are incorrectly filled, Try Again", Toast.LENGTH_SHORT).show();
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

        // Note: Adapter setting moved inside the onResponse() method


        return view;
    }

}