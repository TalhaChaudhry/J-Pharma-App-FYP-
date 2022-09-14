package com.talhachaudhry.jpharmaappfyp.admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.AdminPendingOrdersAdapter;
import com.talhachaudhry.jpharmaappfyp.adapter.AdminProceedingOrderAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.AdminProceedingCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentAdminProceedingOrderBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageOrdersViewModel;

public class AdminProceedingOrderFragment extends Fragment implements AdminProceedingCallbacks {

    FragmentAdminProceedingOrderBinding binding;
    ManageOrdersViewModel viewModel;
    AdminProceedingOrderAdapter adapter;

    public static AdminProceedingOrderFragment newInstance() {
        return new AdminProceedingOrderFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProceedingOrderBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        adapter = new AdminProceedingOrderAdapter(requireActivity(), this);
        binding.pendingOrderRv.setAdapter(adapter);
        viewModel.getProceedingOrdersListLiveData().observe(getViewLifecycleOwner(), orderModels ->
                adapter.submitList(orderModels));
        return binding.getRoot();
    }

    @Override
    public void onCancelClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PROCEEDING_TO_CANCEL, model);
    }

    @Override
    public void onDispatchClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PROCEEDING_TO_DISPATCH, model);
    }

    @Override
    public void onPendingClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PROCEEDING_TO_PENDING, model);
    }

    @Override
    public void onClickedToView(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 0);
        bottomSheet.show(requireActivity().getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }
}