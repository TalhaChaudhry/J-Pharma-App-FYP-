package com.talhachaudhry.jpharmaappfyp.admin.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.AdminDispatchedOrdersAdapter;
import com.talhachaudhry.jpharmaappfyp.adapter.AdminPendingOrdersAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.ManageOrdersActivity;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.AdminDispatchOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentDispatchedOrdersAdminBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageOrdersViewModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.PendingOrdersActivity;

public class DispatchedOrdersAdminFragment extends Fragment implements AdminDispatchOrdersCallback {

    FragmentDispatchedOrdersAdminBinding binding;
    ManageOrdersViewModel viewModel;
    OrdersDetailViewModel viewModel1;
    AdminDispatchedOrdersAdapter adapter;
    boolean inAdmin;


    public static DispatchedOrdersAdminFragment newInstance() {
        return new DispatchedOrdersAdminFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ManageOrdersActivity) {
            inAdmin = true;
            viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        } else if (context instanceof PendingOrdersActivity) {
            inAdmin = false;
            viewModel1 = new ViewModelProvider(requireActivity()).get(OrdersDetailViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispatchedOrdersAdminBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        adapter = new AdminDispatchedOrdersAdapter(requireActivity(), this, inAdmin);
        binding.dispatchOrderRv.setAdapter(adapter);
        if (inAdmin) {
            viewModel.getDispatchOrdersListLiveData().observe(getViewLifecycleOwner(), orderModels -> {
                if (orderModels.isEmpty()) {
                    requireActivity().runOnUiThread(() -> binding.animation.setVisibility(View.VISIBLE));
                } else if (binding.animation.getVisibility() != View.INVISIBLE) {
                    requireActivity().runOnUiThread(() -> binding.animation.setVisibility(View.INVISIBLE));
                }
                adapter.submitList(orderModels);
            });
        } else {
            viewModel1.getDispatchOrdersLiveData().observe(getViewLifecycleOwner(), orderModels -> {
                if (orderModels.isEmpty()) {
                    requireActivity().runOnUiThread(() -> binding.animation.setVisibility(View.VISIBLE));
                } else if (binding.animation.getVisibility() != View.INVISIBLE) {
                    requireActivity().runOnUiThread(() -> binding.animation.setVisibility(View.INVISIBLE));
                }
                adapter.submitList(orderModels);
            });
        }
        return binding.getRoot();
    }

    @Override
    public void onCancelClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.DISPATCH_TO_CANCEL, model);
    }

    @Override
    public void onCompleteClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.DISPATCH_TO_COMPLETE, model);
    }

    @Override
    public void onClickedToView(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 0);
        bottomSheet.show(requireActivity().getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }

    @Override
    public void putInProceeding(OrderModel orderModel) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.DISPATCH_PROCEEDING, orderModel);
    }
}