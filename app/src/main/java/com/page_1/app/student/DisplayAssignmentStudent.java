package com.page_1.app.student;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.StudentAssesment.keys2;
import static com.page_1.app.student.student_menu.Year;
import static com.page_1.app.student.student_menu.fullName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.DisplayFiles.DisplayAssignmentF;
import com.page_1.app.DisplayFiles.DisplayFiles;
import com.page_1.app.DisplayFiles.TeacherAssignment;
import com.page_1.app.R;

public class DisplayAssignmentStudent extends AppCompatActivity {

    ListView l;
    public  static  String AssignmentNOO;
    public static String SelectedAssignment;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment_student);
        AddActionBar();
        if(keys2.size()==0)
        {
            finish();
            Toast.makeText(DisplayAssignmentStudent.this,"Assignment Not Available Yet!!!\ntry some times later",Toast.LENGTH_SHORT).show();
        }
        else{
            l = findViewById(R.id.listAssignment);
            ArrayAdapter<String> arr;
            arr
                    = new ArrayAdapter<String>(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    keys2);
            l.setAdapter(arr);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    AssignmentNOO = selectedItem;
                    SelectedAssignment=selectedItem;
                    Intent intent=new Intent(DisplayAssignmentStudent.this,DisplayFiles.class);
                    startActivity(intent);

                    databaseReference= FirebaseDatabase.getInstance().getReference("DataAnalysis").child(Year).child(SelectedSubject).child("Assignments").child(SelectedAssignment).child(fullName);
                    databaseReference.child("Response").setValue("Viewed");

                }
            });
        }

    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Assignments");
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
}