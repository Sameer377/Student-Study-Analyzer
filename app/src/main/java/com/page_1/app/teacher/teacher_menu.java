package com.page_1.app.teacher;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.page_1.app.R;

public class teacher_menu extends AppCompatActivity implements View.OnClickListener {
public CardView card1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);
        getSupportActionBar().hide();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_teacher_profile:
                Intent intent = new Intent(this, TeacherProfileActivity.class);
                startActivity(intent);
                break;

        }
    }
}
