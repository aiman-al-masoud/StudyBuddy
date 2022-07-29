package com.luxlunaris.studybuddy.view.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luxlunaris.studybuddy.R;

public class IntroFragment extends Fragment {

    private int fragmentLayout;

    public IntroFragment(@NonNull int fragmentLayout){
        this.fragmentLayout = fragmentLayout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(fragmentLayout, container, false);
        return rootView;
    }





}
