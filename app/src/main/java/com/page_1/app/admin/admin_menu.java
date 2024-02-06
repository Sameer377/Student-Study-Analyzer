package com.page_1.app.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.page_1.app.MainLogin;
import com.page_1.app.R;

public class admin_menu extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rel_faculty_details;
    private RelativeLayout rel_student_details;
    private RelativeLayout rel_admin_about;
    private RelativeLayout rel_admin_database;
    private LinearLayout mainlin;
    private FrameLayout FAdmin;
    ImageButton Logout_btn;
    public static Activity add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        getSupportActionBar().hide();
        initUi();
        initListener();
        add=admin_menu.this;
    }

    private void initUi() {
        FAdmin=findViewById(R.id.FAdmin);
        rel_faculty_details=(RelativeLayout) findViewById(R.id.rel_faculty_details);
        rel_student_details=(RelativeLayout) findViewById(R.id.rel_student_details);
        rel_admin_database=(RelativeLayout) findViewById(R.id.rel_admin_database);
        rel_admin_about=(RelativeLayout) findViewById(R.id.rel_admin_about);
        Logout_btn=findViewById(R.id.imageButton);
        mainlin=findViewById(R.id.linearLayout2);
    }
    private void initListener() {
        FAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDetailsDialog cdd=new AdminDetailsDialog(admin_menu.this);
                cdd.getWindow().setBackgroundDrawableResource(R.drawable.curve_dialog);

                cdd.show();
            }
        });
        rel_faculty_details.setOnClickListener(this);
        rel_student_details.setOnClickListener(this);
        rel_admin_database.setOnClickListener(this);
        rel_admin_about.setOnClickListener(this);
        Logout_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rel_faculty_details:
                Intent intent = new Intent(this,admin_faculty_details.class);
                startActivity(intent);
                break;
            case R.id.rel_student_details:
                Intent intent1 = new Intent(this,admin_student_details.class);
                startActivity(intent1);
                break;
            case R.id.rel_admin_about:
                Intent intent2 = new Intent(this,FeesConsolAdmin.class);
                startActivity(intent2);
                break;
            case R.id.rel_admin_database:
                Intent intent3 = new Intent(this,AdminDataBase.class);
                startActivity(intent3);
                break;
            case R.id.imageButton:
                final SharedPreferences sharedPreferences=getSharedPreferences("Data", Context.MODE_PRIVATE);
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(admin_menu.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to Logout?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // When the user click yes button
                                        // then app will close
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        Intent intent4 = new Intent(admin_menu.this, MainLogin.class);
                                        startActivity(intent4);
                                        finish();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();

                break;
        }
    }
    //back press alert
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(admin_menu.this);
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
