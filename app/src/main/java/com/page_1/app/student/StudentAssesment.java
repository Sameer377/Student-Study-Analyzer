package com.page_1.app.student;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import static com.page_1.app.student.student_menu.Year;
import java.util.ArrayList;

public class StudentAssesment extends AppCompatActivity {

    CardView LinksStudent,TeacherAssignmentsCD,NotesStudent,VideosStudent;
    TextView subname,studAssesBack_tv;
    private DatabaseReference databaseReference;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assesment);
        getSupportActionBar().hide();
        subname=findViewById(R.id.SubnameStudent);
        subname.setText(SelectedSubject);
        studAssesBack_tv=findViewById(R.id.studAssesBack_tv);
        studAssesBack_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinksStudent=findViewById(R.id.LinksStudent);
        NotesStudent=findViewById(R.id.NotesStudent);
        VideosStudent=findViewById(R.id.VideosStudent);
        TeacherAssignmentsCD=findViewById(R.id.TeacherAssignmentsCDStudent);
        TeacherAssignmentsCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentAssesment.this, DisplayAssignmentStudent.class);
                startActivity(intent);
            }

        });
        LinksStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentAssesment.this, DisplayLinksS.class);
                startActivity(intent);
            }

        });
        NotesStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(StudentAssesment.this, DisplayNotesS.class);
                startActivity(intent2);

            }
        });
        VideosStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StudentAssesment.this, DisplayVideosS.class);
                startActivity(intent2);

            }
        });
        addValueToList();
        addValueToList2();
        addValueToList3();
        Toast.makeText(StudentAssesment.this,"Loading...",Toast.LENGTH_SHORT).show();
    }





    public static String tutorials[]
            = {};
    public static ArrayList<String> keys2 = new ArrayList<String>();
    public void addValueToList()
    {

        databaseReference= FirebaseDatabase.getInstance().getReference("Database").child(Year).child(SelectedSubject).child("Assignments");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();
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
        keys13.clear();
        keys14.clear();
    }

    public static ArrayList<String> keys13 = new ArrayList<String>();
    public void addValueToList2()
    {

        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference("Database").child(Year).child(SelectedSubject).child("Notes");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key3 = ds.getKey();

                    keys13.add(key3);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };

        databaseReference4.addListenerForSingleValueEvent(eventListener);
    }
    //for videos
    public static ArrayList<String> keys14 = new ArrayList<String>();
    public void addValueToList3()
    {

        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Database").child(Year).child(SelectedSubject).child("Videos");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key1 = ds.getKey();

                    keys14.add(key1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference3.addListenerForSingleValueEvent(eventListener);
    }
}