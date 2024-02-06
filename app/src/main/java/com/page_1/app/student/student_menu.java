package com.page_1.app.student;

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
import com.page_1.app.MainLogin;
import com.page_1.app.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
//import com.page_1.app.admin.User;
//import com.page_1.app.student.User2;

public class student_menu extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    public RelativeLayout rel_student_ELearn;
    public RelativeLayout rel_student_profile;
    public RelativeLayout rel_student_updates;
    public RelativeLayout rel_student_Fees,rel_student_About;
    private TextView tv_stud_year;
    private RelativeLayout rel_student_upload;
    public static Activity st;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        getSupportActionBar().hide();
        initUI();
        initListener();
        setDetails();
//dialog
    }

    public void initUI() {
        rel_student_ELearn =(RelativeLayout) findViewById(R.id.rel_student_ELearn);
        rel_student_profile =(RelativeLayout) findViewById(R.id.rel_student_profile);
        rel_student_upload =(RelativeLayout) findViewById(R.id.rel_student_upload);
        rel_student_updates =(RelativeLayout) findViewById(R.id.rel_student_updates);
        rel_student_Fees=findViewById(R.id.rel_student_Fees);
        rel_student_About=findViewById(R.id.rel_student_About);
        tv_stud_year = (TextView) findViewById(R.id.tv_stud_year);
        st=this;
    }

    public void initListener() {
        rel_student_ELearn.setOnClickListener(this);
        rel_student_profile.setOnClickListener(this);
        rel_student_upload.setOnClickListener(this);
        rel_student_About.setOnClickListener(this);
        rel_student_Fees.setOnClickListener(this);
        rel_student_updates.setOnClickListener(student_menu.this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_student_About:
                Intent in = new Intent(student_menu.this,About.class);
                startActivity(in);
            break;
            case R.id.rel_student_ELearn:
                if(keys2.size()==0){
                    Toast.makeText(student_menu.this,"Loading",Toast.LENGTH_SHORT).show();
                }else{
                Intent intent = new Intent(this, DisplaySubjectsS.class);
                startActivity(intent);}
                break;
            case R.id.rel_student_profile:
                Intent intent1 = new Intent(this, student_profile.class);
                startActivity(intent1);
                break;
            case R.id.rel_student_upload:
                Intent intent2 = new Intent(this, StudentUpload.class);
                startActivity(intent2);
                break;
            case R.id.rel_student_updates:
                Intent intent3 = new Intent(this, StudentUpdates.class);
                startActivity(intent3);
                break;
            case R.id.rel_student_Fees:
                makepayment();
                break;
        }
    }

    public String getFeeAmmount() {
        return FeeAmmount;
    }

    public void setFeeAmmount(String feeAmmount) {
        FeeAmmount = feeAmmount;
    }

    public String FeeAmmount;
    public String checkFeesAmmount(){
        DatabaseReference  FeesAmmount_DBR;
        if(Year.equals("First year")){
            FeesAmmount_DBR= FirebaseDatabase.getInstance().getReference("FeesAmmount").child("FYCO");

        }
        else if(Year.equals("Second year")){
            FeesAmmount_DBR= FirebaseDatabase.getInstance().getReference("FeesAmmount").child("SYCO");

        }
        else {
            FeesAmmount_DBR= FirebaseDatabase.getInstance().getReference("FeesAmmount").child("TYCO");

        }

        FeesAmmount_DBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                setFeeAmmount(String.valueOf(snapshot.getValue()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return null;
    }

    Checkout checkout = new Checkout();
    private void makepayment()
    {


        checkout.setKeyID("rzp_test_o199CPrf6W0FwB");

        checkout.setImage(R.drawable.logo);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", fullName);
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", getFeeAmmount()+"00");//300 X 100
            options.put("prefill.email", Email);
            options.put("prefill.contact",contact);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    public static String  Year;
    public static String  fullName;
    public static String  contact;
    public static String  Email;
    public static String Enroll;
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(student_menu.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    String Studentclass="FYCO";
    private void setDetails() {
        openProgressDialog();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Student");
        UserID = user.getUid();
        reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User2 userProfile=snapshot.getValue(User2.class);
                if(userProfile != null){
                    fullName = userProfile.fullname;
                    contact = userProfile.Contact;
                    Email= userProfile.Email;
                    Year=userProfile.Year;
                    addValueToList();
                    Enroll=userProfile.Enroll;
                    tv_stud_year.setText(Year);
                    checkFeesAmmount();
                    Log.i("Tag","Name..............:"+fullName);
                    progress.dismiss();
                }
                else if(userProfile==null){
                    Studentclass="SYCO";
                    reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User2 userProfile=snapshot.getValue(User2.class);
                            if(userProfile != null){
                                fullName = userProfile.fullname;
                                contact = userProfile.Contact;
                                Email= userProfile.Email;
                                Year=userProfile.Year;
                                Enroll=userProfile.Enroll;
                                checkFeesAmmount();
                                addValueToList();
                                tv_stud_year.setText(Year);
                                Log.i("Tag","Name..............:"+fullName);
                                progress.dismiss();
                            }
                            else if(userProfile==null){
                                Studentclass="TYCO";
                                reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User2 userProfile=snapshot.getValue(User2.class);
                                        if(userProfile != null){
                                            fullName = userProfile.fullname;
                                            contact = userProfile.Contact;
                                            Email= userProfile.Email;
                                            Year=userProfile.Year;
                                            addValueToList();
                                            checkFeesAmmount();
                                            Enroll=userProfile.Enroll;
                                            tv_stud_year.setText(Year);
                                            Log.i("Tag","Name..............:"+fullName);
                                            progress.dismiss();
                                        }
                                        else
                                        {
                                            new SweetAlertDialog(student_menu.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Details not found!!! ")
                                                    .setContentText("Logout all accounts .then try again.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    finish();
                                                    Intent intent = new Intent(student_menu.this, MainLogin.class);
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
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error){
                            progress.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.dismiss();
            }
        });

    }


    @Override
    public void onPaymentSuccess(String s) {

        SimpleDateFormat sdf = new SimpleDateFormat("  dd MM yyyy   hh mm aa");
        String currentDateandTime = sdf.format(new Date());
        try {
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Fees").child(Year).child(Enroll+" "+fullName+" Name"+currentDateandTime+" "+s+" "+"Successful "+getFeeAmmount());
            databaseReference1.child("Status").setValue("Payment Successful");
            new SweetAlertDialog(
                    this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("RazorPay")
                    .setContentText("Payment Successful")
                    .show();

        }catch (Exception e){
            Log .v("TAG","EXXXXXX.........:"+e);
            new SweetAlertDialog(
                    this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("RazorPay")
                    .setContentText("Payment Failed")
                    .show();
        }


    }

    @Override
    public void onPaymentError(int i, String s) {
        new SweetAlertDialog(
                this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("RazorPay")
                .setContentText("Payment Failed")
                .show();
    }
    //back press alert
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(student_menu.this);
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
    public static String tutorials[]
            = {};
    public static ArrayList<String> keys2 = new ArrayList<String>();
    public void addValueToList()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("Database").child(Year);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    int n=tutorials.length;
                    String x=key;
                    keys2.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference.addListenerForSingleValueEvent(eventListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        keys2.clear();
    }

}