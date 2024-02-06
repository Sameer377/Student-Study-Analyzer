package com.page_1.app.admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;

import java.util.ArrayList;

public class FeesConsolAdmin extends AppCompatActivity  implements View.OnClickListener {
    public CardView fycd;
    public CardView sycd;
    public CardView tycd;
    public CardView FacultyCD;
    public static String YEARR;
    ScrollView Scrollll;

    public static   ArrayList<String> Finalkey = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_consol_admin);
        AddActionBar();
        Toast.makeText(FeesConsolAdmin.this,"Loading..",Toast.LENGTH_LONG).show();
        initUI();
        intiListener();
        addValueToList1();
        addValueToList2();
        addValueToList3();
    }
    private void initUI() {
        fycd=findViewById(R.id.admin_fy_cd);
        sycd=findViewById(R.id.admin_sy_cd);
        tycd=findViewById(R.id.admin_ty_cd);
        FacultyCD=findViewById(R.id.admin_Faculty_cd);

        Scrollll=(ScrollView) findViewById(R.id.Scrollll);
    }
    private void intiListener() {
        fycd.setOnClickListener(this);
        sycd.setOnClickListener(this);
        tycd.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch(view.getId())
        {

            case R.id.admin_fy_cd:
                YEARR="FYCO";
              Finalkey=keys4;
              if (Finalkey.size()==0)
              {
                  Toast.makeText(this, "Wait\ntry again!!", Toast.LENGTH_SHORT).show();
              }
              else {

                  Intent intent =new Intent(FeesConsolAdmin.this, FeesDetails.class);
                  startActivity(intent);

              }

                break;
            case R.id.admin_sy_cd:
                YEARR="SYCO";
               Finalkey=keys55;
                if (Finalkey.size()==0)
                {
                    Toast.makeText(this, "Wait\ntry again!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(FeesConsolAdmin.this, FeesDetails.class);
                    startActivity(intent1);
                }
                break;
            case R.id.admin_ty_cd:
                YEARR="TYCO";
               Finalkey=keys6;
                if (Finalkey.size()==0)
                {
                    Toast.makeText(this, "Wait\ntry again!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent2 = new Intent(FeesConsolAdmin.this, FeesDetails.class);
                    startActivity(intent2);
                    break;
                }
        }
    }
    public  ArrayList<String> keys4 = new ArrayList<String>();
    public void addValueToList1()
    {

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Fees").child("First year");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    keys4.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference2.addListenerForSingleValueEvent(eventListener);
    }
    public  ArrayList<String> keys55 = new ArrayList<String>();
    public void addValueToList2()
    {

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Fees").child("Second year");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    keys55.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference2.addListenerForSingleValueEvent(eventListener);
    }
    public  ArrayList<String> keys6 = new ArrayList<String>();
    public void addValueToList3()
    {

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Fees").child("Third year");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    keys6.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference2.addListenerForSingleValueEvent(eventListener);
    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Admin");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C3D4E")));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
