package com.page_1.app;

import androidx.appcompat.app.AppCompatActivity;
import com.page_1.app.admin.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.page_1.app.admin.adminlogin;
import com.page_1.app.student.student_login;
import com.page_1.app.teacher.LoginTeacherActivity;

public class DashboardActivity extends AppCompatActivity {
private Button button3;
private Button button4;
private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        button4=(Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openteacher_login();
            }
        });
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openstudent_login();
            }
        });
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openadminlogin();
            }

        });
    }
    public void openadminlogin(){
        Intent intent = new Intent(this,adminlogin.class);
        startActivity(intent);
//        finish();
    }
    public void openteacher_login()
    {
        Intent intent = new Intent(this, LoginTeacherActivity.class);
        startActivity(intent);
//        finish();
    }
    public void openstudent_login()
    {
        Intent intent = new Intent(this, student_login.class);
        startActivity(intent);
//        finish();
    }
}
