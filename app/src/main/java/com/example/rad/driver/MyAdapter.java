package com.example.rad.driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rad.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myviewholder> {
    ArrayList<DataModel> dataholder;

    public MyAdapter(ArrayList<DataModel> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.myviewholder holder, int position) {
        holder.name.setText(dataholder.get(position).getName());
        holder.school.setText(dataholder.get(position).getSchool());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView name, school;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtViewName);
            school = itemView.findViewById(R.id.txtViewSchool);
        }
    }
}
