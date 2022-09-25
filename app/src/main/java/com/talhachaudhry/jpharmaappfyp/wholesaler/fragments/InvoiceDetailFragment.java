package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.InvoiceDetailAdapter;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentInvoiceDetailBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailFragment extends Fragment {

    FragmentInvoiceDetailBinding binding;
    private static final String ARG_PARAM1 = "orderModel";
    OrderModel model;
    InvoiceDetailAdapter adapter;

    public static InvoiceDetailFragment newInstance(OrderModel model) {
        InvoiceDetailFragment fragment = new InvoiceDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false);
        adapter = new InvoiceDetailAdapter(requireActivity());
        adapter.submitList(model.getOrdersList());
        binding.invoiceDetailRv.setAdapter(adapter);
        binding.crossIconIv.setOnClickListener(view -> requireActivity().onBackPressed());
        binding.dateTv.setText(model.getDateAndTime());
        binding.orderIdTv.setText(model.getOrderId());
        int total = 0;
        for (CartModel model : model.getOrdersList()) {
            total += model.getQuantity() * model.getModel().getPrice();
        }
        binding.totalPriceTextView.setText(MessageFormat.format("{0}", total));
        return binding.getRoot();
    }
}