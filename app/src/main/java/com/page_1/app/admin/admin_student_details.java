package com.page_1.app.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.page_1.app.R;
import com.page_1.app.loginDetailsGenerator.passwordGenerator;
import com.page_1.app.student.User2;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class admin_student_details extends AppCompatActivity implements View.OnClickListener,OnTaskCompleted{
private Spinner spinner;
private Button btn_save_stud_ad;
private Button btn_back_stud_ad;
private TextInputEditText tf_Name_stud_ad;
private TextInputEditText tf_Enroll_stud_ad;
private TextInputEditText tf_Contact_stud_ad;
private TextInputEditText tf_Email_stud_ad;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    private char[] password;
    private String pass="";
    private String TeachingYear="";
    private String userId;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_details);
        getSupportActionBar().hide();

        initUI();
        initListener();
        subjectSpinner();
        initiateFirebase();
    }


    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(admin_student_details.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    private void initUI() {
        spinner = findViewById(R.id.year_spinner);
        tf_Name_stud_ad = findViewById(R.id.tf_Name_stud_ad);
        tf_Enroll_stud_ad = findViewById(R.id.tf_Enroll_stud_ad);
        tf_Contact_stud_ad=findViewById(R.id.tf_Contact_stud_ad);
        tf_Email_stud_ad=findViewById(R.id.tf_Email_stud_ad);
        btn_back_stud_ad=findViewById(R.id.btn_back_stud_ad);
        btn_save_stud_ad=findViewById(R.id.btn_save_stud_ad);
        mAuth = FirebaseAuth.getInstance();
        setInputCon();
    }
    private void setInputCon() {
        tf_Name_stud_ad.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });

    }
    private void initListener() {
        btn_back_stud_ad.setOnClickListener(this);
        btn_save_stud_ad.setOnClickListener(this);
    }
  public  String Year="";
    String StudentClass=null;
    private void subjectSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Year");
        arrayList.add("First year");
        arrayList.add("Second year");
        arrayList.add("Third year");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Year = parent.getItemAtPosition(position).toString();
                switch (Year)
                {
                    case "First year":
                        StudentClass="FYCO";
                        break;
                    case "Second year":
                        StudentClass="SYCO";
                        break;
                    case "Third year":
                        StudentClass="TYCO";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_stud_ad:
                finish();
                break;
            case R.id.btn_save_stud_ad:
                    save();

                break;
        }
    }
    String MobilePattern = "[0-9]{10}";
    String fullname="",Email="",contact="",enrollmentNo="";
    private void save() {
        fullname = tf_Name_stud_ad.getText().toString().trim();
        Email = tf_Email_stud_ad.getText().toString().trim();
        contact = tf_Contact_stud_ad.getText().toString().trim();
        enrollmentNo = tf_Enroll_stud_ad.getText().toString().trim();

        if(fullname.isEmpty()){
            tf_Name_stud_ad.setError("Required");
            tf_Name_stud_ad.requestFocus();
            return;
        }
        else if(enrollmentNo.isEmpty()){
            tf_Enroll_stud_ad.setError("Required");
            tf_Enroll_stud_ad.requestFocus();
            return;
        }
        else if(Year.equals("Select Year"))
        {
            Toast.makeText(this,"Select Year",Toast.LENGTH_SHORT).show();
            return;
        }
       else if(contact.isEmpty()){
            tf_Contact_stud_ad.setError("Required");
            tf_Contact_stud_ad.requestFocus();
            return;
        }
        else if(!contact.matches(MobilePattern)){
            tf_Contact_stud_ad.setError("please enter valid contact No");
            tf_Contact_stud_ad.requestFocus();
            return;
        }
        else if(!enrollmentNo.matches(MobilePattern)){
            tf_Enroll_stud_ad.setError("please enter valid ERP No");
            tf_Enroll_stud_ad.requestFocus();
            return;
        }
       else if(Email.isEmpty()){
            tf_Email_stud_ad.setError("Required");
            tf_Email_stud_ad.requestFocus();
            return;
        }
       else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            tf_Email_stud_ad.setError("Plz Enter valid Email ");
            tf_Email_stud_ad.requestFocus();
            return;
        }
       else {
           openProgressDialog();
            passwordGenerator obj =new passwordGenerator();
            password = obj.password();
            pass = String.valueOf(password);

            mAuth.createUserWithEmailAndPassword(Email, pass)
                    .addOnCompleteListener(admin_student_details.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser userI = FirebaseAuth.getInstance().getCurrentUser();

                            if (userI != null) {
                                userId=userI.getUid();
                            }

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                createUser();
                                Intent intent = getIntent();
                                startActivity(intent);
                                sendDetails();
                                finish();
                                Toast.makeText(admin_student_details.this, "user created.",
                                        Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(admin_student_details.this, "creation failed.",
                                        Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }
                    });

        }

    }
    private void createUser() {
        // TODO

        User2 user = new User2(fullname, contact, Email, pass,Year,enrollmentNo);
        mFirebaseDatabase.child(StudentClass).child(userId).setValue(user);
        String details=Email+pass+"ph"+contact;
        mFirebaseDatabase1.child("ERP"+enrollmentNo).setValue(details);
        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference("Student Name List").child(Year).child(fullname);
        databaseReference4.child("status").setValue("Persuing");
//        mFirebaseDatabase.child(userId).setValue(user);
        Log.v("TAG","USER ID :   "+FirebaseAuth.getInstance().getCurrentUser().getUid());
//        addUserChangeListener();
    }
    public void initiateFirebase()
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Student");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("passwords");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TAG", "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read app title value.", error.toException());
            }
        });
    }
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(StudentClass).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e("TAG", "User data is null!");
                    return;
                }

                Log.e("TAG", "User data is changed!" + user.fullname + ", " + user.Email+ ", "+user.contact);

                // Display newly updated name and email

                Toast.makeText(admin_student_details.this,user.fullname + ", " + user.Email+ ", "+user.contact+","+user.pass,Toast.LENGTH_SHORT).show();
                // txtDetails.setText();

                // clear edit text

                // toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }
        });
    }
    public void sendDetails(){
        try
        {

            String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
            String sendId="FSTSMS";
            //important step...
//                    String number="8999596143";
//                    String number= String.valueOf(tf_teacher_username.getText());
            String number = tf_Contact_stud_ad.getText().toString().trim();
            /* Dear user,
This is your login details for the AGPPI app
username - sameershaikh01072004@gmail.com
password - 212122
thank you.
 */
            String mess="\nThis is your login details for the AGPPI app,\nusername - "+(tf_Email_stud_ad.getText().toString().trim())+"\npassword - "+pass+"\nthank you.";
            String message= URLEncoder.encode(mess, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            OkHttpHandler okHttpHandler=new OkHttpHandler(admin_student_details.this, (OnTaskCompleted) admin_student_details.this,null,"OTPSend");
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
                    Toast.makeText( admin_student_details. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText( admin_student_details. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



}