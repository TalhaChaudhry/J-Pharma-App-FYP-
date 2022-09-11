package com.talhachaudhry.jpharmaappfyp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.adapter.ViewWholesalerRecyclerAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.ViewWholesalersBottomFragment;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewWholesalerClicked;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityViewWholesalerBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ViewWholesalersViewModel;

import java.util.Objects;

public class ViewWholesalerActivity extends AppCompatActivity implements OnViewWholesalerClicked {

    ActivityViewWholesalerBinding binding;
    ViewWholesalersViewModel viewModel;
    ViewWholesalerRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewWholesalerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        adapter = new ViewWholesalerRecyclerAdapter(this, this);
        binding.viewWholesalerRv.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(ViewWholesalersViewModel.class);
        viewModel.getUsersModel().observe(this, userModels ->
                adapter.submitList(userModels));
    }

    @Override
    public void onViewWholesalerClicked(UserModel model) {
        ViewWholesalersBottomFragment bottomSheet = ViewWholesalersBottomFragment.newInstance(model);
        bottomSheet.show(getSupportFragmentManager(),
                "UserModalBottomSheet");
    }
}