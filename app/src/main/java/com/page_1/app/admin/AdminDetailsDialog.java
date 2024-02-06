package com.page_1.app.admin;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;

import static com.page_1.app.admin.admin_menu.add;


public class AdminDetailsDialog extends Dialog {
    public TextInputLayout adminname,admincontact,adminemail,currentpassword,newpassword,confirmpassword;
    public TextInputEditText adminnameET,admincontactET,adminemailET,Currentpass,newpass,conpass;
    public Activity a;
    public TextView adminDtv;
    LinearLayout adminbtlin,addlin2;
    Button savechanges,changepassword,verify,canceladd;
    public RelativeLayout rel_admin_details;
    ImageView backadmindetails;
    private String Passwordstr;

    public AdminDetailsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.a = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindialogbox);

//        getWindow().setLayout(1000,1250);
initUI();
setDetails();
        setCancelable(false);
    }
    ProgressBar progress;


    private void setDetails() {

        progress.setVisibility(View.VISIBLE);
       DatabaseReference Name= FirebaseDatabase.getInstance().getReference("Admin").child("Name");
        Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adminnameET.setText((CharSequence) snapshot.getValue());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference contact= FirebaseDatabase.getInstance().getReference("Admin").child("Contact");
        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                admincontactET.setText((CharSequence) snapshot.getValue());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference Password= FirebaseDatabase.getInstance().getReference("Admin").child("Password");
        Password.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Passwordstr=(String) snapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference EmailAdd= FirebaseDatabase.getInstance().getReference("Admin").child("UserName");
        EmailAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adminemailET.setText((CharSequence) snapshot.getValue());
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void initUI() {
        newpass=findViewById(R.id.newpassET);
        conpass=findViewById(R.id.confirmpass);
        Currentpass=findViewById(R.id.CurrentpassET);
        canceladd=findViewById(R.id.canceladd);
        addlin2=findViewById(R.id.addlin2);
        verify=findViewById(R.id.verify);
        adminbtlin=findViewById(R.id.adminbtlin);
        adminDtv=findViewById(R.id.adminDtv);
        adminname=findViewById(R.id.adminname);
        admincontact=findViewById(R.id.admincontact);
        adminemail=findViewById(R.id.adminEmail);
        progress=findViewById(R.id.adminpr);
        currentpassword=findViewById(R.id.Currentpasslay);
        newpassword=findViewById(R.id.newpasslay);
        confirmpassword=findViewById(R.id.confirmpasslay);
        adminemailET=findViewById(R.id.adminemailET);
        adminnameET=findViewById(R.id.adminnameET);
        admincontactET=findViewById(R.id.admincontactET);
        savechanges=findViewById(R.id.savechanges);
        changepassword=findViewById(R.id.changepass);
        backadmindetails=findViewById(R.id.backadmindetails);
        backadmindetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        savechanges.setOnClickListener(new View.OnClickListener() {
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
               else if (EditedContact.isEmpty()) {
                    admincontactET.setError("username Required");
                    admincontactET.requestFocus();

                    return;
                }
               else if (EditedEmail.isEmpty()) {
                    adminemailET.setError("username Required");
                    adminemailET.requestFocus();

                    return;
                }else{
                    DatabaseReference Name= FirebaseDatabase.getInstance().getReference("Admin");
                            Name.child("Name").setValue(EditedName);
                    DatabaseReference contact= FirebaseDatabase.getInstance().getReference("Admin");
                    contact.child("Contact").setValue(EditedContact);
                    DatabaseReference EmailAdd= FirebaseDatabase.getInstance().getReference("Admin");
                    EmailAdd.child("UserName").setValue(EditedEmail);
                    Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                }
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
changeToPassword();
            }
        });
        canceladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToDetails();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curpass=Currentpass.getText().toString().trim();
                String newpass_str=newpass.getText().toString().trim();
                String confipass_str=conpass.getText().toString().trim();
                if (curpass.isEmpty()) {
                    Currentpass.setError("Required");
                    Currentpass.requestFocus();
                    return;
                }
               else if (newpass_str.isEmpty()) {
                    newpass.setError("Required");
                    newpass.requestFocus();
                    return;
                }
               else if (confipass_str.isEmpty()) {
                    conpass.setError("Required");
                    conpass.requestFocus();
                    return;
                }
                else{
                    if(curpass.equals(Passwordstr)){
                        if(newpass_str.equals(confipass_str)){
                            DatabaseReference EmailAdd= FirebaseDatabase.getInstance().getReference("Admin");
                            EmailAdd.child("Password").setValue(confipass_str);
                            Toast.makeText(getContext(),"password Changed",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"Confirm password\n Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(),"password Not Matched",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

public void changeToDetails(){
    addlin2.setVisibility(View.INVISIBLE);
    adminDtv.setText("Admin Details");
    adminbtlin.setVisibility(View.VISIBLE);
    adminname.setVisibility(View.VISIBLE);
    admincontact.setVisibility(View.VISIBLE);
    adminemail.setVisibility(View.VISIBLE);
    currentpassword.setVisibility(View.GONE);
    newpassword.setVisibility(View.GONE);
    confirmpassword.setVisibility(View.GONE);
}
public  void changeToPassword(){
    addlin2.setVisibility(View.VISIBLE);
    adminDtv.setText("Change Password");
    adminbtlin.setVisibility(View.GONE);
        adminname.setVisibility(View.GONE);
        admincontact.setVisibility(View.GONE);
        adminemail.setVisibility(View.GONE);
        currentpassword.setVisibility(View.VISIBLE);
        newpassword.setVisibility(View.VISIBLE);
        confirmpassword.setVisibility(View.VISIBLE);
}

}
