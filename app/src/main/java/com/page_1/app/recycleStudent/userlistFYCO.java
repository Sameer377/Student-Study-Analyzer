package com.page_1.app.recycleStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.page_1.app.R;
import java.util.ArrayList;
//firebase Libraries
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.page_1.app.admin.AdminDataBase.ClassYear;
public class userlistFYCO extends AppCompatActivity {


RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<com.page_1.app.recycleStudent.User> list;
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(userlistFYCO.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    TextView TotalNo,classname;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist_fyco);
        getSupportActionBar().hide();
        classname=findViewById(R.id.tv_rec_class);
        classname.setText(ClassYear);
        back=findViewById(R.id.userlistFYCO_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Student").child(ClassYear);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(userlistFYCO.this,list);
        TotalNo=findViewById(R.id.TotalNo);
        recyclerView.setAdapter(myAdapter);
        openProgressDialog();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    com.page_1.app.recycleStudent.User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                    int length=list.size();
                    TotalNo.setText(String.valueOf(length));
                }
                myAdapter.notifyDataSetChanged();
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
