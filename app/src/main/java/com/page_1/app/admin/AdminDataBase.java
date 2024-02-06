package com.page_1.app.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.page_1.app.R;
import com.page_1.app.recycleFaculty.userlist;
import com.page_1.app.recycleStudent.userlistFYCO;

public class AdminDataBase extends AppCompatActivity implements View.OnClickListener{
public CardView fycd;
public CardView sycd;
public CardView tycd;
public CardView FacultyCD;
TextView admindatabaseBack_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_base);
        getSupportActionBar().hide();
        initUI();
        intiListener();
    }

    private void initUI() {
        fycd=findViewById(R.id.admin_fy_cd);
        sycd=findViewById(R.id.admin_sy_cd);
        tycd=findViewById(R.id.admin_ty_cd);
        FacultyCD=findViewById(R.id.admin_Faculty_cd);
        admindatabaseBack_tv=findViewById(R.id.admindatabaseBack_tv);
    }
    private void intiListener() {
        fycd.setOnClickListener(this);
        sycd.setOnClickListener(this);
        tycd.setOnClickListener(this);
        FacultyCD.setOnClickListener(this);
        admindatabaseBack_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public static  String ClassYear;
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.admin_Faculty_cd:
                Intent intent=new Intent(this, userlist.class);
                startActivity(intent);
                break;
            case R.id.admin_fy_cd:
                ClassYear="FYCO";
                Intent intent1=new Intent(this, userlistFYCO.class);
                startActivity(intent1);
                break;
            case R.id.admin_sy_cd:
                ClassYear="SYCO";
                Intent intent2=new Intent(this, userlistFYCO.class);
                startActivity(intent2);
                break;
            case R.id.admin_ty_cd:
                ClassYear="TYCO";
                Intent intent3=new Intent(this, userlistFYCO.class);
                startActivity(intent3);
                break;
        }
    }
}
