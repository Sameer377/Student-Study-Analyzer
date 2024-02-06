package com.page_1.app.teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.page_1.app.R;
public class fragment2 extends Fragment {

    View view;
public TextView tv_filename;
private ImageButton clear;
    String myDataFromActivity;
    String filetv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_fragment2, container, false);
        tv_filename=view.findViewById(R.id.tv_filename);
        clear=view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_filename.setText("");
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        teacher_upload activity = (teacher_upload) getActivity();
         myDataFromActivity = activity.getMyData();
        tv_filename.setText(myDataFromActivity);
        tv_filename.setMovementMethod(new ScrollingMovementMethod());
    }

}