package com.page_1.app.teacher;

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

import static com.page_1.app.teacher.MainTeacherActivity.Email;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.contact;
import static com.page_1.app.teacher.MainTeacherActivity.fullName;

public class TeacherProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_logout, btn_cancel;
    private TextInputEditText tf_teacher_profile_name;
    private TextInputEditText tf_teacher_profile_contact;
    private TextInputEditText tf_teacher_profile_Email;
    private TextInputEditText tf_teacher_profile_Subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        getSupportActionBar().hide();
        initUI();
        initListener();
        fillDetails();
    }



    public void initUI() {
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_logout = findViewById(R.id.btn_logout);
        tf_teacher_profile_name=findViewById(R.id.tf_teacher_profile_name);
        tf_teacher_profile_contact=findViewById(R.id.tf_teacher_profile_contact);
        tf_teacher_profile_Email=findViewById(R.id.tf_teacher_profile_Email);
        tf_teacher_profile_Subject=findViewById(R.id.tf_teacher_profile_Subject);

    }

    public void initListener() {
        btn_cancel.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }



    private void fillDetails() {
        tf_teacher_profile_name.setText(fullName);
        tf_teacher_profile_contact.setText(contact);
        tf_teacher_profile_Email.setText(Email);
        tf_teacher_profile_Subject.setText(SubjectName);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_logout:
                SharedPreferences.Editor editor1=MainLogin.getGloblevar().edit();
                editor1.clear();
                editor1.commit();
                Intent intent4 = new Intent(TeacherProfileActivity.this, MainLogin.class);
                startActivity(intent4);
                Toast.makeText(TeacherProfileActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();

                MainTeacherActivity.fa.finish();
                break;
        }
    }
}
