package com.page_1.app.teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.page_1.app.R;
public class UrlFragment extends Fragment {


    public UrlFragment() {
        // Required empty public constructor
    }
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_url, container, false);
        return view;
    }
}