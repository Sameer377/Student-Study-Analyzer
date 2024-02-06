package com.page_1.app.student;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.student_menu.Year;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.DisplayFiles.Links.LinksAdapter;
import com.page_1.app.DisplayFiles.Links.LinksModel;
import com.page_1.app.R;

import java.util.ArrayList;
import java.util.Iterator;

public class DisplayLinksS extends AppCompatActivity {


private DatabaseReference dbr;
//rec
private RecyclerView courseRV;

    // Arraylist for storing data
    private ArrayList<LinksModel> linksModelArrayList = new ArrayList<LinksModel>();
    LinksAdapter linksAdapter = new LinksAdapter(this, linksModelArrayList);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_links);
        courseRV = findViewById(R.id.idRVCourse1);
        AddActionBar();
        dbr= FirebaseDatabase.getInstance().getReference().child("Database").child(Year).child(SelectedSubject).child("Links");
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    int cnt=0;
    String Sender;
    String uuur;
    public void updateConversation(DataSnapshot snapshot){

        String msg,user;
        Iterator i =snapshot.getChildren().iterator();
        while(i.hasNext()){

            msg=(String) ((DataSnapshot)i.next()).getValue();
            user=(String) ((DataSnapshot)i.next()).getValue();
            linksModelArrayList.add(new LinksModel(msg,user));
            linksAdapter.notifyDataSetChanged();
            cnt=cnt+1;
            Sender=msg;
            uuur=user;
        }


        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(linksAdapter);
        courseRV.smoothScrollToPosition(cnt-1);
    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(SelectedSubject);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C3D4E")));
    }
}