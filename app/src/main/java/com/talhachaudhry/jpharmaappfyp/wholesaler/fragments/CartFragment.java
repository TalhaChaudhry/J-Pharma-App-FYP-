package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.CartAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.CartCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentCartBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.view_models.PlaceOrderViewModel;

public class CartFragment extends Fragment implements CartCallback {

    FragmentCartBinding binding;
    CartAdapter adapter;
    PlaceOrderViewModel viewModel;

    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PlaceOrderViewModel.class);
        adapter = new CartAdapter(requireActivity(), this);
        binding.backBtn.setOnClickListener(view -> requireActivity().onBackPressed());
        binding.cartRv.setAdapter(adapter);
        viewModel.getCartList().observe(getViewLifecycleOwner(), cartModels ->
                adapter.submitList(cartModels));
        binding.placeOrderBtn.setOnClickListener(view -> {
            viewModel.placeOrder();
            requireActivity().onBackPressed();
        });
        return binding.getRoot();
    }

    @Override
    public void onQuantityUpdated(CartModel model) {
        // do nothing
    }

    @Override
    public void onDeleteClicked(CartModel model) {
        viewModel.removeFromCart(model);
    }
}