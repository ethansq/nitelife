package me.ethansq.nitelife.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.ethansq.nitelife.R;

public class FragmentBlank extends Fragment {
    public FragmentBlank() {
        // Required empty public constructor
    }

    public static FragmentBlank newInstance() {
        FragmentBlank fragment = new FragmentBlank();
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
