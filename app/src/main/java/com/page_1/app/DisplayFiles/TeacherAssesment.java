package com.page_1.app.DisplayFiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import com.page_1.app.student.student_menu;

import java.util.ArrayList;

import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;
public class TeacherAssesment extends AppCompatActivity {
CardView TeacherAssignmentsCD,VideosTeachercd,notesTeacherasses,LinksF;
TextView subname;
    private DatabaseReference databaseReference;
    private String key;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private  TextView teacherassesBack_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assesment);
        getSupportActionBar().hide();
        subname=findViewById(R.id.Subname);
        subname.setText(SubjectName);
        notesTeacherasses=findViewById(R.id.notesTeacherasses);
        TeacherAssignmentsCD=findViewById(R.id.TeacherAssignmentsCD);
        LinksF=findViewById(R.id.LinksF);
        teacherassesBack_tv=findViewById(R.id.teacherassesBack_tv);
        teacherassesBack_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        VideosTeachercd=findViewById(R.id.VideosTeachercd);
        VideosTeachercd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TeacherAssesment.this, DisplayVideos.class);
                startActivity(intent2);

            }
        });
        LinksF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TeacherAssesment.this, DisplayLinks.class);
                startActivity(intent2);

            }
        });
        notesTeacherasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(TeacherAssesment.this, DisplayNotes.class);
                startActivity(intent2);

            }
        });
        TeacherAssignmentsCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherAssesment.this, DisplayAssignmentF.class);
                startActivity(intent);

            }

        });
        addValueToList();
        addValueToList1();
        addValueToList2();
    }





    public static String tutorials[]
            = {};
    public static String [] keys={"Assignment no 1"};
   public static ArrayList<String> keys1 = new ArrayList<String>();
    public void addValueToList()
    {

            databaseReference= FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child("Assignments");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();

                    keys1.add(key);
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
        keys1.clear();
        keys7.clear();
        keys8.clear();

    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(TeacherAssesment.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    public static String tutorials6[]
            = {};
    public static ArrayList<String> keys7 = new ArrayList<String>();
    public void addValueToList1()
    {

        databaseReference3= FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child("Videos");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key1 = ds.getKey();

                    keys7.add(key1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference3.addListenerForSingleValueEvent(eventListener);
    }
    //for Notes purpose
    public static String tutorials8[]
            = {};
    public static ArrayList<String> keys8 = new ArrayList<String>();
    public void addValueToList2()
    {

        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child("Notes");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key3 = ds.getKey();

                    keys8.add(key3);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference4.addListenerForSingleValueEvent(eventListener);
    }
}
