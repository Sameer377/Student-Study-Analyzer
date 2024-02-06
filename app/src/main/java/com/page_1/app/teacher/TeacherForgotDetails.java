package com.page_1.app.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import com.page_1.app.admin.User;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Random;

public class TeacherForgotDetails extends AppCompatActivity implements View.OnClickListener,OnTaskCompleted {
    private TextView back_getdetails;
    private TextInputEditText enrollno;
    private Button getdetails;
    private String enrollment;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String UserID;
    private String fullName;
    private String contact;
    private String Email;
    private FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_forgot_details);
        getSupportActionBar().hide();
        initUI();
        initListner();
        back_getdetails.setText("<< BACK");
    }



    private void initUI() {
        back_getdetails = findViewById(R.id.back_getdetails);
        getdetails=findViewById(R.id.btn_teacher_getdetails);
        enrollno=findViewById(R.id.enroll_ED);
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    private void initListner() {
        back_getdetails.setOnClickListener(this);
        getdetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.back_getdetails:
                finish();
                break;
            case R.id.btn_teacher_getdetails:
                getUserDetails();
                break;
        }
    }
    String username;
    String password;
    String value;
    DatabaseReference databaseReference;
    public void getUserDetails() {
        enrollment=enrollno.getText().toString().trim();
        openProgressDialog();

        databaseReference = firebaseDatabase.getReference("passwords").child("ERP" + enrollment);



            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    value = snapshot.getValue(String.class);
                    if(value==null)
                    {
                        progress.dismiss();
                        Toast.makeText(TeacherForgotDetails.this, "user not found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TeacherForgotDetails.this, value, Toast.LENGTH_SHORT).show();
                        convertString(value);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // calling on cancelled method when we receive
                    // any error or we are not able to get the data.
                    Toast.makeText(TeacherForgotDetails.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });





    }
    String dig3;
    public void convertString(String value){
        int l,j,h,g;
        //String value contents username,password and users contact
        int valLength=value.length();
        //for getting username
        h=valLength-18;
         username=value.substring(0,h);
        //forgetting password
        l=valLength-18;
       password= value.substring(l,l+6);
       //for getting contact no
       j=valLength-10;
        contact= value.substring(j,j+10);
        //for progressbar
        g=valLength-3;
        dig3=value.substring(g,g+3);

//        Toast.makeText(TeacherForgotDetails.this,username, Toast.LENGTH_SHORT).show();
sendDetails();
    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(TeacherForgotDetails.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    public void sendDetails(){
        progress.setMessage("sms sending to \n XXXXXXX"+dig3);
        try
        {
            int length = 4;
            String numbers = "1234567890";
            Random random = new Random();
            char[] otp = new char[length];
            for (int i = 0; i < length; i++) {
                otp[i] = numbers.charAt(random.nextInt(numbers.length()));
            }
            String finalotp=String.valueOf(otp);
            String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
            String sendId="FSTSMS";
            //important step...
            String number = contact;
            String mess="\nThis is your login details for the AGPPI app,\nusername - "+username+"\npassword - "+password+"\nthank you.";
            String message= URLEncoder.encode(mess, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            OkHttpHandler okHttpHandler=new OkHttpHandler(TeacherForgotDetails.this, (OnTaskCompleted) TeacherForgotDetails.this,null,"OTPSend");
            okHttpHandler.execute(myUrl);

//                        Toast.makeText( LoginTeacherActivity. this,  "OTP ="+getFinalotp(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onTaskCompleted(String result, String TAG) throws Exception {
        JSONObject jsonObject=new JSONObject(result);
        switch (TAG)
        {
            case "OTPSend":
                if(jsonObject.optBoolean("return"))
                {
                    progress.dismiss();
                    JSONArray jsonArray=jsonObject.optJSONArray("message");
                    Toast.makeText( TeacherForgotDetails. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText( TeacherForgotDetails. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
                break;
        }
    }
}
