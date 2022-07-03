package com.talhachaudhry.jpharmaappfyp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.databinding.ActivityManageMedicineBinding;

public class ManageMedicineActivity extends AppCompatActivity {

    ActivityManageMedicineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Manage Medicine");
    }
}