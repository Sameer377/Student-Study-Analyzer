package com.page_1.app.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.page_1.app.MainLogin;
import com.page_1.app.R;

import static com.page_1.app.student.student_menu.Email;
import static com.page_1.app.student.student_menu.Enroll;
import static com.page_1.app.student.student_menu.contact;
import static com.page_1.app.student.student_menu.fullName;

public class student_profile extends AppCompatActivity implements View.OnClickListener {
    private Button btn_stud_logout, btn_stud_cancel;
    private TextInputEditText tf_stud_fullname;
    private TextInputEditText tf_stud_contact;
    private TextInputEditText tf_stud_Email;
    private TextInputEditText tf_stud_Enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getSupportActionBar().hide();
        initUI();
        initListener();
        fillDetails();
    }



    public void initUI() {
        btn_stud_cancel = findViewById(R.id.btn_stud_cancel);
        btn_stud_logout = findViewById(R.id.btn_stud_logout);
        tf_stud_fullname=findViewById(R.id.tf_stud_fullname);
        tf_stud_contact=findViewById(R.id.tf_stud_contact);
        tf_stud_Email=findViewById(R.id.tf_stud_Email);
        tf_stud_Enroll=findViewById(R.id.tf_stud_Enroll);

    }

    public void initListener() {
        btn_stud_cancel.setOnClickListener(this);
        btn_stud_logout.setOnClickListener(this);
    }



    private void fillDetails() {
        tf_stud_fullname.setText(fullName);
        tf_stud_contact.setText(contact);
        tf_stud_Email.setText(Email);
        tf_stud_Enroll.setText(Enroll);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stud_cancel:
                finish();
                break;
            case R.id.btn_stud_logout:
                SharedPreferences.Editor editor2= MainLogin.getGloblevar().edit();
                editor2.clear();
                editor2.commit();
                Intent intent4 = new Intent(student_profile.this, MainLogin.class);
                startActivity(intent4);
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();

                student_menu.st.finish();
                break;
        }
    }
}