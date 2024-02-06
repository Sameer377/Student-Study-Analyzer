package com.page_1.app.DisplayFiles;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;

import java.util.ArrayList;

import static com.page_1.app.DisplayFiles.TeacherAssesment.keys8;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
public class DisplayNotes extends AppCompatActivity {
    ListView ListViewVideosTeacher;
    TextView Assignment_teacher_assesme_videos;
    public static String SelectedNotes;
    public ArrayList<String> It;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_videos);
        AddActionBar();
        ListViewVideosTeacher = findViewById(R.id.ListViewVideosTeacher);
        Assignment_teacher_assesme_videos=findViewById(R.id.Assignment_teacher_assesme_videos);
        if(keys8.size()==0)
        { finish();
            Toast.makeText(DisplayNotes.this," Notes Not Available Yet!!!\n try some times later",Toast.LENGTH_LONG).show();

        }
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                keys8);
        ListViewVideosTeacher.setAdapter(arr);
        ListViewVideosTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                SelectedNotes=selectedItem;
                addValueToList1();
                Intent intent = new Intent(DisplayNotes.this,DisplayNotesFiles.class);
                startActivity(intent);
            }
        });
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
    public static ArrayList<String> keys15 = new ArrayList<String>();
    public void addValueToList1()
    {

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("DataAnalysis").child(teachingYear).child(SubjectName).child("Notes").child(SelectedNotes);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    keys15.add(key);
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
        SelectedNotes=null;
        keys15.clear();
    }
}
