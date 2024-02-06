package com.page_1.app.admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;

public class AdminPassword extends Dialog {
    public LinearLayout adminlin;
    public TextInputEditText adminnameET,admincontactET,adminemailET;
    public Activity a;
    Button savechanges,changepassword,Verify;
    public TextInputLayout adminEmail,admincontact;
    ImageView backadmindetails;
    public AdminPassword(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.a = a;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog);

//        getWindow().setLayout(1000,1250);
        initUI();
        setDetails();
        setCancelable(false);
    }
    ProgressBar progress;

String CurrentPassword;
    private void setDetails() {

        progress.setVisibility(View.VISIBLE);
        DatabaseReference Name= FirebaseDatabase.getInstance().getReference("Admin").child("Password");
        Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                CurrentPassword=(String)snapshot.getValue();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void initUI() {
        progress=findViewById(R.id.adminpr);
        adminemailET=findViewById(R.id.adminemailET);
        adminnameET=findViewById(R.id.adminnameET);
        admincontactET=findViewById(R.id.admincontactET);
        savechanges=findViewById(R.id.savechanges);
        changepassword=findViewById(R.id.changepass);
        backadmindetails=findViewById(R.id.backadmindetails);
        adminEmail=findViewById(R.id.adminEmail);
        admincontact=findViewById(R.id.admincontact);
        Verify=findViewById(R.id.verify);
        backadmindetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EditedName=adminnameET.getText().toString().trim();
                String EditedContact=admincontactET.getText().toString().trim();
                String EditedEmail=adminemailET.getText().toString().trim();
                if (EditedName.isEmpty()) {
                    adminnameET.setError("username Required");
                    adminnameET.requestFocus();

                    return;
                }
//                else if (EditedContact.isEmpty()) {
//                    admincontactET.setError("username Required");
//                    admincontactET.requestFocus();
//
//                    return;
//                }
//                else if (EditedEmail.isEmpty()) {
//                    adminemailET.setError("username Required");
//                    adminemailET.requestFocus();
//
//                    return;
//                }
                else{
                   if(EditedName.equals(CurrentPassword)){
                       admincontact.setVisibility(View.VISIBLE);
                       adminEmail.setVisibility(View.VISIBLE);
                       adminlin.setVisibility(View.VISIBLE);
                       Verify.setVisibility(View.INVISIBLE);
                   }
                }
            }
        });
    }




}
