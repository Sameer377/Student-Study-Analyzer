package com.page_1.app.DisplayFiles;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.R;


import static com.page_1.app.DisplayFiles.DisplayNotes.SelectedNotes;
import static com.page_1.app.DisplayFiles.DisplayNotes.keys15;
import static com.page_1.app.DisplayFiles.DisplayVideos.SelectedVideo;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

public class DisplayNotesFiles extends AppCompatActivity {

    private RecyclerView recview;
    myadapter adapter;
    ImageView eve_btn,files;
    RelativeLayout view_eye_rel;
    ListView ListViewVideoDataanay;
    int val=0;
    String selectedNode,ParentNode;
    TextView Assignment_teacher_Video_tv,Subject_name_DV;
    FirebaseRecyclerOptions<model> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_videos_files);
        AddActionBar();
        recview = (RecyclerView) findViewById(R.id.recview11);
        Subject_name_DV=findViewById(R.id.Subject_name_DV);
        Assignment_teacher_Video_tv=findViewById(R.id.Assignment_teacher_Video_tv);
        recview.setLayoutManager(new LinearLayoutManager(this));
        Subject_name_DV.setText(SubjectName);
        ListViewVideoDataanay=findViewById(R.id.ListViewVideoDataanay);

        Assignment_teacher_Video_tv.setText(SelectedNotes);

            options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Database").child(teachingYear).child(SubjectName).child("Notes").child(SelectedNotes), model.class)
                            .build();

            adapter=new myadapter(options);
            recview.setAdapter(adapter);
        view_eye_rel=findViewById(R.id.view_eye_rel);

        eve_btn=findViewById(R.id.eye_btn);
        files=findViewById(R.id.files);
        view_eye_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val==0)
                {
                    val=1;
                    ListViewVideoDataanay.setVisibility(View.VISIBLE);
                    recview.setVisibility(View.GONE);
                    eve_btn.setVisibility(View.INVISIBLE);
                    files.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> arr;
                    arr
                            = new ArrayAdapter<String>(DisplayNotesFiles.this,
                            R.layout.item_list_for_dataanaylysis,
                            keys15);
                    ListViewVideoDataanay.setAdapter(arr);
                }
                else{
                    val=0;
                    recview.setVisibility(View.VISIBLE);
                    eve_btn.setVisibility(View.VISIBLE);
                    files.setVisibility(View.INVISIBLE);
                    ListViewVideoDataanay.setVisibility(View.GONE);
                }
            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keys15.clear();
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
