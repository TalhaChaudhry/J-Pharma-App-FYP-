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
import com.talhachaudhry.jpharmaappfyp.adapter.CancelledOrdersAdapter;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.CancelledOrdersBottomSheet;
import com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet.ViewMedicineDetailsBottomSheet;
import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentCancelledOrdersBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageOrdersViewModel;

public class CancelledOrdersFragment extends Fragment implements CancelledOrdersCallback {

    FragmentCancelledOrdersBinding binding;
    ManageOrdersViewModel viewModel;
    CancelledOrdersAdapter adapter;

    public static CancelledOrdersFragment newInstance() {
        return new CancelledOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCancelledOrdersBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageOrdersViewModel.class);
        adapter = new CancelledOrdersAdapter(requireActivity(), this);
        binding.cancelOrderRv.setAdapter(adapter);
        viewModel.getCancelOrdersListLiveData().observe(getViewLifecycleOwner(), orderModels ->
                adapter.submitList(orderModels));
        return binding.getRoot();
    }

    @Override
    public void onItemClicked(OrderModel model) {
        CancelledOrdersBottomSheet bottomSheet = CancelledOrdersBottomSheet.newInstance(model, 1);
        bottomSheet.show(requireActivity().getSupportFragmentManager(),
                "CancelOrdersBottomSheet");
    }
}