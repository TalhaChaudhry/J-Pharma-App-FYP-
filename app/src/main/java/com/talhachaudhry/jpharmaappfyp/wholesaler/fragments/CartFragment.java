package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.CartAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.CartCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentCartBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.view_models.OrdersDetailViewModel;
import com.talhachaudhry.jpharmaappfyp.view_models.PlaceOrderViewModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.EditOrderActivity;
import com.talhachaudhry.jpharmaappfyp.wholesaler.PlaceOrderActivity;

import java.util.List;

public class CartFragment extends Fragment implements CartCallback {

    FragmentCartBinding binding;
    CartAdapter adapter;
    PlaceOrderViewModel viewModel;
    OrdersDetailViewModel viewModel1;
    List<CartModel> list;
    boolean inEdit;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PlaceOrderActivity) {
            viewModel = new ViewModelProvider(requireActivity()).get(PlaceOrderViewModel.class);
            inEdit = false;
        } else if (context instanceof EditOrderActivity) {
            viewModel1 = new ViewModelProvider(requireActivity()).get(OrdersDetailViewModel.class);
            inEdit = true;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        if (inEdit) {
            binding.title.setText("Update Order");
            binding.placeOrderBtn.setText("Update");
        }
        adapter = new CartAdapter(requireActivity(), this);
        binding.backBtn.setOnClickListener(view -> requireActivity().onBackPressed());
        binding.cartRv.setAdapter(adapter);
        if (inEdit) {
            viewModel1.getCurrentlyActiveOrder().observe(getViewLifecycleOwner(), orderModel -> {
                list = orderModel.getOrdersList();
                adapter.submitList(orderModel.getOrdersList());
            });
        } else {
            viewModel.getCartList().observe(getViewLifecycleOwner(), cartModels -> {
                list = cartModels;
                adapter.submitList(cartModels);
            });
        }
        binding.placeOrderBtn.setOnClickListener(view -> {
            if (!list.isEmpty()) {
                if (inEdit) {
                    viewModel1.editOrder();
                    Toast.makeText(requireActivity(), "Order Edited", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.placeOrder();
                    Toast.makeText(requireActivity(), "Order Placed", Toast.LENGTH_SHORT).show();
                }
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(requireActivity(), "Cart cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDeleteClicked(CartModel model) {
        viewModel.removeFromCart(model);
    }
}