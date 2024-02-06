package com.page_1.app.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import com.page_1.app.loginDetailsGenerator.*;
import com.page_1.app.teacher.TeacherForgotDetails;
import com.page_1.app.utils.OkHttpHandler;
import com.page_1.app.utils.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

public class admin_faculty_details extends AppCompatActivity implements View.OnClickListener,OnTaskCompleted {
private Spinner spinner1;
private Spinner spinner;
private ArrayList<String> arrayList1;
public TextInputEditText tf_fullname_admin_faculty ;
public TextInputEditText tf_Email_admin_faculty ;
public TextInputEditText tf_contact_admin_faculty ;
public Button save;
private ArrayList<String> arrayList;
private DatabaseReference mFirebaseDatabase;
private DatabaseReference mFirebaseDatabase1;
private FirebaseDatabase mFirebaseInstance;
private Button btn_back;
private char[] password;
private String pass="";
private String TeachingYear="";
private String userId;
    private FirebaseAuth mAuth;
    private String subjectcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_faculty_details);
        initUI();
        initListener();
        _funcSetSpinnerValues();
        initiateFirebase();
    }


    public void initUI()
    {
        getSupportActionBar().hide();
        spinner = findViewById(R.id.year_spinner);
        tf_fullname_admin_faculty = findViewById(R.id.tf_fullname_admin_faculty);
        tf_Email_admin_faculty = findViewById(R.id.tf_Email_admin_faculty);
        tf_contact_admin_faculty = findViewById(R.id.tf_contact_admin_faculty);
        save=findViewById(R.id.btn_save);
        spinner1 = findViewById(R.id.subject_spinner);
        mAuth = FirebaseAuth.getInstance();
        btn_back=findViewById(R.id.btn_back);
        setInputCon();
    }
    private void setInputCon() {
        tf_fullname_admin_faculty.setFilters(new InputFilter[] {
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
    public void initListener()
    {
        btn_back.setOnClickListener(this);
        save.setOnClickListener(this);
    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(admin_faculty_details.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
private String SubjectName="";
    public void _funcSetSpinnerValues()
    {
        arrayList = new ArrayList<>();
        arrayList.add("Select Year");
        arrayList.add("First year");
        arrayList.add("Second year");
        arrayList.add("Third year");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        arrayList1 = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 TeachingYear = parent.getItemAtPosition(position).toString();
                switch(TeachingYear){
                    case "Select Year":
                        spinner1.setVisibility(View.GONE);
                        break;
                    case "First year":
                        spinner1.setVisibility(View.VISIBLE);
                        arrayList1.add("Select Subject");
                        arrayList1.add("22001 - Fundamentals of ICT ");
                        arrayList1.add("22003 - Engineering graphics ");
                        arrayList1.add("22005 - Workshop practice ");
                        arrayList1.add("22101 - English ");
                        arrayList1.add("22102 - Basic science ");
                        arrayList1.add("22103 - Basic mathematics ");
                        arrayList1.add("22215 - Elements of electrical engineering");
                        arrayList1.add("22224 - Applied mathematics");
                        arrayList1.add("22225 - Basic electronics");
                        arrayList1.add("22226 - Programming in C");
                        arrayList1.add("22009 - Business communication using computers");
                        arrayList1.add("22013 - Computer peripherals and hardware maintenance ");
                        arrayList1.add("22014 - Web page designing with HTML ");
                        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(admin_faculty_details.this,android.R.layout.simple_spinner_item,arrayList1);
                        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(arrayAdapter1);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 SubjectName = parent.getItemAtPosition(position).toString();
                                 subjectcode=SubjectName.substring(0,5);
                            }
                            @Override
                            public void onNothingSelected(AdapterView <?> parent) {
                            }
                        });
                        break;
                    case "Second year":
                        spinner1.setVisibility(View.VISIBLE);

                        ArrayList<String> arrayList2 = new ArrayList<>();
                        arrayList2.add("Select Subject");
                        arrayList2.add("22316 - Object oriented programming");
                        arrayList2.add("22317 - Data structure using c");
                        arrayList2.add("22318 - Computer graphics");
                        arrayList2.add("22319 - Database management system");
                        arrayList2.add("22320 - Digital techniques");
                        arrayList2.add("22412 - Java Programming");
                        arrayList2.add("22413 - Software Engineer");
                        arrayList2.add("22414 - Data communication and computer network ");
                        arrayList2.add("22415 - MICROPROCESSOR");
                        arrayList2.add("22034 - GUI Application development using Visual Basics");
                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(admin_faculty_details.this,android.R.layout.simple_spinner_item,arrayList2);
                        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(arrayAdapter2);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                SubjectName = parent.getItemAtPosition(position).toString();
                                subjectcode=SubjectName.substring(0,5);
                            }
                            @Override
                            public void onNothingSelected(AdapterView <?> parent) {
                            }
                        });
                        break;
                    case "Third year":
                        spinner1.setVisibility(View.VISIBLE);

                        ArrayList<String> arrayList3 = new ArrayList<>();
                        arrayList3.add("Select Subject");
                        arrayList3.add("22447 - Environmental Studies");
                        arrayList3.add("22516 - Operating Systems");
                        arrayList3.add("22517 - Advanced Java Programming");
                        arrayList3.add("22518 - Software Testing");
                        arrayList3.add("22620 - Network and Information Security");
                        arrayList3.add("22520 - Advance computer network");
                        arrayList3.add("22057 - Industrial Training");
                        arrayList3.add("22509 - Management");
                        arrayList3.add("22616 - Programming With Python");
                        arrayList3.add("22617 - Mobile Application Development");
                        arrayList3.add("22618 - Emerging Trends in Computer and Information Technology");
                        arrayList3.add("22032 - Entrepreneurship Development");
                        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(admin_faculty_details.this,android.R.layout.simple_spinner_item,arrayList3);
                        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(arrayAdapter3);

                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                SubjectName = parent.getItemAtPosition(position).toString();
                                subjectcode=SubjectName.substring(0,5);
                            }
                            @Override
                            public void onNothingSelected(AdapterView <?> parent) {
                            }
                        });
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                spinner1.setVisibility(View.GONE);
            }
        });
    }

    public void initiateFirebase()
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Faculty");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("FacultyPassword");

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
    String MobilePattern = "[0-9]{10}";
     String fullname="",Email="",contact="";
    private void save() {
        fullname = tf_fullname_admin_faculty.getText().toString().trim();
         Email = tf_Email_admin_faculty.getText().toString().trim();
        contact = tf_contact_admin_faculty.getText().toString().trim();

        if(fullname.isEmpty()){
            tf_fullname_admin_faculty.setError("Full Name Required");
            tf_fullname_admin_faculty.requestFocus();
            return;
        }
        else if(TeachingYear.equals("Select Year"))
        {
            Toast.makeText(this,"Select Year",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(SubjectName.equals("Select Subject"))
        {
            Toast.makeText(this,"Select Subject",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(contact.isEmpty()){
            tf_contact_admin_faculty.setError("Required");
            tf_contact_admin_faculty.requestFocus();
            return;
        }
        else if(!contact.matches(MobilePattern)){
            tf_contact_admin_faculty.setError("please enter valid contact No");
            tf_contact_admin_faculty.requestFocus();
        }
       else if(Email.isEmpty()){
            tf_Email_admin_faculty.setError("Email is required");
            tf_Email_admin_faculty.requestFocus();
            return;
        }
       else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            tf_Email_admin_faculty.setError("Please Enter valid Email ");
            tf_Email_admin_faculty.requestFocus();
            return;
        }
       else {
           openProgressDialog();
            passwordGenerator obj =new passwordGenerator();
            password = obj.password();
            pass = String.valueOf(password);

            mAuth.createUserWithEmailAndPassword(Email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           FirebaseUser userI = FirebaseAuth.getInstance().getCurrentUser();

                            if (userI != null) {
                                userId=userI.getUid();
                            }

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "create User With Email:success");
                                createUser();
                                Intent intent = getIntent();
                                startActivity(intent);
                                finish();
                                Toast.makeText(admin_faculty_details.this, "user created.",
                                        Toast.LENGTH_SHORT).show();
                                sendDetails();
                                progress.dismiss();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(admin_faculty_details.this, "creation failed.",
                                        Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }
                    });
        }

    }






    /**
     * Creating new user node under 'users'
     */
    private void createUser() {
        // TODO

        User user = new User(fullname, contact, Email, pass,TeachingYear,SubjectName);
        mFirebaseDatabase.child(userId).setValue(user);
        String details=Email+pass+"ph"+contact;
        mFirebaseDatabase1.child("ERP"+subjectcode).setValue(details);
//        mFirebaseDatabase.child(userId).setValue(user);
      Log.v("TAG","USER ID :   "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
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

                Toast.makeText(admin_faculty_details.this,user.fullname + ", " + user.Email+ ", "+user.contact+","+user.pass,Toast.LENGTH_SHORT).show();
               // txtDetails.setText();

                // clear edit text
                tf_fullname_admin_faculty.setText("");
                tf_Email_admin_faculty.setText("");
                tf_contact_admin_faculty.setText("");

               // toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
    }
    public void sendDetails(){
        try
        {

            String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
            String sendId="FSTSMS";
            //important step...
//                    String number="8999596143";
//                    String number= String.valueOf(tf_teacher_username.getText());
            String number = tf_contact_admin_faculty.getText().toString().trim();
            /* Dear user,
This is your login details for the AGPPI app
username - sameershaikh01072004@gmail.com
password - 212122
thank you.
 */
            String mess="\nThis is your login details for the AGPPI app,\nusername - "+(tf_Email_admin_faculty.getText().toString().trim())+"\npassword - "+pass+"\nthank you.";
            String message= URLEncoder.encode(mess, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            OkHttpHandler okHttpHandler=new OkHttpHandler(admin_faculty_details.this, (OnTaskCompleted) admin_faculty_details.this,null,"OTPSend");
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
                    Toast.makeText( admin_faculty_details. this,  jsonArray.get(0)+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText( admin_faculty_details. this,  "OTP sending failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
