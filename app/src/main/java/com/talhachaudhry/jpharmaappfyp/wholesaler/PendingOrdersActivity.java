package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.databinding.ActivityPendingOrdersBinding;

public class PendingOrdersActivity extends AppCompatActivity {

    ActivityPendingOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Pending Orders");
    }
}