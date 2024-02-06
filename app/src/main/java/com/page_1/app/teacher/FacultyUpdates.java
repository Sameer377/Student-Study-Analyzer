package com.page_1.app.teacher;

import static com.page_1.app.teacher.MainTeacherActivity.fullName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.page_1.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FacultyUpdates extends AppCompatActivity {
    private DatabaseReference dbr;
    Button buttonSendMsg;
    EditText edittMessage;
    ListView lvDiscussion;
    ArrayList<String> listConversation=new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    String UserName,SelectedTopic,user_msg_key;
    public String classY;

    public String getClassY() {
        return classY;
    }

    public void setClassY(String classY) {
        this.classY = classY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_updates);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F26586F0"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        buttonSendMsg=(Button) findViewById(R.id.buttonSendMsg);
        edittMessage=(EditText) findViewById(R.id.edittMessage);

        lvDiscussion=(ListView) findViewById(R.id.lvConversation);
        lvDiscussion.setStackFromBottom(true);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listConversation);
        lvDiscussion.setAdapter(arrayAdapter);
        lvDiscussion.setSelection(lvDiscussion.getAdapter().getCount() - 1);
        UserName="ketan";
//            UserName=getIntent().getExtras().get("user_name").toString();
//            SelectedTopic=getIntent().getExtras().get("selected_topic").toString();
        setTitle("Class : " + teachingYear);


        switch (teachingYear){
            case "First year":
                setClassY("FY class");
                break;
            case "Second year":
                setClassY("SY class");
                break;
            case "Third year":
                setClassY("TY class");
                break;
        }

        dbr= FirebaseDatabase.getInstance().getReference().child("Chat").child(getClassY());

        buttonSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((edittMessage.getText().toString().trim()).isEmpty()){
                    edittMessage.setError("Empty msg");
                    edittMessage.requestFocus();
                    return;
                }
                else{
                    Map<String,Object> map=new HashMap<String, Object>();
                    user_msg_key=dbr.push().getKey();
                    dbr.updateChildren(map);

                    DatabaseReference dbr2=dbr.child(user_msg_key);
                    Map<String,Object>map2=new HashMap<String,Object>();
                    map2.put("msg",edittMessage.getText().toString());
                    map2.put("user","\uD83D\uDC64  "+fullName);
                    dbr2.updateChildren(map2);
                    edittMessage.setText("");
                }

            }
        });
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
    public void updateConversation(DataSnapshot snapshot){

        String msg,user,conversation;
        Iterator i =snapshot.getChildren().iterator();
        while(i.hasNext()){

            msg=(String) ((DataSnapshot)i.next()).getValue();
            user=(String) ((DataSnapshot)i.next()).getValue();
            conversation=user + " : " + msg;
            arrayAdapter.insert(conversation,cnt);
            arrayAdapter.notifyDataSetChanged();
            cnt=cnt+1;
        }
        lvDiscussion.smoothScrollToPosition(cnt-1);
    }
}