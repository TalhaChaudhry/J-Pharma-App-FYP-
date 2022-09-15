package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.adapter.EditOrderAdapter;
import com.talhachaudhry.jpharmaappfyp.adapter.OrderHistoryAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.callbacks.EditOrderCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityEditOrderBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;

import java.util.Objects;

public class EditOrderActivity extends AppCompatActivity implements EditOrderCallbacks {

    ActivityEditOrderBinding binding;
    OrdersDetailViewModel viewModel;
    EditOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        viewModel = new ViewModelProvider(this).get(OrdersDetailViewModel.class);
        adapter = new EditOrderAdapter(this, this);
        binding.editOrderRv.setAdapter(adapter);
        viewModel.getPendingOrdersLiveData().observe(this, orderModels ->
                adapter.submitList(orderModels));
    }


    @Override
    public void onViewOrderClicked(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 1);
        bottomSheet.show(getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }

    @Override
    public void onDeleteOrderClicked(OrderModel model) {
        // TODO
    }

    @Override
    public void onUpdateOrderClicked(OrderModel model) {
        // TODO
    }
}