package com.page_1.app.teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import com.page_1.app.admin.User;
import com.page_1.app.models.FireBaseAuth;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Random;

public class
LoginTeacherActivity extends AppCompatActivity implements View.OnClickListener,OnTaskCompleted {
    private Button btn_teacher_login;
    private Button btn_teacher_otp;
    String finalotp;
    private FirebaseAuth mAuth;
    public TextInputEditText tf_teacher_username;
    public TextInputEditText tf_teacher_password;
    public TextInputEditText tf_teacher_otp;
    public TextView tv_back;
    private ProgressBar progressBar2;
    //firebase initialization
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    private TextView tv_details;
    private String delay;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private String contact;

    public String getFinalotp() {
        return finalotp;
    }

    public void setFinalotp(String finalotp) {
        this.finalotp = finalotp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        getSupportActionBar().hide();

        initUI();
        initListener();
        tv_back.setText("<< Back");
    }

    public void initUI() {
        btn_teacher_login = findViewById(R.id.btn_teacher_login);
        btn_teacher_otp = findViewById(R.id.btn_teacher_otp);
        tf_teacher_username=findViewById(R.id.tf_teacher_username);
        tf_teacher_password=findViewById(R.id.tf_teacher_password);
        tf_teacher_otp=findViewById(R.id.tf_teacher_otp);
        tv_back=findViewById(R.id.tv_back_teacher);
        tv_details=findViewById(R.id.tv_details_teacher2);
        mAuth = FirebaseAuth.getInstance();
    }

    public void initListener() {
        btn_teacher_login.setOnClickListener(this);
        btn_teacher_otp.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_details.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_teacher_login:
                _userLogin();
                break;

            case R.id.btn_teacher_otp:
                authentication();
//                btn_teacher_otp.setEnabled(false);


                break;
            case R.id.tv_back_teacher :
                finish();
                break;
            case R.id.tv_details_teacher2 :
                Intent intent6 = new Intent(LoginTeacherActivity.this,TeacherForgotDetails.class);
                startActivity(intent6);
        }
    }

    @Override

    protected void onResume() {
        super.onResume();


    }
    int i=30;
    private void setDelay() {
        final Runnable enableButton = new Runnable() {
            @Override
            public void run() {

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                            if(i==0)
                            {
                                btn_teacher_otp.setText("resend otp");
                                btn_teacher_otp.setEnabled(true);
                                i=30;

                            }else {
                                btn_teacher_otp.setText("("+i+")");
                                i--;
                                handler.postDelayed(this, 1000);
                            }
                    }
                });

            }

        };

        new Handler().postDelayed(enableButton, 000);

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
                    Toast.makeText( LoginTeacherActivity. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    progress.dismiss();
                    Toast.makeText( LoginTeacherActivity. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(LoginTeacherActivity.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    public void authentication()
    {
        String teacher_password = tf_teacher_password.getText().toString().trim();
        String teacher_username = tf_teacher_username.getText().toString().trim();
        if (teacher_username.isEmpty()) {
            tf_teacher_username.setError("Full Name Required");
            tf_teacher_username.requestFocus();
            return;
        }
        else if (teacher_password.isEmpty()) {
            tf_teacher_password.setError("plz enter valid password No");
            tf_teacher_password.requestFocus();
            return;
        }else {
            btn_teacher_otp.setEnabled(false);
            setDelay();
        }
        //username password confirmation
        openProgressDialog();
        mAuth.signInWithEmailAndPassword(teacher_username,teacher_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progress.setMessage("User Found...");
                            Toast.makeText(LoginTeacherActivity.this, "User Found...", Toast.LENGTH_LONG).show();
                            setDetails();
                        }
                        else {
                            Toast.makeText(LoginTeacherActivity.this, "Invalid User\n please try again ", Toast.LENGTH_LONG).show();
                            i=0;
                            progress.dismiss();
                        }
                    }
                });

    }
  /*  public void userLogin() {
        String teacher_username = tf_teacher_username.getText().toString().trim();
        String teacher_otp      = tf_teacher_otp.     getText().toString().trim();
        String teacher_password = tf_teacher_password.getText().toString().trim();

        if (teacher_username.isEmpty()) {
            tf_teacher_username.setError("Full Name Required");
            tf_teacher_username.requestFocus();
            return;
        }
        if (teacher_password.isEmpty()) {
            tf_teacher_password.setError("plz enter valid password No");
            tf_teacher_password.requestFocus();
            return;
        }
        if (teacher_otp.isEmpty()) {
            tf_teacher_otp.setError("plz enter valid OTP No");
            tf_teacher_otp.requestFocus();
            return;
        }
              openProgressDialog();
        mAuth.signInWithEmailAndPassword(teacher_username,teacher_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (tf_teacher_otp.getText().toString().equals(getFinalotp()))
                            {
                                Toast.makeText(LoginTeacherActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginTeacherActivity.this, MainTeacherActivity.class);
                                startActivity(intent);
                                progress.dismiss();
                                tf_teacher_otp.setText("");
                                tf_teacher_username.setText("");
                                tf_teacher_password.setText("");
                                finish();
                            }
                            else{
                                Toast.makeText(LoginTeacherActivity.this, "Login Failed ! Invalid Otp", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginTeacherActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }
                });

    }

   */
    public static char
    getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }
    //Details setup
    private void setDetails() {
        progress.setMessage("Preparing OTP");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Faculty");
        UserID = user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){

                    contact = userProfile.contact;

                    int index = 7;
                    int index1 = 8;
                    int index2 = 9;

                    // Get the specific character
                    char ch = getCharFromString(contact, index);
                    char ch1=getCharFromString(contact, index1);
                    char ch2=getCharFromString(contact, index2);
                    progress.setMessage("sms sending to \n XXXXXXX"+ch+ch1+ch2);
//                    Toast.makeText( LoginTeacherActivity. this,  "sms sending to 89xxxxxx43", Toast.LENGTH_LONG).show();
//                    progress.dismiss();
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
                        setFinalotp(finalotp);
                        String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
                        String sendId="FSTSMS";
                        //important step...
                        String number = contact;
                        String message= URLEncoder.encode(" This is Your OTP - " + finalotp, "UTF-8");
                        String language="english";
                        String route="p";
                        String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

                        OkHttpHandler okHttpHandler=new OkHttpHandler(LoginTeacherActivity.this, (OnTaskCompleted) LoginTeacherActivity.this,null,"OTPSend");
                        okHttpHandler.execute(myUrl);

//                        Toast.makeText( LoginTeacherActivity. this,  "OTP ="+getFinalotp(), Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.dismiss();
            }
        });


    }
    public void  _userLogin(){
        String teacher_username = tf_teacher_username.getText().toString().trim();
        String teacher_otp      = tf_teacher_otp.     getText().toString().trim();
        String teacher_password = tf_teacher_password.getText().toString().trim();
        if (teacher_username.isEmpty()) {
            tf_teacher_username.setError("Full Name Required");
            tf_teacher_username.requestFocus();
            return;
        }
        if (teacher_password.isEmpty()) {
            tf_teacher_password.setError("plz enter valid password No");
            tf_teacher_password.requestFocus();
            return;
        }
        if (teacher_otp.isEmpty()) {
            tf_teacher_otp.setError("plz enter valid OTP No");
            tf_teacher_otp.requestFocus();
            return;
        }

        if (tf_teacher_otp.getText().toString().equals(getFinalotp()))
        {
            Toast.makeText(LoginTeacherActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginTeacherActivity.this, MainTeacherActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(LoginTeacherActivity.this, "Login Failed ! Invalid Otp", Toast.LENGTH_LONG).show();
        }
    }
}
