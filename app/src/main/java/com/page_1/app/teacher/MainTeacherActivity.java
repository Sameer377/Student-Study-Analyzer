package com.page_1.app.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.DisplayFiles.TeacherAssesment;
import com.page_1.app.MainLogin;
import com.page_1.app.R;
import com.page_1.app.admin.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainTeacherActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rel_teacher_profile;
    private RelativeLayout rel_teacher_upload;
    private RelativeLayout rel_teacher_updates;
    private RelativeLayout rel_teacher_Elearn;
    private TextView tv_teacher_menu_year;
    private TextView tv_teacher_menu_subject;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    public static Activity fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);
        getSupportActionBar().hide();
        initUI();
        initListener();
        setDetails();
    }
    public void initUI() {
        rel_teacher_profile = findViewById(R.id.rel_teacher_profile);
        rel_teacher_upload = findViewById(R.id.rel_teacher_upload);
        rel_teacher_Elearn=findViewById(R.id.rel_teacher_ELearn);
        rel_teacher_updates = findViewById(R.id.rel_teacher_updates);
        tv_teacher_menu_subject=findViewById(R.id.tv_teacher_menu_subject);
        tv_teacher_menu_year=findViewById(R.id.tv_teacher_menu_year);
        fa = this;
    }

    public void initListener() {
        rel_teacher_profile.setOnClickListener(this);
        rel_teacher_upload.setOnClickListener(this);
        rel_teacher_updates.setOnClickListener(this);
        rel_teacher_Elearn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_teacher_profile:
                Intent intent = new Intent(this, TeacherProfileActivity.class);
                startActivity(intent);

                break;
            case R.id.rel_teacher_upload:
                Intent intent1 = new Intent(this, teacher_upload.class);
                startActivity(intent1);
                break;
            case R.id.rel_teacher_updates:
                Intent intent2 = new Intent(this, FacultyUpdates.class);
                startActivity(intent2);
                break;
            case R.id.rel_teacher_ELearn:
                Intent intent3=new Intent(MainTeacherActivity.this, TeacherAssesment.class);
                startActivity(intent3);
                break;

        }
    }
    public static String teachingYear;
    public static String  fullName;
    public static String  contact;
    public static String  Email;
    public static String  SubjectName;

    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(MainTeacherActivity.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    private void setDetails() {
        openProgressDialog();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Faculty");
        UserID = user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                     teachingYear = userProfile.TeachingYear;
                     fullName = userProfile.fullname;
                     contact = userProfile.contact;
                     Email= userProfile.Email;
                     SubjectName=userProfile.subjectName;
                    Log.i("TAG","name...................:  "+SubjectName);
                    tv_teacher_menu_subject.setText(SubjectName);
                    tv_teacher_menu_year.setText(teachingYear);
                    progress.dismiss();
                }else {
                    new SweetAlertDialog(MainTeacherActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Details not found!!! ")
                            .setContentText("Logout all accounts .then try again.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            finish();
                            Intent intent = new Intent(MainTeacherActivity.this,MainLogin.class);
                            startActivity(intent);
                        }
                    }).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.dismiss();
            }
        });

    }
//back press alert
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainTeacherActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_eye);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
