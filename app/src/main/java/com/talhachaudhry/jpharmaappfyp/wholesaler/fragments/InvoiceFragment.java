package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.adapter.InvoiceAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.callbacks.OpenBillFragmentCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentInvoiceBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;

public class InvoiceFragment extends Fragment implements CancelledOrdersCallback {

    FragmentInvoiceBinding binding;
    OrdersDetailViewModel viewModel;
    InvoiceAdapter adapter;
    OpenBillFragmentCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OpenBillFragmentCallback) {
            this.callback = (OpenBillFragmentCallback) context;
        }
    }

    public static InvoiceFragment newInstance() {
        return new InvoiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        adapter = new InvoiceAdapter(requireActivity(), this);
        viewModel = new ViewModelProvider(requireActivity()).get(OrdersDetailViewModel.class);
        binding.invoiceRecycler.setAdapter(adapter);
        viewModel.getCompleteOrdersLiveData().observe(getViewLifecycleOwner(), orderModels -> {
            if (orderModels.isEmpty()) {
                binding.animation.setVisibility(View.VISIBLE);
            } else if (binding.animation.getVisibility() != View.INVISIBLE) {
                binding.animation.setVisibility(View.INVISIBLE);
            }
            adapter.submitList(orderModels);
        });
        return binding.getRoot();
    }

    @Override
    public void onItemClicked(OrderModel model) {
        callback.openBill(model);
    }
}