package com.talhachaudhry.jpharmaappfyp.wholesaler;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.EditOrderAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
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
        viewModel.getPendingOrdersLiveData().observe(this, orderModels -> {
            if (orderModels.isEmpty()) {
                runOnUiThread(() -> binding.animation.setVisibility(View.VISIBLE));
            } else if(binding.animation.getVisibility() != View.INVISIBLE){
                runOnUiThread(() -> binding.animation.setVisibility(View.INVISIBLE));
            }
            adapter.submitList(orderModels);
        });
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
        confirmationDialog(model);
    }

    void confirmationDialog(OrderModel model) {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Order")
                .setIcon(R.drawable.cancel_order_ic)
                .setMessage("Are you sure you want to Cancel Order No. " + model.getOrderId())
                .setCancelable(true)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    askForReason(model);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    void askForReason(OrderModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reason").
                setCancelable(false).
                setMessage("Tell the reason why you are cancelling this order");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            if (!input.getText().toString().trim().equals("")) {
                model.setReason(input.getText().toString() + "   Cancelled by " + model.getUserModel().getUserName());
                viewModel.deleteOrder(model);
                dialog.dismiss();
            } else {
                Toast.makeText(EditOrderActivity.this, "Reason cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onUpdateOrderClicked(OrderModel model) {
        viewModel.setCurrentlyActiveOrder(model);
        openFragment(CartFragment.newInstance(), R.id.fragment_container);
    }
}