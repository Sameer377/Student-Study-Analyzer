package com.page_1.app.DisplayFiles;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.student_menu.Year;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.R;

import static com.page_1.app.student.DisplayAssignmentStudent.SelectedAssignment;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;

public class DisplayFiles extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
   static ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(DisplayFiles.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        openProgressDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_files);
        AddActionBar();
        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Database").child(Year).child(SelectedSubject).child("Assignments").child(SelectedAssignment)
                                , model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(""+SelectedAssignment);
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
