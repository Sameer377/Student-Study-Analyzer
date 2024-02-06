package com.page_1.app.DisplayFiles;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import static com.page_1.app.DisplayFiles.DisplayAssignmentF.AssignmentNOO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.R;

import static com.page_1.app.teacher.AssignmentStudentResponse.StudentName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;

public class DisplayFiles2 extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
    static ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(DisplayFiles2.this );
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
        setContentView(R.layout.activity_display_files2);
        AddActionBar();
        recview = (RecyclerView) findViewById(R.id.recview7);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Database2").child(teachingYear).child(SubjectName).child("Assignments").child(AssignmentNOO).child(StudentName)
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
        actionBar.setTitle("Response : "+StudentName);
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
