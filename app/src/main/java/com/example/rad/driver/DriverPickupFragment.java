package com.example.rad.driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rad.R;

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


        DataModel ob1 = new DataModel("Angular", "Web Application");
        dataholder.add(ob1);

        DataModel ob2 = new DataModel("C Programming", "Embed Programming");
        dataholder.add(ob2);

        DataModel ob3 = new DataModel("C++ Programming", "Embed Programming");
        dataholder.add(ob3);

        DataModel ob4 = new DataModel(".NET Programming", "Desktop and Web Programming");
        dataholder.add(ob4);

        DataModel ob5 = new DataModel("Java Programming", "Desktop and Web Programming");
        dataholder.add(ob5);

        DataModel ob6 = new DataModel("Magento", "Web Application Framework");
        dataholder.add(ob6);

        DataModel ob7 = new DataModel("NodeJS", "Web Application Framework");
        dataholder.add(ob7);

        DataModel ob8 = new DataModel("Python", "Desktop and Web Programming");
        dataholder.add(ob8);

        DataModel ob9 = new DataModel("Shopify", "E-Commerce Framework");
        dataholder.add(ob9);

        DataModel ob10 = new DataModel("Wordpress", "WebApplication Framewrok");
        dataholder.add(ob10);

        recyclerView.setAdapter(new MyAdapter(dataholder));

        return view;


    }
}