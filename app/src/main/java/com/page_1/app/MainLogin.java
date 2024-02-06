package com.page_1.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.admin.User;
import com.page_1.app.admin.admin_menu;
import com.page_1.app.student.User2;
import com.page_1.app.student.student_menu;
import com.page_1.app.teacher.MainTeacherActivity;
import com.page_1.app.teacher.SubjectCodeForgetDetails;
import com.page_1.app.teacher.TeacherForgotDetails;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class MainLogin extends AppCompatActivity implements OnTaskCompleted, View.OnTouchListener {
private Spinner spinner1;
    private Button btn_teacher_otp;
Button Login;
TextView selectLogintv;
TextView Logintv,Help;
TextView back,help;
TextView tv_details_teacher2;
ImageView profileimg;
LinearLayout Usernamelin;
LinearLayout background;
LinearLayout LoginOTP;
LinearLayout passwordlin;
LinearLayout linmain;
TextView AdminForgot;
public String detailspath=null;
    private String Passwordstr,Username_str;
    private String contt_str;

    public String getShar1() {
        return shar1;
    }

    public void setShar1(String shar1) {
        this.shar1 = shar1;
    }

    //saving details into system
String shar1;
//new ver
    private String finalotp;
    private FirebaseAuth mAuth;
    public TextInputEditText tf_teacher_username;
    public TextInputEditText tf_teacher_password;
    public TextInputEditText tf_teacher_otp;
    //firebase initialization
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
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

    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        getSupportActionBar().hide();
        initUI();
        SpinnerFuction();
fetchAdminLoginDetails();

    }



    private void initUI() {
        AdminForgot=findViewById(R.id.AdminForgot);
        Help=(TextView) findViewById(R.id.Help);
       Help.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intt=new Intent(MainLogin.this,HELP.class);
               startActivity(intt);
           }
       });
        spinner1 =(Spinner) findViewById(R.id.mainLoginSpinner);
        profileimg = findViewById(R.id.profileimg);
        selectLogintv=findViewById(R.id.selectLogintv);
        Usernamelin=findViewById(R.id.UsernameLinear);
        passwordlin=findViewById(R.id.PasswordLinear);
        Login=findViewById(R.id.btn_Main_login1);
        btn_teacher_otp=findViewById(R.id.btn_teacher_otp1);
        LoginOTP=findViewById(R.id.LoginOTP);
        Logintv=findViewById(R.id.textView);
        background=findViewById(R.id.linearLayout);
        tv_details_teacher2=findViewById(R.id.tv_details_teacher2);
        linmain=findViewById(R.id.linearLayout);
        back=findViewById(R.id.tv_back_teacher);
        help=findViewById(R.id.Help);
        //user input
        tf_teacher_username=findViewById(R.id.tf_teacher_username1);
        tf_teacher_password=findViewById(R.id.tf_teacher_password1);
        tf_teacher_otp=findViewById(R.id.tf_teacher_otp1);
        //firebase
        mAuth = FirebaseAuth.getInstance();
    }

static SharedPreferences Globlevar;

    public static SharedPreferences getGloblevar() {
        return Globlevar;
    }

    public void setGloblevar(SharedPreferences globlevar) {
        Globlevar = globlevar;
    }
public void fetchAdminLoginDetails(){
        openProgressDialog();
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
            Username_str=(String) snapshot.getValue();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
    DatabaseReference Connt= FirebaseDatabase.getInstance().getReference("Admin").child("Contact");
    Connt.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            contt_str=(String) snapshot.getValue();
            progress.dismiss();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
}
    String LoginType=null;
    private void SpinnerFuction() {
        final SharedPreferences sharedPreferences=getSharedPreferences("Data", Context.MODE_PRIVATE);
        setGloblevar(sharedPreferences);
        final  String type=sharedPreferences.getString("Email","");
        final String type1=sharedPreferences.getString("Email1","");
        final String type2=sharedPreferences.getString("Email2","");
        arrayList = new ArrayList<>();
        arrayList.add("Select");
        arrayList.add("Admin Login");
        arrayList.add("Teacher Login");
        arrayList.add("Student Login");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoginType = parent.getItemAtPosition(position).toString();
                switch(LoginType){
                    case "Admin Login":
                        AdminForgot.setVisibility(View.VISIBLE);
                        AdminForgot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendOTP1(contt_str);
                            }
                        });
                        help.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        linmain.setVisibility(View.VISIBLE);
                        selectLogintv.setVisibility(View.GONE);
                        spinner1.setVisibility(View.GONE);
                        Logintv.setText("Admin Login");
                        if(type.isEmpty())
                        {

                            Toast.makeText(MainLogin.this,"Please Login",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent intent = new Intent(MainLogin.this, admin_menu.class);
                            startActivity(intent);
                            finish();
                        }
                        Login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String teacher_password = tf_teacher_password.getText().toString().trim();
                                String teacher_username = tf_teacher_username.getText().toString().trim();
                                if (teacher_username.isEmpty()) {
                                    tf_teacher_username.setError("username Required");
                                    tf_teacher_username.requestFocus();

                                    return;
                                }
                                else if(!Patterns.EMAIL_ADDRESS.matcher(teacher_username).matches()) {
                                    tf_teacher_username.setError("Enter valid Email Address");
                                    tf_teacher_username.requestFocus();

                                    return;
                                }
                                else if (teacher_password.isEmpty()) {
                                    tf_teacher_password.setError("Password Required");
                                    tf_teacher_password.requestFocus();
                                    return;
                                }
                                else if ((tf_teacher_username.getText().toString().trim() .equals(Username_str) ) && (tf_teacher_password.getText().toString().trim().equals(Passwordstr))) {
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("Email",teacher_username);
                                    editor.putString("Password",teacher_password);
                                    editor.commit();
                                    finish();
                                    Intent intent = new Intent(MainLogin.this, admin_menu.class);
                                    startActivity(intent);
                                    Toast.makeText( MainLogin. this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText( MainLogin. this, "Login Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                selectLogintv.setVisibility(View.VISIBLE);
                                spinner1.setVisibility(View.VISIBLE);
                                Logintv.setText("Login");
                                linmain.setVisibility(View.GONE);
                                back.setVisibility(View.GONE);
                                help.setVisibility(View.VISIBLE);
                                spinner1.setSelection(0);
                                tf_teacher_username.setText(null);
                                tf_teacher_password.setText(null);

                            }
                        });
                        break;
                    case "Teacher Login":
                        AdminForgot.setVisibility(View.GONE);
                        help.setVisibility(View.GONE);
                        linmain.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        selectLogintv.setVisibility(View.GONE);
                        spinner1.setVisibility(View.GONE);
                        LoginOTP.setVisibility(View.VISIBLE);
                        detailspath="Faculty";
                        Logintv.setText("Teacher Login");
                        tv_details_teacher2.setVisibility(View.VISIBLE);
                        if(type1.isEmpty())
                        {
                            Toast.makeText(MainLogin.this,"Please Login",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent intent = new Intent(MainLogin.this, MainTeacherActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        tv_details_teacher2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent3=new Intent(MainLogin.this, SubjectCodeForgetDetails.class);
                                startActivity(intent3);
                            }
                        });
                        btn_teacher_otp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                authentication();
                            }
                        });
                        Login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                _userLogin();
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                selectLogintv.setVisibility(View.VISIBLE);
                                spinner1.setVisibility(View.VISIBLE);
                                LoginOTP.setVisibility(View.GONE);
                                Logintv.setText("Login");
                                linmain.setVisibility(View.GONE);
                                tv_details_teacher2.setVisibility(View.GONE);
                                back.setVisibility(View.GONE);
                                help.setVisibility(View.VISIBLE);
                                spinner1.setSelection(0);
                                tf_teacher_username.setText(null);
                                tf_teacher_password.setText(null);
                                tf_teacher_otp.setText(null);
                            }
                        });
                        break;
                    case "Student Login":
                        AdminForgot.setVisibility(View.GONE);
                        help.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        linmain.setVisibility(View.VISIBLE);
                        selectLogintv.setVisibility(View.GONE);
                        LoginOTP.setVisibility(View.VISIBLE);
                        spinner1.setVisibility(View.GONE);
                        Logintv.setText("Student Login");
                        detailspath="Student";
                        tv_details_teacher2.setVisibility(View.VISIBLE);
                        if(type2.isEmpty())
                        {
                            Toast.makeText(MainLogin.this,"Please Login",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent intent = new Intent(MainLogin.this, student_menu.class);
                            startActivity(intent);
                            finish();
                        }
                        tv_details_teacher2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainLogin.this, TeacherForgotDetails.class);
                                startActivity(intent);
                            }
                        });
                        btn_teacher_otp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                authentication();
                            }
                        });
                        Login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                _userLogin1();
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                selectLogintv.setVisibility(View.VISIBLE);
                                spinner1.setVisibility(View.VISIBLE);
                                Logintv.setText("Login");
                                LoginOTP.setVisibility(View.GONE);
                                linmain.setVisibility(View.GONE);
                                tv_details_teacher2.setVisibility(View.GONE);
                                back.setVisibility(View.GONE);
                                help.setVisibility(View.VISIBLE);
                                spinner1.setSelection(0);
                                tf_teacher_username.setText(null);
                                tf_teacher_password.setText(null);
                                tf_teacher_otp.setText(null);

                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(MainLogin.this );
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
            tf_teacher_password.setError("Password Required");
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
                            Toast.makeText(MainLogin.this, "User Found...", Toast.LENGTH_LONG).show();
                            setDetails(detailspath);
                        }
                        else {
                            Toast.makeText(MainLogin.this, "Invalid User\n please try again ", Toast.LENGTH_LONG).show();
                            i=0;
                            progress.dismiss();
                        }
                    }
                });

    }
    //delay for button
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
                    Toast.makeText( MainLogin. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    progress.dismiss();
                    Toast.makeText( MainLogin. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public static char getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }
    //Details setup
    String Studentclass="FYCO";
    private void setDetails(String childpath) {
        progress.setMessage("Preparing OTP");
        user = FirebaseAuth.getInstance().getCurrentUser();

        UserID = user.getUid();
        if(childpath == "Faculty"){
            reference = FirebaseDatabase.getInstance().getReference(childpath);
            reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User userProfile=snapshot.getValue(User.class);
                    if(userProfile != null){

                        contact = userProfile.contact;
                        sendOTP(contact);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progress.dismiss();
                }
            });

        }
        else if(childpath == "Student"){
            reference = FirebaseDatabase.getInstance().getReference("Student");
            reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User2 userProfile=snapshot.getValue(User2.class);
                    if(userProfile != null){
                        contact = userProfile.Contact;
                        sendOTP(contact);

                    }
                    else if(userProfile==null){
                        Studentclass="SYCO";
                        reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User2 userProfile=snapshot.getValue(User2.class);
                                if(userProfile != null){
                                    contact = userProfile.Contact;
                                    sendOTP(contact);

                                }
                                else if(userProfile==null){
                                    Studentclass="TYCO";
                                    reference.child(Studentclass).child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User2 userProfile=snapshot.getValue(User2.class);
                                            if(userProfile != null){
                                                contact = userProfile.Contact;
                                                sendOTP(contact);

                                            }
                                            else
                                            {
                                                Toast.makeText(MainLogin.this,"User Details Not found",Toast.LENGTH_LONG).show();
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
                            public void onCancelled(@NonNull DatabaseError error) {
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


    }
    public void sendOTP(String Contact){
        int index = 7;
        int index1 = 8;
        int index2 = 9;

        // Get the specific character
        char ch = getCharFromString(Contact, index);
        char ch1=getCharFromString(Contact, index1);
        char ch2=getCharFromString(Contact, index2);
        progress.setMessage("sms sending to \n XXXXXXX"+ch+ch1+ch2);
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
            String number = Contact;
            String message= URLEncoder.encode(" This is Your OTP - " + finalotp, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            OkHttpHandler okHttpHandler=new OkHttpHandler(MainLogin.this, (OnTaskCompleted) MainLogin.this,null,"OTPSend");
            okHttpHandler.execute(myUrl);

//                        Toast.makeText( LoginTeacherActivity. this,  "OTP ="+getFinalotp(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void sendOTP1(String Contact){
        int index = 7;
        int index1 = 8;
        int index2 = 9;

        // Get the specific character
        char ch = getCharFromString(Contact, index);
        char ch1=getCharFromString(Contact, index1);
        char ch2=getCharFromString(Contact, index2);
        progress.setMessage("sms sending to \n XXXXXXX"+ch+ch1+ch2);
        try
        {
            openProgressDialog();
            progress.setMessage("Sms Sending..");
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
            String number = Contact;
            String message="\nThis is your Admin Login Details ,\nusername : "+Username_str+"\n\n password : "+Passwordstr+"\n"+" thank you.";
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            OkHttpHandler okHttpHandler=new OkHttpHandler(MainLogin.this, (OnTaskCompleted) MainLogin.this,null,"OTPSend");
            okHttpHandler.execute(myUrl);

//                        Toast.makeText( LoginTeacherActivity. this,  "OTP ="+getFinalotp(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
            tf_teacher_password.setError("Password Required");
            tf_teacher_password.requestFocus();
            return;
        }
        if (teacher_otp.isEmpty()) {
            tf_teacher_otp.setError("OTP Required");
            tf_teacher_otp.requestFocus();
            return;
        }

        if (tf_teacher_otp.getText().toString().equals(getFinalotp()))
        {
            SharedPreferences.Editor editor1=getGloblevar().edit();
            editor1.putString("Email1",teacher_username);
            editor1.putString("Password1",teacher_password);
            editor1.putString("USERID1",UserID);
            editor1.commit();
            Toast.makeText(MainLogin.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainLogin.this, MainTeacherActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(MainLogin.this, "Login Failed ! Invalid Otp", Toast.LENGTH_LONG).show();
        }
    }
    public void  _userLogin1(){
        String teacher_username = tf_teacher_username.getText().toString().trim();
        String teacher_otp      = tf_teacher_otp.     getText().toString().trim();
        String teacher_password = tf_teacher_password.getText().toString().trim();
        if (teacher_username.isEmpty()) {
            tf_teacher_username.setError("Full Name Required");
            tf_teacher_username.requestFocus();
            return;
        }
        if (teacher_password.isEmpty()) {
            tf_teacher_password.setError("Password Required");
            tf_teacher_password.requestFocus();
            return;
        }
        if (teacher_otp.isEmpty()) {
            tf_teacher_otp.setError("OTP Required");
            tf_teacher_otp.requestFocus();
            return;
        }

        if (tf_teacher_otp.getText().toString().equals(getFinalotp()))
        {
            SharedPreferences.Editor editor2=getGloblevar().edit();
            editor2.putString("Email2",teacher_username);
            editor2.putString("Password2",teacher_password);
            editor2.putString("USERID2",UserID);
            editor2.commit();
            Toast.makeText(MainLogin.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainLogin.this, student_menu.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(MainLogin.this, "Login Failed ! Invalid Otp", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainLogin.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.agppilogo6);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;

    }
}