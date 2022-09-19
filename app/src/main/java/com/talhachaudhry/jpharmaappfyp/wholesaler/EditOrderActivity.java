package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.EditOrderAdapter;
import com.talhachaudhry.jpharmaappfyp.adapter.OrderHistoryAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.callbacks.EditOrderCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityEditOrderBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.CartFragment;

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

    private void openFragment(@NonNull Fragment fragment, @IdRes int container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewOrderClicked(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 0);
        bottomSheet.show(getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }

    @Override
    public void onDeleteOrderClicked(OrderModel model) {
        viewModel.deleteOrder(model);
    }

    @Override
    public void onUpdateOrderClicked(OrderModel model) {
        viewModel.setCurrentlyActiveOrder(model);
        openFragment(CartFragment.newInstance(), R.id.fragment_container);
    }
}