package com.page_1.app.teacher;

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

import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.DisplayFiles.DisplayFiles;
import com.page_1.app.DisplayFiles.DisplayFiles2;
import com.page_1.app.R;

import static com.page_1.app.DisplayFiles.DisplayAssignmentF.AssignmentNOO;
import static com.page_1.app.DisplayFiles.DisplayAssignmentF.keys4;
import static com.page_1.app.DisplayFiles.TeacherAssignment.keys6;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

public class AssignmentStudentResponse extends AppCompatActivity {
ListView l1;
    public static String StudentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_student_response);
        AddActionBar();
        if(keys6.size()==0)
        {
            Toast.makeText(AssignmentStudentResponse.this,"No response recorded",Toast.LENGTH_LONG).show();
            finish();
        }
        l1 = findViewById(R.id.StudentResponceLV);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                keys6);
        l1.setAdapter(arr);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                 StudentName=selectedItem;

                Intent intent=new Intent(AssignmentStudentResponse.this, DisplayFiles2.class);
                startActivity(intent);
            }
        });
    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Student Response");
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
