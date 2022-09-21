package com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.adapter.OrdersDetailAdapter;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentCancelledOrdersBottomSheetBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

import java.text.MessageFormat;

public class CancelledOrdersBottomSheet extends BottomSheetDialogFragment {

    FragmentCancelledOrdersBottomSheetBinding binding;
    private static final String ARG_PARAM1 = "model";
    private static final String ARG_PARAM2 = "indicator";
    private OrderModel model;
    OrdersDetailAdapter adapter;
    int indicator;

    public static CancelledOrdersBottomSheet newInstance(OrderModel model, int indicator) {
        CancelledOrdersBottomSheet bottomSheetFragment = new CancelledOrdersBottomSheet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, model);
        args.putInt(ARG_PARAM2, indicator);
        bottomSheetFragment.setArguments(args);
        return bottomSheetFragment;
    }

    int total = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(ARG_PARAM1);
            indicator = getArguments().getInt(ARG_PARAM2);
        }
    }


    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCancelledOrdersBottomSheetBinding.inflate(inflater,
                container, false);
        if (indicator == 1) {
            binding.cancelTv.setVisibility(View.VISIBLE);
            binding.cancelTag.setVisibility(View.VISIBLE);
            binding.cancelTv.setText(model.getReason());
        }
        adapter = new OrdersDetailAdapter(requireActivity(), model.getOrdersList());
        binding.orderId.setText(model.getOrderId());
        binding.userNameTv.setText(model.getUserModel().getUserName());
        binding.shopNameTv.setText(model.getUserModel().getShopName());
        binding.contactNumberTv.setText(model.getUserModel().getContact());
        binding.cancelTv.setText(model.getReason());
        binding.orderDetailRv.setAdapter(adapter);
        for (CartModel price :
                model.getOrdersList()) {
            total += ((price.getQuantity()) * (price.getModel().getPrice()));
        }
        binding.totalTv.setText(MessageFormat.format("{0}", total));
        return binding.getRoot();
    }
}