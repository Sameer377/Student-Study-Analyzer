package com.page_1.app.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.page_1.app.R;

public class student_URL_verification extends AppCompatActivity {
    private Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__url_verification);
        getSupportActionBar().hide();
        button5=(Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openstudent_login();
            }
        });
    }
    public void openstudent_login()
    {
        Intent intent = new Intent(this,student_login.class);
        startActivity(intent);
    }
}
