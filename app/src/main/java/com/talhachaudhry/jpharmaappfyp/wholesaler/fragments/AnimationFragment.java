package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentAnimationBinding;

public class AnimationFragment extends Fragment {


    private static final String ARG_PARAM1 = "indicator";
    private static final String ARG_PARAM2 = "info_text";
    FragmentAnimationBinding binding;
    private Integer indicator;


    public static AnimationFragment newInstance(Integer indicator, String info) {
        AnimationFragment fragment = new AnimationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, indicator);
        args.putString(ARG_PARAM2, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            indicator = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnimationBinding.inflate(inflater, container, false);
        binding.animation.setAnimation(indicator);
        binding.animation.setVisibility(View.VISIBLE);
        return binding.getRoot();
    }
}