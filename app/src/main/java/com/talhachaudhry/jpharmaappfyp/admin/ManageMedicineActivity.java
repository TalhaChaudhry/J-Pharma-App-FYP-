package com.talhachaudhry.jpharmaappfyp.admin;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.ManageMedicineRecyclerAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.ViewMedicineDetailsBottomSheet;
import com.talhachaudhry.jpharmaappfyp.admin.fragments.AddMedicineFragment;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewMedicineDetail;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityManageMedicineBinding;
import com.talhachaudhry.jpharmaappfyp.login_details.Login;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageMedicineViewModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.AnimationFragment;

import java.util.Objects;

public class ManageMedicineActivity extends AppCompatActivity implements OnViewMedicineDetail {

    ActivityManageMedicineBinding binding;
    ManageMedicineRecyclerAdapter adapter;
    ManageMedicineViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this).get(ManageMedicineViewModel.class);
        if (viewModel.getIsLoading()) {
            openAnimation();
        }
        adapter = new ManageMedicineRecyclerAdapter(this, this);
        viewModel.getManageMedicineViewModelMutableLiveData().observe(this, manageMedicineModels ->
                adapter.submitList(manageMedicineModels));
        viewModel.getAllMedicinesSet().observe(this, aBoolean -> runOnUiThread(() -> getSupportFragmentManager().popBackStack()));
        binding.manageMedicineRv.setAdapter(adapter);
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.addMedicine.setOnClickListener(view ->
                openFragment(AddMedicineFragment.newInstance(null), R.id.fragment_container));
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
        ViewMedicineDetailsBottomSheet bottomSheet = ViewMedicineDetailsBottomSheet.newInstance(model);
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    @Override
    public void deleteMedicine(ManageMedicineModel model) {
        confirmationDialog(model);
    }

    void confirmationDialog(ManageMedicineModel model) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Medicine")
                .setIcon(R.drawable.delete_ic)
                .setMessage("Are you sure you want to delete " + model.getName())
                .setCancelable(true)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    viewModel.deleteMedicine(model);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    void openAnimation() {
        new Handler(Looper.getMainLooper()).post(() ->
                openFragment(AnimationFragment.newInstance(R.raw.loader_animation, ""),
                R.id.animation_container));
    }

    @Override
    public void updateMedicine(ManageMedicineModel model) {
        openFragment(AddMedicineFragment.newInstance(model), R.id.fragment_container);
    }
}