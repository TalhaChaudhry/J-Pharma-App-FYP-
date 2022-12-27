package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.MedicineAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewMedicineDetail;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityPlaceOrderBinding;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageMedicineViewModel;
import com.talhachaudhry.jpharmaappfyp.view_models.PlaceOrderViewModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.AddToCartFragment;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.AnimationFragment;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.CartFragment;

import java.util.Objects;

public class PlaceOrderActivity extends AppCompatActivity implements OnViewMedicineDetail {

    ActivityPlaceOrderBinding binding;
    MedicineAdapter adapter;
    ManageMedicineViewModel viewModel;
    PlaceOrderViewModel placeOrderViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this).get(ManageMedicineViewModel.class);
        if (Boolean.TRUE.equals(viewModel.getIsLoading().getValue())) {
            openAnimation();
        }
        placeOrderViewModel = new ViewModelProvider(this).get(PlaceOrderViewModel.class);
        placeOrderViewModel.getCartList().observe(this, cartModels -> {
            if (!cartModels.isEmpty()) {
                binding.cartFrame.setVisibility(View.VISIBLE);
            } else {
                binding.cartFrame.setVisibility(View.GONE);
            }
        });
        placeOrderViewModel.getIsTaskPerformed().observe(this, aBoolean -> {
            if (Boolean.TRUE.equals(viewModel.getIsLoading().getValue())) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    viewModel.reload();
                    if (!getSupportFragmentManager().isDestroyed()) {
                        getSupportFragmentManager().popBackStackImmediate();
                        openAnimation();
                        Toast.makeText(this, "Updating Medicines", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        binding.viewCartBtn.setOnClickListener(view ->
                openFragment(CartFragment.newInstance(), R.id.fragment_container));
        viewModel.getAllMedicinesSet().observe(this, aBoolean -> {
            if (aBoolean) {
                ArrayAdapter arrayAdapter = new
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, viewModel.getAllMedicines());
                binding.searchEt.setAdapter(arrayAdapter);
                runOnUiThread(() -> getSupportFragmentManager().popBackStack());
            }
        });
        binding.searchEt.setThreshold(1);
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        adapter = new MedicineAdapter(this, this);
        binding.placeOrderRv.setAdapter(adapter);
        viewModel.getManageMedicineViewModelMutableLiveData().observe(this, manageMedicineModels ->
                adapter.submitList(manageMedicineModels));
        binding.searchBtn.setOnClickListener(view -> {
            int pos = viewModel.getAllMedicines().indexOf(binding.searchEt.getText().toString().trim());
            binding.placeOrderRv.scrollToPosition(pos);
        });
    }

    void openAnimation() {
        new Handler(Looper.getMainLooper()).post(() -> openFragment(AnimationFragment.newInstance(R.raw.loader_animation, ""),
                R.id.animation_container));
    }

    private void openFragment(@NonNull Fragment fragment, @IdRes int container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewMedicineDetailClicked(ManageMedicineModel model) {
        openFragment(AddToCartFragment.newInstance(model), R.id.fragment_container);
    }

    @Override
    public void deleteMedicine(ManageMedicineModel model) {
        // do nothing
    }

    @Override
    public void updateMedicine(ManageMedicineModel model) {
        // do nothing
    }
}