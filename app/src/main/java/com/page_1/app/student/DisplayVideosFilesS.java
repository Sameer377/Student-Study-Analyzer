package com.page_1.app.student;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.DisplayFiles.model;
import com.page_1.app.DisplayFiles.myadapter;
import com.page_1.app.R;

import static com.page_1.app.DisplayFiles.DisplayVideos.SelectedVideo;
import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.DisplayVideosS.SelectedVideoS;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.student.student_menu.Year;

public class DisplayVideosFilesS extends AppCompatActivity {

    private RecyclerView recview;
    myadapter adapter;
    RelativeLayout view_eye_rel;
    String selectedNode,ParentNode;
    TextView Assignment_teacher_Video_tv,Subject_name_DV;
    FirebaseRecyclerOptions<model> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_videos_files);

        view_eye_rel=findViewById(R.id.view_eye_rel);
        view_eye_rel.setVisibility(View.GONE);
        recview = (RecyclerView) findViewById(R.id.recview11);
        AddActionBar();
        Subject_name_DV=findViewById(R.id.Subject_name_DV);
        Assignment_teacher_Video_tv=findViewById(R.id.Assignment_teacher_Video_tv);
        recview.setLayoutManager(new LinearLayoutManager(this));
        Subject_name_DV.setText(SelectedSubject);

            Assignment_teacher_Video_tv.setText(SelectedVideoS);
            options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Database").child(Year).child(SelectedSubject).child("Videos").child(SelectedVideoS), model.class)
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
        actionBar.setTitle("Videos");
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
