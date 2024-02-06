package com.page_1.app.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.BreakIterator;
import java.util.Random;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.page_1.app.R;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

public class student_login extends AppCompatActivity implements OnTaskCompleted, View.OnClickListener {
    private Button btn_student_login;
    private Button btn_stud_otp;
    String finalotp;
    private TextInputEditText tf_stud_username;
    private TextInputEditText tf_stud_otp;
    private TextInputEditText tf_stud_password;
    private FirebaseAuth mAuth;


    public String getFinalotp() {
        return finalotp;
    }

    public void setFinalotp(String finalotp) {
        this.finalotp = finalotp;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        getSupportActionBar().hide();
        initUI();
        initListener();
//        setInputCon();
    }

    private void setInputCon() {
        tf_stud_username.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z0-9]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }


    private void initUI() {
        btn_student_login=findViewById(R.id.btn_student_login);
        btn_stud_otp=findViewById(R.id.btn_stud_otp);
        tf_stud_username=(TextInputEditText) findViewById(R.id.tf_stud_username);
        tf_stud_password=(TextInputEditText) findViewById(R.id.tf_stud_password);
        tf_stud_otp=(TextInputEditText) findViewById(R.id.tf_stud_otp);
        mAuth = FirebaseAuth.getInstance();
    }

    private void initListener() {
        btn_stud_otp.setOnClickListener(this);
        btn_student_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stud_otp:

                try
                {
                    int length = 4;
                    String numbers = "123456789";
                    Random random = new Random();
                    char[] otp = new char[length];
                    for (int i = 0; i < length; i++) {
                        otp[i] = numbers.charAt(random.nextInt(numbers.length()));
                    }
                    String finalotp=String.valueOf(otp);
                    setFinalotp(finalotp);
                    // otpSender.otpSender(" This is Your OTP " + finalotp , "8999596143");
                    String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
                    String sendId="FSTSMS";
                    //important step...
                    String number="8999596143";
                    String message= URLEncoder.encode(" This is Your OTP " + finalotp, "UTF-8");
                    String language="english";
                    String route="p";
                    String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

//                    OkHttpHandler okHttpHandler=new OkHttpHandler(student_login.this,student_login.this,null,"OTPSend");
//                    okHttpHandler.execute(myUrl);
                    System.out.println("OTP ="+finalotp);

                    Toast.makeText( student_login. this,  "OTP = "+getFinalotp(), Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText( student_login. this,  "error", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_student_login:
                userLogin();
                Toast.makeText( student_login. this,  "Successfully Logged in", Toast.LENGTH_LONG).show();
                break;
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
                    JSONArray jsonArray=jsonObject.optJSONArray("message");
                    Toast.makeText( student_login. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText( student_login. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(student_login.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    private void userLogin() {
         String stud_username =tf_stud_username.getText().toString().trim();
         String stud_otp = tf_stud_otp.getText().toString().trim();
         String stud_password = tf_stud_password.getText().toString().trim();

        if (stud_username.isEmpty()) {
            tf_stud_username.setError("Full Name Required");
            tf_stud_username.requestFocus();
            return;
        }

        if (stud_otp.isEmpty()) {
            tf_stud_otp.setError("plz enter valid OTP No");
            tf_stud_otp.requestFocus();
            return;
        }
        if (stud_password.isEmpty()) {
            tf_stud_password.setError("plz enter valid password No");
            tf_stud_password.requestFocus();
            return;
        }
        openProgressDialog();
        mAuth.signInWithEmailAndPassword(stud_username,stud_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (tf_stud_otp.getText().toString().equals(getFinalotp()))
                            {
                                Toast.makeText(student_login.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(student_login.this, student_menu.class);
                                startActivity(intent);
                                progress.dismiss();
                                tf_stud_otp.setText("");
                                tf_stud_password.setText("");
                                tf_stud_username.setText("");
//                                finish();
                            }
                            else{
                                Toast.makeText(student_login.this, "Login Failed ! Invalid Otp", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(student_login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }
                });

    }


}