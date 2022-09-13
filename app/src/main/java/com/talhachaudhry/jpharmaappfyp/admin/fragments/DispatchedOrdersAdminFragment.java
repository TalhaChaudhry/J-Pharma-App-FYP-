package com.talhachaudhry.jpharmaappfyp.admin.fragments;

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
import com.talhachaudhry.jpharmaappfyp.callbacks.AdminDispatchOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentDispatchedOrdersAdminBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageOrdersViewModel;

public class DispatchedOrdersAdminFragment extends Fragment implements AdminDispatchOrdersCallback {

    FragmentDispatchedOrdersAdminBinding binding;
    ManageOrdersViewModel viewModel;
    AdminDispatchedOrdersAdapter adapter;

    public static DispatchedOrdersAdminFragment newInstance() {
        return new DispatchedOrdersAdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispatchedOrdersAdminBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        adapter = new AdminDispatchedOrdersAdapter(requireActivity(), this);
        binding.dispatchOrderRv.setAdapter(adapter);
        viewModel.getDispatchOrdersListLiveData().observe(getViewLifecycleOwner(), orderModels ->
                adapter.submitList(orderModels));
        return binding.getRoot();
    }

    @Override
    public void onCancelClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.DISPATCH_TO_CANCEL, model);
    }

    @Override
    public void onCompleteClicked(OrderModel model) {
        viewModel.performOperation(ManageOrdersViewModel.OrderOperationsEnums.DISPATCH_TO_COMPLETE,model);
    }

    @Override
    public void onClickedToView(OrderModel model) {
        // TODO
    }
}