package com.page_1.app.student;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.page_1.app.R;

public class FirstYearStudentElearn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_e_learn);
        getSupportActionBar().hide();
    }
}