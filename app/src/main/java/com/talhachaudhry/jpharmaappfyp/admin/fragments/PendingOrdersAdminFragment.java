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
import com.talhachaudhry.jpharmaappfyp.callbacks.AdminPendingOrderCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentPendingOrdersAdminBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageOrdersViewModel;

public class PendingOrdersAdminFragment extends Fragment implements AdminPendingOrderCallbacks {

    FragmentPendingOrdersAdminBinding binding;
    ManageOrdersViewModel viewModel;
    AdminPendingOrdersAdapter adapter;

    public static PendingOrdersAdminFragment newInstance() {
        return new PendingOrdersAdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingOrdersAdminBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        adapter = new AdminPendingOrdersAdapter(requireActivity(), this);
        binding.pendingOrderRv.setAdapter(adapter);
        viewModel.getPendingOrdersListLiveData().observe(getViewLifecycleOwner(), orderModels ->
                adapter.submitList(orderModels));
        return binding.getRoot();
    }

    @Override
    public void onProceedOrderClicker(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PENDING_TO_PROCEEDING,model);
    }

    @Override
    public void onDispatchOrderClicker(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PENDING_TO_DISPATCH,model);
    }

    @Override
    public void onCancelOrderClicker(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.PENDING_TO_CANCEL,model);
    }

    @Override
    public void onClickedToView(OrderModel orderModel) {
        // TODO
    }
}