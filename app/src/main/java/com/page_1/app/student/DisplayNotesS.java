package com.page_1.app.student;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.DisplayFiles.DisplayNotesFiles;
import com.page_1.app.DisplayFiles.DisplayVideosFiles;
import com.page_1.app.R;

import java.util.ArrayList;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.StudentAssesment.keys13;
import static com.page_1.app.student.student_menu.Year;
import static com.page_1.app.student.student_menu.fullName;

public class DisplayNotesS extends AppCompatActivity {
    ListView ListViewVideosTeacher;
    TextView Assignment_teacher_assesme_videos;
    public static String SelectedNotesS;
    public ArrayList<String> It;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_videos);
       AddActionBar();
        ListViewVideosTeacher = findViewById(R.id.ListViewVideosTeacher);
        Assignment_teacher_assesme_videos=findViewById(R.id.Assignment_teacher_assesme_videos);
        if(keys13.size()==0)
        {
            finish();
            Toast.makeText(DisplayNotesS.this,"Notes Not Available yet !!!\ntry some times later",Toast.LENGTH_SHORT).show();
        }
        else{
            ArrayAdapter<String> arr;
            arr
                    = new ArrayAdapter<String>(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    keys13);
            ListViewVideosTeacher.setAdapter(arr);
            ListViewVideosTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    SelectedNotesS=selectedItem;
                    Intent intent = new Intent(DisplayNotesS.this, DisplayNotesFilesS.class);
                    startActivity(intent);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DataAnalysis").child(Year).child(SelectedSubject).child("Notes").child(SelectedNotesS).child(fullName);
                    databaseReference.child("Response").setValue("Viewed");
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SelectedNotesS=null;

    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notes");
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
