package com.talhachaudhry.jpharmaappfyp.Wholesaler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.databinding.ActivityOrdersHistoryBinding;

public class OrdersHistoryActivity extends AppCompatActivity {

    ActivityOrdersHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Orders History");
    }
}