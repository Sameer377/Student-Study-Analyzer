package com.page_1.app.recycleFaculty;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.page_1.app.R;
import java.util.ArrayList;
//firebase Libraries
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.page_1.app.recycleFaculty.MyAdapter.MyViewHolder.Email;

public class userlist extends AppCompatActivity {
    ImageButton back;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<com.page_1.app.recycleFaculty.User> list;
    ProgressDialog progress;
    private FirebaseAuth mAuth;

    public void openProgressDialog() {
        progress = new ProgressDialog(userlist.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    TextView TotalNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().hide();
        back=findViewById(R.id.userlist_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Faculty");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(userlist.this,list);
        TotalNo=findViewById(R.id.TotalNo);
        recyclerView.setAdapter(myAdapter);
openProgressDialog();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    com.page_1.app.recycleFaculty.User user = dataSnapshot.getValue(User.class);
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//            mAuth.signInWithEmailAndPassword(Email, MyAdapter.MyViewHolder.password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()) {
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                if (user != null) {
//                                    // User is signed in
//                                    Toast.makeText(userlist.this,String.valueOf(user),Toast.LENGTH_SHORT).show();
//                                } else {
//                                    // No user is signed in
//                                }
//
//                            }
//                            else {
//                            }
//                        }
//                    });
//    }



}

