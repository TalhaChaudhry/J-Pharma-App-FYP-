package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.talhachaudhry.jpharmaappfyp.adapter.OrderHistoryAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityOrdersHistoryBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;

import java.util.Objects;

public class OrdersHistoryActivity extends AppCompatActivity implements CancelledOrdersCallback {

    ActivityOrdersHistoryBinding binding;
    OrderHistoryAdapter adapter;
    OrdersDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        viewModel = new ViewModelProvider(this).get(OrdersDetailViewModel.class);
        adapter = new OrderHistoryAdapter(this, this);
        binding.ordersHistoryRv.setAdapter(adapter);
        viewModel.getCompleteOrdersLiveData().observe(this, orderModels -> {
            if (orderModels.isEmpty()) {
                runOnUiThread(() -> binding.animation.setVisibility(View.VISIBLE));
            } else if (binding.animation.getVisibility() != View.INVISIBLE) {
                runOnUiThread(() -> binding.animation.setVisibility(View.INVISIBLE));
            }
            adapter.submitList(orderModels);
        });
    }

    @Override
    public void onItemClicked(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 0);
        bottomSheet.show(getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }
}