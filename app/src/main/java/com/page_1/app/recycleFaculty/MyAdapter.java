package com.page_1.app.recycleFaculty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.page_1.app.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<com.page_1.app.recycleFaculty.User> list;

    public MyAdapter(Context context, ArrayList<com.page_1.app.recycleFaculty.User> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_item2,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.firstName.setText(user.getFullname());
        holder.lastName.setText(user.getEmail());
        holder.age.setText(user.getPass());
        holder.age1.setText(user.getContact());
        holder.age2.setText(user.getSubjectName());
        holder.age3.setText(user.getTeachingYear());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, age,age1,age2,age3;
        ImageView Dbtn;
        private FirebaseAuth mAuth;
        public static String Email,password;
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
