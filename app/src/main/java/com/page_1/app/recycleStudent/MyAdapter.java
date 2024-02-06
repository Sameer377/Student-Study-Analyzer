package com.page_1.app.recycleStudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.page_1.app.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<com.page_1.app.recycleStudent.User> list;


    public MyAdapter(Context context, ArrayList<com.page_1.app.recycleStudent.User> list) {
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        com.page_1.app.recycleStudent.User user = list.get(position);
        holder.firstName.setText(user.getFullname());
        holder.lastName.setText(user.getEmail());
        holder.age.setText(user.getPass());
        holder.age1.setText(user.getContact());
        holder.age2.setText(user.getEnroll());
        holder.age3.setText(user.getYear());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, age,age1,age2,age3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvfirstName);
            lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.tvage);
            age1 = itemView.findViewById(R.id.tvage1);
            age2 = itemView.findViewById(R.id.tvage2);
            age3 = itemView.findViewById(R.id.tvage3);

        }
    }

}
