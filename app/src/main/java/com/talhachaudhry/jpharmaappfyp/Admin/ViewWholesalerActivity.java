package com.talhachaudhry.jpharmaappfyp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.databinding.ActivityViewWholesalerBinding;

public class ViewWholesalerActivity extends AppCompatActivity {

    ActivityViewWholesalerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewWholesalerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}