package com.page_1.app.DisplayFiles;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;

import static com.page_1.app.DisplayFiles.TeacherAssesment.keys1;
import static com.page_1.app.student.StudentAssesment.keys2;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

import java.util.ArrayList;

public class DisplayAssignmentF extends AppCompatActivity {
    ListView l;
    public  static  String AssignmentNOO;
    private DatabaseReference databaseReference2;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment_f);
      AddActionBar();
        if(keys1.size()==0)
        {
            finish();
            Toast.makeText(DisplayAssignmentF.this,"   Assignments Not Available Yet!!!\n try some times later",Toast.LENGTH_LONG).show();
        }
        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                keys1);
        l.setAdapter(arr);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                AssignmentNOO=selectedItem;
                addValueToList1();
                Intent intent = new Intent(DisplayAssignmentF.this,TeacherAssignment.class);
                startActivity(intent);
            }
        });
addValueToList4();
    }
public void AddActionBar(){
    ActionBar actionBar = getSupportActionBar();
    actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    actionBar.setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C3D4E")));
}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static ArrayList<String> keys5 = new ArrayList<String>();
    public void addValueToList4()
    {

        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Student Name List").child(teachingYear);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key1 = ds.getKey();

                    keys5.add(key1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference3.addListenerForSingleValueEvent(eventListener);
    }
    public static ArrayList<String> keys4 = new ArrayList<String>();
    public void addValueToList1()
    {

        databaseReference2= FirebaseDatabase.getInstance().getReference("DataAnalysis").child(teachingYear).child(SubjectName).child("Assignments").child(AssignmentNOO);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();

                    keys4.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference2.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keys4.clear();
        keys5.clear();
    }
}
